package order;

public interface OrderDao {

    /**
     * 주문을 추가하는 메서드
     *
     * @param order 추가할 주문 정보
     * @return 추가된 주문의 ID
     */
    int insertOrder(Order order);


    /**
     * 멤버 ID로 주문 내역을 조회하는 메서드
     *
     * @param memberId 주문 내역을 조회할 멤버의 ID
     * @return 멤버 ID에 해당하는 주문 목록
     */
    public Order[] showOrderByMemberId(int memberId);


    /**
     * 전체 주문 내역을 조회하는 메서드
     *
     * @return 전체 주문 목록
     */
    public Order[] showAllOrder();
}
