package m2tp.serietemp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import m2tp.serietemp.models.User;
import m2tp.serietemp.models.Serie;
import java.util.List;

@Service
public class UserService implements  IUserService{
    @Autowired
    JdbcTemplate jdbctemp;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<User> getAll() {
        String req = "SELECT * FROM USERS";
        List<User> users = jdbctemp.query(req, new BeanPropertyRowMapper(User.class));
        return users;
    }
    @Override
    public List<User> findById(Long id) {
        String req = "SELECT * FROM USERS WHERE ID=?";
        return jdbctemp.query(req, new Object[]{id}, new BeanPropertyRowMapper(User.class));
    }

    @Override
    public List<User> findByUserName(String username) {
        String req = "SELECT * FROM USERS WHERE USERNAME='"+username+"'";
        return jdbctemp.query(req, new BeanPropertyRowMapper(User.class));
    }

    @Override
    public List<Serie> getSeries(Long id) {
        String req = "SELECT * FROM SERIES WHERE USERID=" + id;
        List<Serie> series = jdbctemp.query(req, new BeanPropertyRowMapper(Serie.class));
        return series;
    }

    @Override
    public void createUser(String username, String password) {
        String req = "INSERT INTO USERS (USERNAME, PASSWORD) VALUES ('"+username+"','"+
                bCryptPasswordEncoder.encode(password)+"')";
        jdbctemp.execute(req);
    }
}
