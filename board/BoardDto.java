package day0805.board;

import java.time.LocalDateTime;

public class BoardDto {
    private int id;
    private int memberId;
    private String title;
    private int price;
    private String content;
    private String category;
    private String status;
    private LocalDateTime PostDate;


    public BoardDto(int id, int memberId, String title, int price, String content, String category, String status, LocalDateTime postDate) {
        this.id = id;
        this.memberId = memberId;
        this.title = title;
        this.price = price;
        this.content = content;
        this.category = category;
        this.status = status;
        PostDate = postDate;
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

    public LocalDateTime getPostDate() {
        return PostDate;
    }
}

