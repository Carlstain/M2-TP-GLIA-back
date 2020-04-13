package m2tp.serietemp.controller;

import java.util.List;

import m2tp.serietemp.models.Event;
import m2tp.serietemp.models.Serie;
import m2tp.serietemp.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @Autowired
    private ISerieService serieService;

    @Autowired
    private IEventService eventService;

    @RequestMapping("/series")
    public List<Serie> findSeries(){
        return serieService.getAll();
    }
    @RequestMapping("/series/{id}")
    public Serie findSerie(@PathVariable Long id){
        return serieService.findById(id);
    }

    @PostMapping(path = "/series", consumes = "application/json", produces = "application/json")
    public void addSerie(@RequestBody Serie serie){
        serieService.insertSerie(serie.getTitle(),serie.getDescription());
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
        eventService.insertEvent(event.getSerieId(),event.getMoment(),event.getDescription(),event.getTag());
    }

    @DeleteMapping(path = "/events/{id}")
    public void removeEvent(@PathVariable Long id){
        eventService.removeEvent(id);
    }

    @PutMapping(path = "/events/{id}", consumes = "application/json", produces = "application/json")
    public void editEvent(@PathVariable Long id, @RequestBody Event event){
        eventService.editEvent(id,event.getDescription(),event.getTag());
    }
}
