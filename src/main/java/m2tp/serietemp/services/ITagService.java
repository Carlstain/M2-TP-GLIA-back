package m2tp.serietemp.services;

import m2tp.serietemp.models.Tag;
import java.util.List;

public interface ITagService {
    public List<Tag> getAll();
    public List<Tag> getEventAll(Long eventId);
    public List<Tag> findById(Long id);
    public void addTag(String text, Long eventId);
    public void deleteAllTags(Long eventId);
    public void deleteTag(Long id);
}
