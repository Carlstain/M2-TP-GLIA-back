package m2tp.serietemp.services;

import java.util.List;

import m2tp.serietemp.models.Event;
import m2tp.serietemp.models.Serie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class SerieService implements ISerieService {
    @Autowired
    private JdbcTemplate jdbctemp;

    @Override
    public List<Serie> getAll() {
        String req = "SELECT * FROM SERIES";
        List<Serie> series = jdbctemp.query(req, new BeanPropertyRowMapper(Serie.class));
        return series;
    }

    @Override
    public Serie findById(Long id){
        String req = "SELECT * FROM SERIES WHERE ID=?";
        Serie serie = (Serie) jdbctemp.queryForObject(req, new Object[]{id},
                new BeanPropertyRowMapper(Serie.class));
        return serie;
    }

    @Override
    public void insertSerie(String title, String description){
        String req = "INSERT INTO SERIES (TITLE, DESCRIPTION) VALUES ("+title+","+description +")";
        jdbctemp.execute(req);
    }

    @Override
    public void removeSerie(Long id) {
        String req = "DELETE FROM SERIES WHERE ID="+id;
        jdbctemp.execute(req);
    }

    @Override
    public void editSerie(Long id, String title, String description){
        String req = "UPDATE SERIES SET TITLE="+title+", DESCRIPTION="+description+" WHERE ID="+id;

        if(title != null && description==null) {
            req = "UPDATE SERIES SET TITLE="+title+" WHERE ID="+id;
        }
        else if (title == null && description!=null) {
            req = "UPDATE SERIES SET DESCRIPTION="+description+" WHERE ID="+id;
        }
        jdbctemp.execute(req);
    }

    @Override
    public List<Event> getEvents(Long id) {
        String req = "SELECT * FROM EVENTS WHERE SERIEID=" +id;
        List<Event> events = jdbctemp.query(req, new BeanPropertyRowMapper(Event.class));
        return events;
    }
}
