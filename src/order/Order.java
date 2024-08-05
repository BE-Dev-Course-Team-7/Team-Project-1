package order;

import java.sql.Timestamp;


public class Order {

    private int id;

    private int boardId;
    private int memberId;
    private int price;
    private Timestamp orderDate;



    public int getBoardId() {
        return boardId;
    }


    public Order(int id, int boardId, int memberId, int price, Timestamp orderDate) {
        this.id = id;
        this.boardId = boardId;
        this.memberId = memberId;
        this.price = price;
        this.orderDate = orderDate;
    }

    public Order(int boardId, int memberId, int price) {
        this.id = id;
        this.boardId = boardId;
        this.memberId = memberId;
        this.price = price;
        this.orderDate = orderDate;
    }

    public int getMemberId() {
        return memberId;
    }


    public int getPrice() {
        return price;
    }


    public Timestamp getOrderDate() {
        return orderDate;
    }


}


