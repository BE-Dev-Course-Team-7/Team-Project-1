import board.BoardCreateDto;
import board.BoardDto;
import board.BoardImpl;
import board.BoardListDto;
import boardHeart.BoardHeart;
import boardHeart.BoardHeartImpl;
import comment.Comment;
import comment.CommentImpl;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import member.Member;
import member.MemberImpl;
import order.Order;
import order.OrderImpl;


public class Main {
    private static final MemberImpl memberService = new MemberImpl();
    private static final BoardImpl boardService = new BoardImpl();
    private static final CommentImpl commentService = new CommentImpl();
    private static final BoardHeartImpl boardHeartService = (BoardHeartImpl) BoardHeartImpl.getInstance();
    private static final OrderImpl orderService = new OrderImpl();

    private static Member currentMember = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMainMenu();
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> registerMember(scanner);
                case 2 -> login(scanner);
                case 3 -> updateMemberInfo(scanner);
                case 4 -> logout();
                case 5 -> postBoard(scanner);
                case 6 -> viewBoards(scanner);
                case 7 -> viewHearts();
                case 8 -> viewOrders();
                case 9 -> updateBoard(scanner);
                case 10 -> deleteBoard(scanner);
                case 11 -> exit();
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("""
                1. 회원가입
                2. 로그인
                3. 회원 정보 수정
                4. 로그아웃
                5. 게시글 작성
                6. 게시글 목록 조회
                7. 관심 목록 조회
                8. 주문 목록 조회
                9. 게시글 수정
                10. 게시글 삭제
                11. 종료
                """);
    }

    private static void registerMember(Scanner scanner) {
        System.out.print("아이디를 입력하세요: ");
        String account = scanner.nextLine();
        System.out.print("비밀번호를 입력하세요: ");
        String password = scanner.nextLine();
        System.out.print("이름을 입력하세요: ");
        String name = scanner.nextLine();
        System.out.print("사는 지역을 입력하세요 (예시: 서울시 마포구): ");
        String location = scanner.nextLine();

        Member member = new Member(account, password, name, location);
        try {
            memberService.join(member);
            System.out.println("회원가입이 완료되었습니다!");
        } catch (SQLException e) {
            System.out.println("회원가입에 실패했습니다: " + e.getMessage());
        }
    }

    private static void login(Scanner scanner) {
        System.out.print("아이디를 입력하세요: ");
        String account = scanner.nextLine();
        System.out.print("비밀번호를 입력하세요: ");
        String password = scanner.nextLine();

        try {
            currentMember = memberService.login(account, password);
            if (currentMember != null) {
                System.out.println("로그인 되었습니다.");
            } else {
                System.out.println("아이디 또는 비밀번호가 일치하지 않습니다.");
            }
        } catch (SQLException e) {
            System.out.println("로그인에 실패했습니다: " + e.getMessage());
        }
    }

    private static void updateMemberInfo(Scanner scanner) {
        if (currentMember == null) {
            System.out.println("로그인 후 이용해주세요.");
            return;
        }

        System.out.println("""
                1. 비밀번호 변경
                2. 이름 변경
                3. 사는 지역 변경
                """);
        int choice = Integer.parseInt(scanner.nextLine());
        try {
            switch (choice) {
                case 1 -> {
                    System.out.print("새로운 비밀번호를 입력하세요: ");
                    String newPassword = scanner.nextLine();
                    memberService.updatePassword(currentMember, newPassword);
                }
                case 2 -> {
                    System.out.print("새로운 이름을 입력하세요: ");
                    String newName = scanner.nextLine();
                    memberService.updateName(currentMember, newName);
                }
                case 3 -> {
                    System.out.print("새로운 지역을 입력하세요: ");
                    String newLocation = scanner.nextLine();
                    memberService.updateLocation(currentMember, newLocation);
                }
                default -> System.out.println("잘못된 선택입니다.");
            }
            System.out.println("정보가 수정되었습니다.");
        } catch (SQLException e) {
            System.out.println("정보 수정에 실패했습니다: " + e.getMessage());
        }
    }

    private static void logout() {
        currentMember = null;
        System.out.println("로그아웃 되었습니다.");
    }

    private static void postBoard(Scanner scanner) {
        if (currentMember == null) {
            System.out.println("로그인 후 이용해주세요.");
            return;
        }
        System.out.print("제목을 입력하세요: ");
        String title = scanner.nextLine();
        System.out.print("가격을 입력하세요: ");
        int price = Integer.parseInt(scanner.nextLine());
        System.out.print("내용을 입력하세요: ");
        String content = scanner.nextLine();
        System.out.print("카테고리를 입력하세요: ");
        String category = scanner.nextLine();
        String status = "ONSALE";

        BoardCreateDto boardCreateDto = new BoardCreateDto(currentMember.getId(), title, price, content, category, status);
        boardService.createBoard(boardCreateDto);
        System.out.println("게시글이 작성되었습니다.");
    }

    private static void viewBoards(Scanner scanner) {
        while(true) {
            BoardListDto[] boards = boardService.getAllBoards();
            for (BoardListDto board : boards) {
                System.out.printf("ID: %d, 제목: %s, 가격: %d, 카테고리: %s, 상태: %s, 작성일: %s%n",
                        board.getId(), board.getTitle(), board.getPrice(), board.getCategory(), board.getStatus(), board.getPostDate());
            }
            System.out.println("""
                1. 게시글 이동
                2. 판매 중인 글만 보기
                3. 게시글 검색
                4. 메인화면 돌아가기
                """);
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> moveboard(scanner);
                case 2 -> viewsellboard(scanner);
                case 3 -> searchboard(scanner);
                case 4 -> {
                    return;
                }
            }
        }
    }

    private static void moveboard(Scanner scanner) {
        System.out.println("이동할 게시글의 번호를 입력하세요 >>");
        int choice = Integer.parseInt(scanner.nextLine());
        BoardDto board = boardService.getBoard(choice);
        Comment[] comment = commentService.getAllComments(board.getId());
        showboard(board);
        showComment(comment);
        System.out.println("""
                1. 관심 목록에 저장
                2. 댓글 달기
                3. 댓글 수정
                4. 댓글 삭제
                5. 구매하기
                6. 게시글 목록 돌아가기
                """);
        choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1 -> addHeart(board);
            case 2 -> addComment(scanner, board);
            case 3 -> updateComment(scanner, comment);
            case 4 -> deleteComment(scanner, comment);
            case 5 -> purchase(board);
            case 6 -> {
                return;
            }
        }
    }

    private static void showboard(BoardDto board){
        System.out.println("----------------------------------------------------------");
        System.out.println("게시글 ID : " + board.getId());
        System.out.println("제목 : " + board.getTitle());
        System.out.println("작성자 : " + memberService.serachMember(board.getMemberId()));
        System.out.println("판매가 : " + board.getPrice());
        System.out.println("카테고리 : " + board.getCategory());
        System.out.println("작성일 : " + board.getPostDate());
        System.out.println("내용 : " + board.getContent());
        System.out.println("----------------------------------------------------------");
    }
    private static void showComment(Comment[] comment){
        for (Comment c : comment) {
            System.out.println(memberService.serachMember(c.getMemberId()) + " : "+ c.getContent());
        }
    }

    private static void viewsellboard(Scanner scanner){
        BoardListDto[] boards = boardService.getOnSaleBoards();
        for (BoardListDto board : boards) {
            System.out.printf("ID: %d, 제목: %s, 가격: %d, 카테고리: %s, 상태: %s, 작성일: %s%n",
                    board.getId(), board.getTitle(), board.getPrice(), board.getCategory(), board.getStatus(), board.getPostDate());
        }
        System.out.println("""
                1. 게시글 이동
                2. 돌아가기
                """);
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1 -> moveboard(scanner);
            case 2 -> {
                return;
            }
        }
    }

    private static void searchboard(Scanner scanner) {
        String str = "";
        BoardListDto[] boards;
        System.out.println("""
                    1. 제목으로 검색
                    2. 카테고리로 검색
                    3. 글쓴이로 검색
                    """);
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1 -> {
                System.out.print("검색할 제목을 입력하세요: ");
                str = scanner.nextLine();
                boards = boardService.getBoardsByTitle(str);
            }
            case 2 -> {
                System.out.print("검색할 카테고리를 입력하세요: ");
                str = scanner.nextLine();
                boards = boardService.getBoardsByCategory(str);
            }
            case 3 -> {
                System.out.print("검색할 글쓴이를 입력하세요: ");
                str = scanner.nextLine();
                boards = boardService.getBoardsByWriter(str);
            }
            default -> {
                System.out.println("잘못된 입력입니다.");
                return;
            }
        }

        for (BoardListDto board : boards) {
            System.out.printf("ID: %d, 제목: %s, 가격: %d, 카테고리: %s, 상태: %s, 작성일: %s%n",
                    board.getId(), board.getTitle(), board.getPrice(), board.getCategory(), board.getStatus(), board.getPostDate());
        }
        System.out.println("""
                1. 게시글 이동
                2. 돌아가기
                """);
        choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1 -> moveboard(scanner);
            case 2 -> {
                return;
            }
        }
    }




    private static void addComment(Scanner scanner, BoardDto board) {
        if (currentMember == null) {
            System.out.println("로그인 후 이용해주세요.");
            return;
        }
        System.out.print("댓글 내용을 입력하세요: ");
        String content = scanner.nextLine();
        Comment comment = new Comment(board.getId(), currentMember.getId(), content);
        commentService.addComment(comment);
        System.out.println("댓글이 작성되었습니다.");
    }

    private static void updateComment(Scanner scanner, Comment[] comment) {
        if (currentMember == null) {
            System.out.println("로그인 후 이용해주세요.");
            return;
        }
        System.out.print("수정할 댓글 번호를 입력하세요: ");
        int num = Integer.parseInt(scanner.nextLine());
        System.out.print("댓글 내용을 입력하세요: ");
        String content = scanner.nextLine();
        try {
            comment[num-1].setContent(content);
            if(commentService.updateComment(comment[num-1], currentMember) == 0)
                System.out.println("작성자만 할 수 있습니다.");
        } catch(ArrayIndexOutOfBoundsException e){
            System.out.println("범위를 벗어났습니다");
            return;
        }
        System.out.println("댓글 수정이 작성되었습니다.");
    }

    private static void deleteComment(Scanner scanner, Comment[] comment) {
        if (currentMember == null) {
            System.out.println("로그인 후 이용해주세요.");
            return;
        }
        System.out.print("삭제할 댓글 번호를 입력하세요: ");
        int num = Integer.parseInt(scanner.nextLine());
        try {
            if(commentService.deleteComment(comment[num-1], currentMember) == 0)
                System.out.println("작성자만 할 수 있습니다.");
        } catch(ArrayIndexOutOfBoundsException e){
            System.out.println("범위를 벗어났습니다");
            return;
        }
        System.out.println("댓글 삭제가 완료되었습니다.");
    }

    private static void purchase(BoardDto board){
        orderService.insertOrder(new Order(board.getId(), currentMember.getId(), board.getPrice()));
        System.out.println("구매가 완료되었습니다.");
    }


    private static void addHeart(BoardDto board) {
        if (currentMember == null) {
            System.out.println("로그인 후 이용해주세요.");
            return;
        }

        BoardHeart boardHeart = new BoardHeart(board.getId(), currentMember.getId());
        boardHeartService.addBoardHeart(boardHeart);
        System.out.println("관심 목록에 추가되었습니다.");
    }

    private static void viewOrders() {
        if (currentMember == null) {
            System.out.println("로그인 후 이용해주세요.");
            return;
        }

        Order[] orders = orderService.showOrderByMemberId(currentMember.getId());
        for (Order order : orders) {
            System.out.printf("주문 ID: %d, 게시글 ID: %d, 가격: %d, 주문일: %s%n",
                    order.getBoardId(), order.getBoardId(), order.getPrice(), order.getOrderDate());
        }
    }

    private static void viewHearts() {
        if (currentMember == null) {
            System.out.println("로그인 후 이용해주세요.");
            return;
        }

        List<BoardHeart> orders = boardHeartService.getBoardHeartsByMember(currentMember.getId());
        for (BoardHeart order : orders) {
            BoardDto board = boardService.getBoard(order.getBoardId());
            System.out.printf("ID: %d, 제목: %s, 가격: %d, 카테고리: %s, 상태: %s, 작성일: %s%n",
                    board.getId(), board.getTitle(), board.getPrice(), board.getCategory(), board.getStatus(), board.getPostDate());
        }
    }

    private static void updateBoard(Scanner scanner){
        String str = "";
        BoardListDto boards;
        int x;

        if (currentMember == null) {
            System.out.println("로그인 후 이용해주세요.");
            return;
        }

        System.out.print("수정힐 게시글 ID를 입력하세요: ");
        int boardId = Integer.parseInt(scanner.nextLine());

        if(boardService.getBoard(boardId) == null || boardService.getBoard(boardId).getMemberId() != currentMember.getId()){
            System.out.println("글 작성자만 수정할 수 있습니다.");
            return;
        }
        
        System.out.println("""
                    1. 제목 수정
                    2. 가격 수정
                    3. 내용 수정
                    """);
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1 -> {
                System.out.print("수정할 제목을 입력하세요 : ");
                str = scanner.nextLine();
                boardService.updateBoardTitle(boardId, str);
            }
            case 2 -> {
                System.out.print("수정할 가격을 입력하세요: ");
                x = Integer.parseInt(scanner.nextLine());
                boardService.updateBoardPrice(boardId, x);
            }
            case 3 -> {
                System.out.print("수정할 내용을 입력하세요: ");
                str = scanner.nextLine();
                boardService.updateBoardContent(boardId, str);
            }
            default -> {
                System.out.println("잘못된 입력입니다.");
                return;
            }
        }

    }


    private static void deleteBoard(Scanner scanner) {
        if (currentMember == null) {
            System.out.println("로그인 후 이용해주세요.");
            return;
        }

        System.out.print("삭제할 게시글 ID를 입력하세요: ");
        int boardId = Integer.parseInt(scanner.nextLine());
        if(boardService.getBoard(boardId) == null || boardService.getBoard(boardId).getMemberId() != currentMember.getId()){
            System.out.println("글 작성자만 삭제할 수 있습니다.");
            return;
        }
        boardService.deleteBoard(boardId);
        System.out.println("게시글이 삭제되었습니다.");
    }

    private static void exit() {
        System.out.println("프로그램을 종료합니다.");
        System.exit(0);
    }
}