package day0805.boardHeart.src;

import java.util.List;

/**
 * BoardHeart 데이터 접근 객체 (DAO) 인터페이스.
 * BoardHeart 엔티티에 대한 CRUD 작업을 수행하기 위한 메서드를 제공합니다.
 */
public interface BoardHeartDao {

    /**
     * 새로운 BoardHeart를 데이터베이스에 추가합니다.
     *
     * @param boardHeart 추가할 BoardHeart 객체
     */
    void addBoardHeart(BoardHeart boardHeart);

    /**
     * ID를 기반으로 BoardHeart를 데이터베이스에서 제거합니다.
     *
     * @param id 제거할 BoardHeart의 ID
     */
    void removeBoardHeart(int id);

    /**
     * 특정 회원의 BoardHeart 목록을 조회합니다.
     *
     * @param memberId BoardHeart를 조회할 회원의 ID
     * @return 특정 회원의 BoardHeart 목록
     */
    List<BoardHeart> getBoardHeartsByMember(int memberId);
}
