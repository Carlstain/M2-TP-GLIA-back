package sharedseries.services;

import sharedseries.models.SharedSerie;

import java.util.List;

public interface ISharedSeriesService {
    public List<SharedSerie> getAll();
    public void shareSerie(Long userid, Long serieId, String premission);
    public void removeAccess(Long userid, Long serieId);
    public void privatizeSerie(Long serieId);
}
