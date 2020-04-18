package m2tp.serietemp.services;

import m2tp.serietemp.models.Event;
import m2tp.serietemp.models.Serie;

import java.util.List;

public interface ISerieService {
    public List<Serie> getAll();
    public List<Serie> getUserSeries(Long userid);
    public Serie findById(Long Id);
    public void insertSerie(String title, String description, Long userid);
    public void removeSerie(Long id);
    public void editSerie(Long id, String title, String description);
    public List<Event> getEvents(Long id);
}
