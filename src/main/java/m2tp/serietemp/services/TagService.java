package m2tp.serietemp.services;

import m2tp.serietemp.models.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService implements ICommentService{
    @Autowired
    private JdbcTemplate jdbctemp;

    @Override
    public List<Comment> getAll() {
        String req  = "SELECT * FROM COMMENTS";
        List<Comment> comments = jdbctemp.query(req, new BeanPropertyRowMapper(Comment.class));
        return comments;
    }

    @Override
    public Comment findById(Long id){
        String req  = "SELECT * FROM COMMENTS WHERE ID=?";
        Comment comment = (Comment) jdbctemp.query(req, new Object[]{id}, new BeanPropertyRowMapper(Comment.class));
        return comment;
    }

    @Override
    public void addComment(String text, Long eventId){
        String req = "INSERT INTO COMMENTS (TEXT, EVENTID) VALUES ('"+text+"',"+eventId+")";
        jdbctemp.execute(req);
    }

    @Override
    public void deleteComment(Long id) {
        String req = "DELETE FROM COMMENTS WHERE ID="+id;
        jdbctemp.execute(req);
    }
}
