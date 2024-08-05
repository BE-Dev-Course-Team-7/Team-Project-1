package day0805.board;

public class Board {
    private int id;
    private int memberId;
    private String title;
    private int price;
    private String content;
    private String category;
    private String status;

    public Board(int memberId, String title, int price, String content, String category, String status) {
        this.memberId = memberId;
        this.title = title;
        this.price = price;
        this.content = content;
        this.category = category;
        this.status = status;
    }
}
