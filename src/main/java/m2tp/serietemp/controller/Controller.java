package m2tp.serietemp.controller;

import java.util.List;

import m2tp.serietemp.models.*;
import m2tp.serietemp.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @Autowired
    private ISerieService serieService;

    @Autowired
    private IEventService eventService;

    @Autowired
    private ITagService tagService;

    @RequestMapping("/series")
    public List<Serie> findSeries(@RequestParam(name = "userid", required = false) Long userid){
        if (userid != null)
            return  serieService.getUserSeries(userid);
        return serieService.getAll();
    }

    @RequestMapping("/series/{id}")
    public Serie findSerie(@PathVariable Long id){
        return serieService.findById(id);
    }

    @PostMapping(path = "/series", consumes = "application/json", produces = "application/json")
    public Serie addSerie(@RequestBody Serie serie){
        serieService.insertSerie(serie.getTitle(),serie.getDescription(),serie.getUserid());
        return serie;
    }

    @DeleteMapping(path = "/series/{id}")
    public void deleteSerie(@PathVariable Long id){
        serieService.removeSerie(id);
    }

    @PutMapping(path = "/series/{id}", consumes = "application/json", produces = "application/json")
    public void editSerie(@RequestBody Serie serie, @PathVariable Long id) {
        serieService.editSerie(id, serie.getTitle(), serie.getDescription());
    }

    @RequestMapping(path = "/events")
    public List<Event> findEvents(){
        return eventService.getAll();
    }

    @RequestMapping(path = "/events/{id}")
    public Event findEvent(@PathVariable Long id){
        return eventService.findById(id);
    }

    @PostMapping(path = "/events", consumes = "application/json", produces = "application/json")
    public void addEvent(@RequestBody Event event){
        eventService.insertEvent(event.getSerieId(),
                event.getMoment(),
                event.getRecord(),
                event.getComment());
    }

    @DeleteMapping(path = "/events/{id}")
    public void removeEvent(@PathVariable Long id){
        eventService.removeEvent(id);
    }

    @PutMapping(path = "/events/{id}", consumes = "application/json", produces = "application/json")
    public void editEvent(@PathVariable Long id, @RequestBody Event event){
        eventService.editEvent(id,event.getRecord(), event.getComment());
    }

    @RequestMapping(path = "/tags")
    public List<Tag> findTags () {
        return tagService.getAll();
    }

    @RequestMapping(path = "/tags/all/{eventId}")
    public List<Tag> findTags (@PathVariable Long eventId) {
        return tagService.getEventAll(eventId);
    }

    @RequestMapping(path = "/tags/{id}")
    public List<Tag> findTag (@PathVariable Long id) {
        return tagService.findById(id);
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
        return serieService.getEvents(id);
    }
}
