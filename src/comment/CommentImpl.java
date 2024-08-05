package comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentImpl implements CommentDao {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public int addComment(Comment comment) {
        int result = 0;
        try {
            String SQL = "INSERT INTO COMMENT(BOARD_ID, MEMBER_ID, CONTENT) VALUES(?, ?, ?)";
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(SQL);
            ps.setInt(1, comment.getBoardId());
            ps.setInt(2, comment.getMemberId());
            ps.setString(3, comment.getContent());
            result = ps.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps, conn);
        }
        return result;
    }

    @Override
    public int updateComment(Comment comment, Member member) {
        int result = 0;
        if(comment.getMemberId() != member.getId()) {
            return 0;
        }
        try {
            String SQL = "UPDATE COMMENT SET CONTENT = ? WHERE ID = ?";
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(SQL);
            ps.setString(1, comment.getContent());
            ps.setInt(2, comment.getId());
            result = ps.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps, conn);
        }
        return result;
    }

    @Override
    public int deleteComment(Comment comment, Member member) {
        int result = 0;
        if(comment.getMemberId() != member.getId()) {
            return 0;
        }
        try {
            String SQL = "DELETE FROM COMMENT WHERE ID = ?";
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(SQL);
            ps.setInt(1, comment.getId());
            result = ps.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps, conn);
        }
        return result;
    }

    @Override
    public Comment[] getAllComments(int boardId) {
        List<Comment> list = new ArrayList<>();
        int result = 0;
        try {
            String SQL = "SELECT * FROM COMMENT WHERE BOARD_ID = ?";
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(SQL);
            ps.setInt(1, boardId);
            rs = ps.executeQuery();
            while(rs.next()) {
                Comment comment = new Comment(rs.getInt(2),rs.getInt(3),rs.getString(4));
                comment.setId(rs.getInt(1));
                list.add(comment);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps, conn);
        }
        return list.toArray(new Comment[list.size()]);
    }
}