package m2tp.serietemp.services;

import m2tp.serietemp.models.Serie;

import java.util.List;

public interface ISerieService {
    public List<Serie> getAll();
    public Serie findById(Long Id);
    public void insertSerie(String title, String description);
    public void removeSerie(Long id);
    public void editSerie(Long id, String title, String description);
}
