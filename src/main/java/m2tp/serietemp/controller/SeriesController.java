package m2tp.serietemp.controller;

import m2tp.serietemp.errors.*;
import m2tp.serietemp.models.Serie;
import m2tp.serietemp.models.SharedSerie;
import m2tp.serietemp.models.User;
import m2tp.serietemp.services.ISerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class SeriesController {
    @Autowired
    private ISerieService serieService;

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

}
