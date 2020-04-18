package m2tp.serietemp.services;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

import m2tp.serietemp.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventService implements IEventService{
    @Autowired
    private JdbcTemplate jdbctemp;

    private String mapUpdateNullColumn(String column, Object object) {
        return object != null ? column+"='"+object+"'," : "";
    }

    private String mapInsertNullColumn(Object object) {
        return object != null ? "'"+object+"'" : null;
    }
    @Override
    @Cacheable("events")
    public List<Event> getAll() {
        String req = "SELECT * FROM EVENTS";
        List<Event> events = jdbctemp.query(req, new BeanPropertyRowMapper(Event.class));;
        return events;
    }

    @Override
    public Event findById(Long id) {
        String req = "SELECT * FROM EVENTS WHERE ID=?";
        Event event = (Event) jdbctemp.queryForObject(req, new Object[]{id},
                new BeanPropertyRowMapper(Event.class));
        return event;
    }

    @Override
    @CacheEvict(cacheNames = {"events"}, allEntries = true)
    public void insertEvent(Long serieId, Date moment, Double record, String comment){
        String req = "INSERT INTO EVENTS (MOMENT, RECORD, COMMENT, SERIEID) VALUES " +
                "('"+ new Timestamp(moment.getTime()) +"',"+record +","+this.mapInsertNullColumn(comment)+","+serieId+")";
        jdbctemp.execute(req);
    }

    @Override
    @CacheEvict(cacheNames = {"events"}, allEntries = true)
    public void removeEvent(Long id) {
        String req = "DELETE FROM EVENTS WHERE ID="+id;
        jdbctemp.execute(req);
    }

    @Override
    @CacheEvict(cacheNames = {"events"}, allEntries = true)
    public void editEvent(Long id, Double record, String comment) {
        String parsed = (this.mapUpdateNullColumn("RECORD",record) +
                this.mapUpdateNullColumn("COMMENT",comment));
        String req = "UPDATE EVENTS SET " +
                parsed.substring(0, parsed.length() - 1) +
                "WHERE ID="+id;

        jdbctemp.execute(req);
    }
}
