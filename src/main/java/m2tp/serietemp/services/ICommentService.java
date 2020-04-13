package m2tp.serietemp.services;

import m2tp.serietemp.models.Comment;
import java.util.List;

public interface ICommentService {
    public List<Comment> getAll();
    public Comment findbyId(Long id);
    public void addComment(String text, Long eventId);
    public void deleteComment(Long id);
}
