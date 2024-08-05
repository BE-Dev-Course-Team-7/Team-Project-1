package day0805.board;


import java.time.LocalDateTime;

public class BoardListDto {
    private int id;
    private int memberId;
    private String title;
    private int price;
    private String category;
    private String status;
    private LocalDateTime postDate;


    public BoardListDto(int id, int memberId, String title, int price, String category, String status, LocalDateTime postDate) {
        this.id = id;
        this.memberId = memberId;
        this.title = title;
        this.price = price;
        this.category = category;
        this.status = status;
        this.postDate = postDate;
    }
}

