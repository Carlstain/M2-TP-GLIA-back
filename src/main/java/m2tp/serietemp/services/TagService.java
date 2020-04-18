package m2tp.serietemp.services;

import m2tp.serietemp.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService implements ITagService {
    @Autowired
    private JdbcTemplate jdbctemp;

    @Override
    @Cacheable("tags")
    public List<Tag> getAll() {
        String req  = "SELECT DISTINCT VAL FROM TAGS";
        List<Tag> tags = jdbctemp.query(req, new BeanPropertyRowMapper(Tag.class));
        return tags;
    }

    @Override
    @Cacheable("eventtags")
    public List<Tag> getEventAll(Long eventId) {
        String req  = "SELECT * FROM TAGS WHERE EVENTID="+eventId;
        List<Tag> tags = jdbctemp.query(req, new BeanPropertyRowMapper(Tag.class));
        return tags;
    }

    @Override
    public List<Tag> findById(Long id){
        String req  = "SELECT * FROM TAGS WHERE ID=?";
        return jdbctemp.query(req, new Object[]{id}, new BeanPropertyRowMapper(Tag.class));
    }

    @Override
    @CacheEvict(cacheNames = {"tags"}, allEntries = true)
    public void addTag(String text, Long eventId){
        String req = "INSERT INTO TAGS (VAL, EVENTID) VALUES ('"+text+"',"+eventId+")";
        jdbctemp.execute(req);
    }

    @Override
    @CacheEvict(cacheNames = {"eventtags"}, allEntries = true)
    public void deleteAllTags(Long eventid) {
        String req = "DELETE FROM TAGS WHERE EVENTID="+eventid;
        jdbctemp.execute(req);
    }

    @Override
    @CacheEvict(cacheNames = {"tags"}, allEntries = true)
    public void deleteTag(Long id) {
        String req = "DELETE FROM TAGS WHERE ID="+id;
        jdbctemp.execute(req);
    }
}
