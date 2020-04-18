package users_shares.controller;

import m2tp.serietemp.models.Serie;
import users_shares.models.*;
import users_shares.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {
    @Autowired
    private ISharedSeriesService sharedSeriesService;

    @Autowired
    private IUserService userService;

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

    @RequestMapping(path = "/users")
    public List<User> getAllUsers(@RequestParam(name = "name", required = false) String name) {
        if(name != null)
            return userService.findByUserName(name);
        return userService.getAll();
    }

    @RequestMapping(path = "/users/{id}")
    public List<User> getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping(path = "/users", consumes = "application/json", produces = "application/json")
    public void creatUser(@RequestBody User user) {
        userService.createUser(user.getUsername(), user.getPassword());
    }
}
