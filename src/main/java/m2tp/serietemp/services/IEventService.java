package m2tp.serietemp.services;
import m2tp.serietemp.models.Event;

import java.util.List;

public interface IEventService {
    public List<Event> getAll ();
    public Event findById(Long id);
    public void insertEvent(Long serieId, Long moment, String description, String tag);
    public void removeEvent(Long id);
    public void editEvent (Long id, String description, String tag);
}
