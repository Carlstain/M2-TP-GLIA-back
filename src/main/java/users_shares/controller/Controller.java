package sharedseries.controller;

import sharedseries.models.*;
import sharedseries.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {
    @Autowired
    private ISharedSeriesService sharedSeriesService;

    @RequestMapping(path = "/share")
    public List<SharedSerie> getShared() {
        return sharedSeriesService.getAll();
    }

    @PostMapping(path = "/share", consumes = "application/json", produces = "application/json")
    public void shareSerie(@RequestBody SharedSerie sharedSerie){
        sharedSeriesService.shareSerie(sharedSerie.getUserId(),sharedSerie.getSerieId(),sharedSerie.getPermission());
    }

    @DeleteMapping(path = "/share")
    public void revokePermissions(@RequestParam(name = "userid", required = true) Long userid,
                                  @RequestParam(name = "serieid", required = true) Long serieid) {
        sharedSeriesService.removeAccess(userid, serieid);
    }

    @DeleteMapping(path = "/share/{serieid}")
    public void privatize(@PathVariable Long serieid) {
        sharedSeriesService.privatizeSerie(serieid);
    }
}
