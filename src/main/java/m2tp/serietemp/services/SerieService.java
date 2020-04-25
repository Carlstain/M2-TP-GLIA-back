package m2tp.serietemp.services;

import java.util.List;
import java.util.stream.Collectors;

import m2tp.serietemp.models.Event;
import m2tp.serietemp.models.Serie;
import m2tp.serietemp.models.SharedSerie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class SerieService implements ISerieService {
    @Autowired
    private JdbcTemplate jdbctemp;

    private String mapUpdateNullColumn(String column, Object object) {
        return object != null ? column+"='"+object+"'," : "";
    }

    private String mapInsertNullColumn(Object object) {
        return object != null ? "'"+object+"'" : null;
    }

    @Override
    @Cacheable("series")
    public List<Serie> getAll() {
        String req = "SELECT * FROM SERIES";
        List<Serie> series = jdbctemp.query(req, new BeanPropertyRowMapper(Serie.class));
        return series;
    }

    @Override
    @Cacheable("userseries")
    public List<Serie> getUserSeries(Long userid) {
        String req = "SELECT * FROM SERIES WHERE USERID="+userid;
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
    @CacheEvict(cacheNames = {"series"}, allEntries = true)
    public void insertSerie(String title, String description, Long userid){
        String req = "INSERT INTO SERIES (TITLE, DESCRIPTION, USERID) VALUES ("+this.mapInsertNullColumn(title)+","+this.mapInsertNullColumn(description)+","+userid+")";
        jdbctemp.execute(req);
    }

    @Override
    @CacheEvict(cacheNames = {"series"}, allEntries = true)
    public void removeSerie(Long id) {
        String req = "DELETE FROM SERIES WHERE ID="+id;
        jdbctemp.execute(req);
    }

    @Override
    @CacheEvict(cacheNames = {"serie"}, allEntries = true)
    public void editSerie(Long id, String title, String description){
        String parsed = (this.mapUpdateNullColumn("TITLE",title) +
                        this.mapUpdateNullColumn("DESCRIPTION",description));
        String req = "UPDATE SERIES SET " +parsed.substring(0, parsed.length() - 1) +
                "WHERE ID="+id;
        jdbctemp.execute(req);
    }

    @Override
    @Cacheable("serieevents")
    public List<Event> getEvents(Long id) {
        String req = "SELECT * FROM EVENTS WHERE SERIEID=" +id;
        List<Event> events = jdbctemp.query(req, new BeanPropertyRowMapper(Event.class));
        return events;
    }

    @Override
    public List<Serie> getSharedme(List<SharedSerie> sharedSeries) {
        List<Serie> series = this.getAll();
        series.removeIf(serie -> sharedSeries.stream().noneMatch(sharedSerie -> serie.getId().equals(sharedSerie.getSerieId())));
        return series;
    }
}
