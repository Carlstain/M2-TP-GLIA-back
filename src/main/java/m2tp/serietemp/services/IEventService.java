package m2tp.serietemp.services;
import m2tp.serietemp.models.Event;

import java.util.Date;
import java.util.List;

public interface IEventService {
    public List<Event> getAll ();
    public Event findById(Long id);
    public void insertEvent(Long serieId, Date moment, Double record, String comment);
    public void removeEvent(Long id);
    public void editEvent (Long id, Double record, String comment);
}
