package day0805.boardHeart.src;

import day0805.JDBC.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardHeartImpl implements BoardHeartDao {
    private BoardHeartImpl(){}
    private static BoardHeartDao instance = new BoardHeartImpl() {};
    public static BoardHeartDao getInstance() {
        return instance;
    }

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    // 관심 상품 등록
    @Override
    public void addBoardHeart(BoardHeart boardHeart) {
        String sql = "INSERT INTO BOARD_HEART (BOARD_ID, MEMBER_ID) VALUES (?, ?)";
        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setInt(1, boardHeart.getBoardId());
            ps.setInt(2, boardHeart.getMemberId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 관심 상품 삭제
    @Override
    public void removeBoardHeart(int id) {
        String sql = "DELETE FROM BOARD_HEART WHERE ID = ?";
        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 관심 상품 목록 조회
    @Override
    public List<BoardHeart> getBoardHeartsByMember(int memberId) {
        List<BoardHeart> list = new ArrayList<>();
        String sql = "SELECT * FROM BOARD_HEART WHERE MEMBER_ID = ?";
        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setInt(1, memberId);
            while (rs.next()) {
                BoardHeart boardHeart = new BoardHeart(
                        rs.getInt("ID"),
                        rs.getInt("BOARD_ID"),
                        rs.getInt("MEMBER_ID")
                );
                list.add(boardHeart);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
