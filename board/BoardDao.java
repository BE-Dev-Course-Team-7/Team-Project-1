package day0805.board;

import day01.Member;

public interface BoardDao {
    void createBoard(BoardCreateDto boardCreateDto);
    BoardListDto[] getAllBoards();
    BoardListDto[] getOnSaleBoards();
    BoardListDto[] getBoardsByTitle();
    BoardListDto[] getBoardsByCategory();
    BoardListDto[] getBoardsByWriter();
    BoardDto getBoard(int id);
    
    //수정할 항목을 선택하세요 1.제목 2.가격 3.내용
    void updateBoard(int option, String content);
    void deleteBoard(int id);
}
