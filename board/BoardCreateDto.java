package day0805.board;

public class BoardCreateDto {
    private int memberId;
    private String title;
    private int price;
    private String content;
    private String category;
    private String status;

    public BoardCreateDto(int memberId, String title, int price, String content, String category, String status) {
        this.memberId = memberId;
        this.title = title;
        this.price = price;
        this.content = content;
        this.category = category;
        this.status = status;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    public String getContent() {
        return content;
    }

    public String getCategory() {
        return category;
    }

    public String getStatus() {
        return status;
    }
}
