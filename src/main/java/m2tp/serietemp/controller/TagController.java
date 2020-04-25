package m2tp.serietemp.controller;

import m2tp.serietemp.errors.BadRequestException;
import m2tp.serietemp.errors.CreatedException;
import m2tp.serietemp.errors.NoContentException;
import m2tp.serietemp.models.Tag;
import m2tp.serietemp.services.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TagController {

    @Autowired
    private ITagService tagService;

    @RequestMapping(path = "/tags")
    public List<Tag> findTags () {
        List<Tag> tags = tagService.getAll();
        if(tags.isEmpty())
            throw new NoContentException();
        return tags;
    }

    @RequestMapping(path = "/tags/all/{eventId}")
    public List<Tag> findTags (@PathVariable Long eventId) {
        List<Tag> tags = tagService.getEventAll(eventId);
        if(tags.isEmpty())
            throw new NoContentException();
        return tags;
    }

    @RequestMapping(path = "/tags/{id}")
    public List<Tag> findTag (@PathVariable Long id) {
        List<Tag> tags = tagService.findById(id);
        if(tags.isEmpty())
            throw new NoContentException();
        return tags;
    }

    @PostMapping(path = "/tags", consumes = "application/json", produces = "application/json")
    public void addTag (@RequestBody Tag tag) {
        if(tag.getEventId() == null || tag.getVal() == null)
            throw new BadRequestException();
        tagService.addTag(tag.getVal(), tag.getEventId());
        throw new CreatedException();
    }

    @DeleteMapping(path = "/tags/{id}")
    public void removeTag (@PathVariable Long id) {
        tagService.deleteTag(id);
    }

    @DeleteMapping(path = "/tags/all/{eventId}")
    public void removeAllTags (@PathVariable Long eventId) {
        tagService.deleteAllTags(eventId);
    }
}
