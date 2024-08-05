package day0805.boardHeart.src;

public class BoardHeart {
    private int id;
    private int boardId;
    private int memberId;

    public BoardHeart(int id, int boardId, int memberId) {
        this.id = id;
        this.boardId = boardId;
        this.memberId = memberId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
}