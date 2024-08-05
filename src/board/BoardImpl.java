package day0805.src.board;


import day0805.JDBC.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardImpl implements BoardDao {

    @Override
    public void createBoard(BoardCreateDto boardCreateDto) {
        String sql = "INSERT INTO board (member_id, price, content, category, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, boardCreateDto.getMemberId());
            pst.setInt(2, boardCreateDto.getPrice());
            pst.setString(3, boardCreateDto.getContent());
            pst.setString(4, boardCreateDto.getCategory());
            pst.setString(5, boardCreateDto.getStatus());

            if (pst.executeUpdate() == 0) {
                throw new SQLException("게시글 저장에 실패했습니다.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BoardListDto[] getAllBoards() {
        String sql = "SELECT * FROM board";
        return getBoardList(sql);
    }

    @Override
    public BoardListDto[] getOnSaleBoards() {
        String sql = "SELECT * FROM board WHERE status = ONSALE";
        return getBoardList(sql);
    }


    private BoardListDto[] getBoardList(String sql) {
        List<BoardListDto> boardList = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                BoardListDto board = new BoardListDto(
                        rs.getInt("id"),
                        rs.getInt("member_id"),
                        rs.getString("title"),
                        rs.getInt("price"),
                        rs.getString("category"),
                        rs.getString("status"),
                        rs.getTimestamp("post_date").toLocalDateTime());
                boardList.add(board);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boardList.toArray(new BoardListDto[0]);
    }

    @Override
    public BoardListDto[] getBoardsByTitle(String title) {
        String sql = "SELECT * FROM board WHERE title LIKE ?";
        return getBoardsByParam(title, sql);
    }

    @Override
    public BoardListDto[] getBoardsByCategory(String category) {
        String sql = "SELECT * FROM board WHERE category LIKE ?";
        return getBoardsByParam(category, sql);
    }

    @Override
    public BoardListDto[] getBoardsByWriter(String name) {
        String sql = "SELECT * FROM board JOIN member ON board.member_id = member.id and member.name LIKE ?";
        return getBoardsByParam(name, sql);
    }

    private BoardListDto[] getBoardsByParam(String param, String sql) {
        List<BoardListDto> boardList = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, "%" + param + "%");
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    BoardListDto board = new BoardListDto(
                            rs.getInt("id"),
                            rs.getInt("member_id"),
                            rs.getString("title"),
                            rs.getInt("price"),
                            rs.getString("category"),
                            rs.getString("status"),
                            rs.getTimestamp("post_date").toLocalDateTime());
                    boardList.add(board);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boardList.toArray(new BoardListDto[0]);
    }

    @Override
    public BoardDto getBoard(int id) {
        String sql = "SELECT * FROM board WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new BoardDto(
                            rs.getInt("id"),
                            rs.getInt("member_id"),
                            rs.getString("title"),
                            rs.getInt("price"),
                            rs.getString("content"),
                            rs.getString("category"),
                            rs.getString("status").toUpperCase(),
                            rs.getTimestamp("post_date").toLocalDateTime());
                } else {
                    throw new RuntimeException("id에 해당하는 게시글을 찾을 수 없습니다.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("데이터베이스 오류가 발생했습니다.");
        }
    }


    @Override
    public void updateBoardTitle(int id, String title) {
        String sql = "UPDATE board SET title = ? WHERE id = ?";
        updateBoard(id, title, sql);
    }

    @Override
    public void updateBoardContent(int id, String content) {
        String sql = "UPDATE board SET content = ? WHERE id = ?";
        updateBoard(id, content, sql);
    }

    @Override
    public void updateBoardPrice(int id, int price) {
        String sql = "UPDATE board SET price = ? WHERE id = ?";
        updateBoard(id, price, sql);
    }

    private void updateBoard(int id, Object param, String sql) {
        //status가 ONSALE일 경우만 수정 가능
        if (!isBoardOnSale(id)) {
            throw new RuntimeException("게시글이 판매중이 아닙니다.");
        }
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            if (param instanceof String) {
                pst.setString(1, (String) param);
            } else if (param instanceof Integer) {
                pst.setInt(1, (Integer) param);
            }
            pst.setInt(2, id);

            if (pst.executeUpdate() == 0) {
                throw new RuntimeException("id에 해당하는 게시글을 찾을 수 없습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("데이터베이스 오류가 발생했습니다.");
        }
    }


    @Override
    public void deleteBoard(int id) {
        //status가 ONSALE일 경우만 삭제 가능
        if (!isBoardOnSale(id)) {
            throw new RuntimeException("게시글이 판매중이 아닙니다.");
        }
        String sql = "DELETE FROM board WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            if (pst.executeUpdate() == 0) {
                throw new RuntimeException("id에 해당하는 게시글을 찾을 수 없습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("데이터베이스 오류가 발생했습니다.");
        }
    }

    private boolean isBoardOnSale(int id) {
        String sql = "SELECT status FROM board WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return "ONSALE".equals(rs.getString("status"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("데이터베이스 오류가 발생했습니다.");
        }
        return false;
    }
}
