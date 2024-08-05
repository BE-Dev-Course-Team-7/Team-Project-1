package day0805.src.board;

import day01.Member;

public interface BoardDao {
    void createBoard(BoardCreateDto boardCreateDto);
    BoardListDto[] getAllBoards();
    BoardListDto[] getOnSaleBoards();
    BoardListDto[] getBoardsByTitle(String title);
    BoardListDto[] getBoardsByCategory(String category);
    BoardListDto[] getBoardsByWriter(String name);
    BoardDto getBoard(int id);
    void updateBoardTitle(int id, String title);
    void updateBoardPrice(int id, int price);
    void updateBoardContent(int id, String content);
    void deleteBoard(int id);
}
