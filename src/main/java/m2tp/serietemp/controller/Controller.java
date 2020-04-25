package m2tp.serietemp.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import m2tp.serietemp.errors.*;
import m2tp.serietemp.models.*;
import m2tp.serietemp.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

@RestController
public class Controller {

    @Autowired
    private ISerieService serieService;

    @Autowired
    private IEventService eventService;

    @Autowired
    private ITagService tagService;

    @LoadBalanced
    private final RestTemplate restTemplate = new RestTemplate();

    @RequestMapping("/series")
    public List<Serie> findSeries(@RequestParam(name = "userid", required = false) Long userid){
        if (userid != null)
            return  serieService.getUserSeries(userid);
        List<Serie> series = serieService.getAll();
        if (series.isEmpty())
            throw new NoContentException();
        return series;
    }

    @RequestMapping("/series/me")
    public List<Serie> findSeriesMe(){
        User user = restTemplate.getForObject("http://localhost:8002/authenticated", User.class);
        if (user == null)
            throw new RequestUnauthorizedException();
        List<Serie> series =  serieService.getUserSeries(user.getId());
        if(series.isEmpty())
            throw new NoContentException();
        return  series;
    }

    @RequestMapping("/series/shared")
    public List<Serie> findSeriesSharedMe(){
        ResponseEntity<SharedSerie[]> responseEntity;
        User user = restTemplate.getForObject("http://localhost:8002/authenticated", User.class);
        if (user == null)
            throw new RequestUnauthorizedException();

        responseEntity = restTemplate.getForEntity("http://localhost:8002/share/"+user.getId(), SharedSerie[].class);

        SharedSerie[] shared = responseEntity.getBody();
        assert shared != null;
        List<SharedSerie> sharedSeries = new ArrayList<>(Arrays.asList(shared));
        List<Serie> series = serieService.getSharedme(sharedSeries);
        if (series.isEmpty())
            throw new NoContentException();
        return  series;
    }

    @RequestMapping("/series/{id}")
    public Serie findSerie(@PathVariable Long id){
        return serieService.findById(id);
    }

    @PostMapping(path = "/series", consumes = "application/json", produces = "application/json")
    public void addSerie(@RequestBody @Valid Serie serie){
        User user = restTemplate.getForObject("http://localhost:8002/authenticated", User.class);
        if (user == null)
            throw new RequestUnauthorizedException();
        if (serie.getTitle() == null)
            throw new BadRequestException();
        serie.setUserid(user.getId());
        serieService.insertSerie(serie.getTitle(),serie.getDescription(), serie.getUserid());
        throw new CreatedException();
    }

    @DeleteMapping(path = "/series/{id}")
    public void deleteSerie(@PathVariable Long id){
        User user = restTemplate.getForObject("http://localhost:8002/authenticated", User.class);
        if (user == null)
            throw new RequestUnauthorizedException();
        Serie serie = serieService.findById(id);
        if (user.getId() != serie.getUserid())
            throw new ForbiddenException("You cannot delete a serie you do not own");
        serieService.removeSerie(id);
    }

    @PutMapping(path = "/series/{id}", consumes = "application/json", produces = "application/json")
    public void editSerie(@RequestBody Serie serie, @PathVariable Long id) {
        User user = restTemplate.getForObject("http://localhost:8002/authenticated", User.class);
        if (user == null)
            throw new RequestUnauthorizedException();
        Serie toedit = serieService.findById(id);
        if (user.getId() != toedit.getUserid())
            throw new ForbiddenException("You cannot edit a serie you do not own");
        serieService.editSerie(id, serie.getTitle(), serie.getDescription());
    }

    @RequestMapping(path = "/events")
    public List<Event> findEvents(){
        List<Event> events = eventService.getAll();
        if(events.isEmpty())
            throw new NoContentException();
        return events;
    }

    @RequestMapping(path = "/events/{id}")
    public Event findEvent(@PathVariable Long id){
        return eventService.findById(id);
    }

    @PostMapping(path = "/events", consumes = "application/json", produces = "application/json")
    public void addEvent(@RequestBody Event event){
        User user = restTemplate.getForObject("http://localhost:8002/authenticated", User.class);
        if (user == null)
            throw new RequestUnauthorizedException();
        else if (event.getSerieId() == null || event.getRecord() == null)
            throw new BadRequestException();
        Serie serie = serieService.findById(event.getSerieId());
        if (user.getId() != serie.getUserid())
            throw new ForbiddenException("You can only add events to series you own or that are shared with you.");
        eventService.insertEvent(event.getSerieId(),
                event.getMoment(),
                event.getRecord(),
                event.getComment());
        throw new CreatedException();
    }

    @DeleteMapping(path = "/events/{id}")
    public void removeEvent(@PathVariable Long id){
        User user = restTemplate.getForObject("http://localhost:8002/authenticated", User.class);
        if (user == null)
            throw new RequestUnauthorizedException();
        Event event = eventService.findById(id);
        Serie serie = serieService.findById(event.getSerieId());
        if(user.getId() != serie.getUserid())
            throw new ForbiddenException("You cannot delete an event from a serie you do not own");
        eventService.removeEvent(id);
    }

    @PutMapping(path = "/events/{id}", consumes = "application/json", produces = "application/json")
    public void editEvent(@PathVariable Long id, @RequestBody Event event){
        User user = restTemplate.getForObject("http://localhost:8002/authenticated", User.class);
        if (user == null)
            throw new RequestUnauthorizedException();
        Serie serie = serieService.findById(id);
        if(user.getId() != serie.getUserid())
            throw new ForbiddenException("You cannot edit an event from a serie you do not own");
        eventService.editEvent(id,event.getRecord(), event.getComment());
    }

    @RequestMapping(path = "/tags")
    public List<Tag> findTags () {
        List<Tag> tags = tagService.getAll();
        if(tags.isEmpty())
            throw new NoContentException();
        return tags;
    }

    @RequestMapping(path = "/tags/all/{eventId}")
    public List<Tag> findTags (@PathVariable Long eventId) {
        List<Tag> tags = tagService.getEventAll(eventId);
        if(tags.isEmpty())
            throw new NoContentException();
        return tags;
    }

    @RequestMapping(path = "/tags/{id}")
    public List<Tag> findTag (@PathVariable Long id) {
        List<Tag> tags = tagService.findById(id);
        if(tags.isEmpty())
            throw new NoContentException();
        return tags;
    }

    @PostMapping(path = "/tags", consumes = "application/json", produces = "application/json")
    public void addTag (@RequestBody Tag tag) {
        tagService.addTag(tag.getVal(), tag.getEventId());
    }

    @DeleteMapping(path = "/tags/{id}")
    public void removeTag (@PathVariable Long id) {
        tagService.deleteTag(id);
    }

    @DeleteMapping(path = "/tags/all/{eventId}")
    public void removeAllTags (@PathVariable Long eventId) {
        tagService.deleteAllTags(eventId);
    }

    @RequestMapping(path = "series/{id}/events")
    public List<Event> getSerieEvents(@PathVariable Long id) {
        List<Event> events = serieService.getEvents(id);
        if(events.isEmpty())
            throw new NoContentException();
        return events;
    }
}
