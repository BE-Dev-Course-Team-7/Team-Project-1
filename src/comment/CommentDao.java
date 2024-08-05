import java.sql.SQLException;

public interface CommentDao {

    int addComment(Comment comment) throws SQLException;
    int updateComment(Comment comment, Member member) throws SQLException;
    int deleteComment(Comment comment, Member member) throws SQLException;
    Comment[] getAllComments(int boardId) throws SQLException;
}
