package m2tp.serietemp.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import m2tp.serietemp.errors.*;
import m2tp.serietemp.models.*;
import m2tp.serietemp.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class EventsController {

    @Autowired
    private IEventService eventService;

    @Autowired
    private ISerieService serieService;

    @LoadBalanced
    private final RestTemplate restTemplate = new RestTemplate();

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

    @RequestMapping(path = "series/{id}/events")
    public List<Event> getSerieEvents(@PathVariable Long id) {
        List<Event> events = serieService.getEvents(id);
        if(events.isEmpty())
            throw new NoContentException();
        return events;
    }
}
