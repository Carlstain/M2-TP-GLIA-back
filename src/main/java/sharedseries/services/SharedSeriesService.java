package sharedseries.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import sharedseries.models.Permissions;
import sharedseries.models.SharedSerie;

import java.util.List;

@Service
public class SharedSeriesService implements ISharedSeriesService{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Cacheable("shares")
    public List<SharedSerie> getAll() {
        String req = "SELECT * FROM SHARED";
        return jdbcTemplate.query(req, new BeanPropertyRowMapper(SharedSerie.class));
    }

    @Override
    public void shareSerie(Long userid, Long serieId, String permission) {
        String req = "INSERT INTO SHARED (USERID, SERIEID, PERMISSION) VALUES ("+
                userid+","+serieId+",'"+ Permissions.valueOf(permission) +"')";
        jdbcTemplate.execute(req);
    }

    @Override
    public void removeAccess(Long userid, Long serieid) {
        String req = "DELETE FROM SHARED WHERE USERID=" + userid + " AND SERIEID=" + serieid;
        jdbcTemplate.execute(req);
    }

    @Override
    public void privatiseSerie(Long serieId) {
        String req = "DELETE FROM SHARED WHERE SERIEID="+serieId;
        jdbcTemplate.execute(req);
    }
}
