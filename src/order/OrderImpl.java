package order;

import jdbc.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderImpl implements OrderDao{

    @Override
    public int insertOrder(Order order) {
        String insertOrderSql = "INSERT INTO ORDERS (BOARD_ID, MEMBER_ID, TOTAL_PRICE, ORDER_DATE) VALUES (?, ?, ?, ?)";
        String updateBoardSql = "UPDATE BOARD SET STATUS = '판매 완료' WHERE ID = ?";

        Connection con = null;
        PreparedStatement insertOrderPstmt = null;
        PreparedStatement updateBoardPstmt = null;
        int generatedId =  0;

        try {
            con = DatabaseConnection.getConnection();
            con.setAutoCommit(false); // 트랜잭션 시작

            // 기본키를 알아야 board에 사용가능
            insertOrderPstmt = con.prepareStatement(insertOrderSql, PreparedStatement.RETURN_GENERATED_KEYS);
            insertOrderPstmt.setInt(1, order.getBoardId());
            insertOrderPstmt.setInt(2, order.getMemberId());
            insertOrderPstmt.setInt(3, order.getPrice());
            insertOrderPstmt.setTimestamp(4, order.getOrderDate());
            int row = insertOrderPstmt.executeUpdate();

            if (row > 0) {
                ResultSet keys = insertOrderPstmt.getGeneratedKeys(); // 키 가져오기
                if (keys.next()) {
                    generatedId = keys.getInt(1);
                }
            }

            updateBoardPstmt = con.prepareStatement(updateBoardSql);
            updateBoardPstmt.setInt(1, order.getBoardId());
            updateBoardPstmt.executeUpdate();
            System.out.println("판매되었습니다. 상태가 판매 완료로 변경됩니다.");

            con.commit(); // 트랜잭션 커밋

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {

            DatabaseConnection.close(con);
            if (insertOrderSql != null) {
                try {
                    insertOrderPstmt.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (updateBoardPstmt != null) {
                try {
                    insertOrderPstmt.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return generatedId;
    }

    @Override
    public Order[] showOrderByMemberId(int member_Id) {
        List<Order> orderList = new ArrayList<>();
        String sql = "SELECT * FROM `ORDERS` WHERE MEMBER_ID = ?";

        Connection con = null;
        try {
            con = DatabaseConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, member_Id);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int boardId = rs.getInt("board_id");
                int memberId = rs.getInt("member_id");
                int price = rs.getInt("total_price");
                Timestamp orderDate = rs.getTimestamp("order_date");

                Order order = new Order(id, boardId, memberId, price, orderDate);
                orderList.add(order);

                System.out.println("Order ID: " + id +
                        ", Board ID: " + boardId +
                        ", Member ID: " + memberId +
                        ", Price: " + price +
                        ", Order Date: " + orderDate);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderList.toArray(new Order[0]);
    }

    @Override
    public Order[] showAllOrder() {
        List<Order> orderList = new ArrayList<>();
        String sql = "SELECT * FROM ORDERS";

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("id");
                int boardId = rs.getInt("board_id");
                int memberId = rs.getInt("member_id");
                int price = rs.getInt("total_price");
                Timestamp orderDate = rs.getTimestamp("order_date");

                Order order = new Order(id, boardId, memberId, price, orderDate);
                orderList.add(order);


                System.out.println("Order ID: " + id +
                        ", Board ID: " + boardId +
                        ", Member ID: " + memberId +
                        ", Price: " + price +
                        ", Order Date: " + orderDate);


            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderList.toArray(new Order[0]);
    }
}
