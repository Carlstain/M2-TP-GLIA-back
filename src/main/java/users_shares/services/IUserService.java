package m2tp.serietemp.services;

import m2tp.serietemp.models.User;
import m2tp.serietemp.models.Serie;
import java.util.List;
public interface IUserService {
    public List<User> findById(Long id);
    public List<User> findByUserName(String username);
    public List<User> getAll();
    public List<Serie> getSeries(Long id);
    public void createUser(String username, String password);
}
