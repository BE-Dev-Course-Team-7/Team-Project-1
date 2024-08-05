package day0805.board;


import day0805.db.DBUtil;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardImpl implements BoardDao {
    DBUtil db = DBUtil.getInstance();

    @Override
    public void createBoard(BoardCreateDto boardCreateDto) {
        String sql = "INSERT INTO board (member_id, price, content, category, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = db.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, boardCreateDto.getMemberId());
            pst.setInt(2, boardCreateDto.getPrice());
            pst.setString(3, boardCreateDto.getContent());
            pst.setString(4, boardCreateDto.getCategory());
            pst.setString(5, boardCreateDto.getStatus());

            if (pst.executeUpdate() == 0) {
                throw new SQLException("게시글 저장에 실패했습니다.");
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BoardListDto[] getAllBoards() {
        String sql = "SELECT * FROM board";
        List<BoardListDto> boardList = new ArrayList<>();
        try (Connection conn = db.getConnection();
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
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return boardList.toArray(new BoardListDto[0]);
    }

    @Override
    public BoardListDto[] getOnSaleBoards() {
        return new BoardListDto[0];
    }

    @Override
    public BoardListDto[] getBoardsByTitle() {
        return new BoardListDto[0];
    }

    @Override
    public BoardListDto[] getBoardsByCategory() {
        return new BoardListDto[0];
    }

    @Override
    public BoardListDto[] getBoardsByWriter() {
        return new BoardListDto[0];
    }

    @Override
    public BoardDto getBoard(int id) {
        return null;
    }

    @Override
    public void updateBoard(int option, String content) {

    }

    @Override
    public void deleteBoard(int id) {

    }
}
