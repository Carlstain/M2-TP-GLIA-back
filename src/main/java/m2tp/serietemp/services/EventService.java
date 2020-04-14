package m2tp.serietemp.services;
import java.util.List;

import m2tp.serietemp.models.Comment;
import m2tp.serietemp.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventService implements IEventService{
    @Autowired
    private JdbcTemplate jdbctemp;

    @Override
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
    public void insertEvent(Long serieId, Long moment, String description, String tag){
        String req = "INSERT INTO EVENTS (MOMENT, DESCRIPTION, TAG, SERIEID) VALUES " +
                "("+moment+","+description +","+tag+","+serieId+")";
        jdbctemp.execute(req);
    }

    @Override
    public void removeEvent(Long id) {
        String req = "DELETE FROM EVENTS WHERE ID="+id;
        jdbctemp.execute(req);
    }

    @Override
    public void editEvent(Long id, String description, String tag) {
        String req = "UPDATE EVENTS SET TAG="+tag+", DESCRIPTION="+description+" WHERE ID="+id;

        if(tag != null && description==null) {
            req = "UPDATE EVENTS SET TAG="+tag+" WHERE ID="+id;
        }
        else if (tag == null && description!=null) {
            req = "UPDATE EVENTS SET DESCRIPTION="+description+" WHERE ID="+id;
        }
        jdbctemp.execute(req);
    }

    @Override
    public List<Comment> getComments(Long id) {
        String req = "SELECT * FROM COMMENTS WHERE EVENTID="+id;
        List<Comment> comments = jdbctemp.query(req, new BeanPropertyRowMapper(Comment.class));
        return comments;
    }
}
