package day0805.boardHeart.src;

import java.util.List;

public class BoardHeartDao {
    void addBoardHeart(BoardHeart boardHeart);
    void removeBoardHeart(int id);
    BoardHeart getBoardHeart(int id);
    List<BoardHeart> getAllBoardHearts();
    List<BoardHeart> getBoardHeartsByMember(int memberId);
}
