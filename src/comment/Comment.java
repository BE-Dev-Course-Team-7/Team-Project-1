package comment;

public class Comment {
    private int id;
    private int boardId;
    private int memberId;
    private String content;

    public Comment(){}

    public Comment(int board_id, int member_id, String content) {
        this.boardId = board_id;
        this.memberId = member_id;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "comment.Comment{" +
                "id=" + id +
                ", boardId=" + boardId +
                ", memberId=" + memberId +
                ", content='" + content + '\'' +
                '}';
    }
}
