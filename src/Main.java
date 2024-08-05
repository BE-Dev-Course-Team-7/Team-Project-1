import board.BoardCreateDto;
import board.BoardImpl;
import board.BoardListDto;
import boardHeart.BoardHeart;
import boardHeart.BoardHeartImpl;
import comment.Comment;
import comment.CommentImpl;
import member.Member;
import member.MemberImpl;
import order.Order;
import order.OrderImpl;

import java.sql.SQLException;
import java.util.Scanner;


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
                case 6 -> viewBoards();
                case 7 -> addComment(scanner);
                case 8 -> addHeart(scanner);
                case 9 -> viewOrders();
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
                6. 게시글 조회
                7. 댓글 작성
                8. 관심 목록 추가
                9. 주문 목록 조회
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

    private static void viewBoards() {
        BoardListDto[] boards = boardService.getAllBoards();
        for (BoardListDto board : boards) {
            System.out.printf("ID: %d, 제목: %s, 가격: %d, 카테고리: %s, 상태: %s, 작성일: %s%n",
                    board.getId(), board.getTitle(), board.getPrice(), board.getCategory(), board.getStatus(), board.getPostDate());
        }
    }

    private static void addComment(Scanner scanner) {
        if (currentMember == null) {
            System.out.println("로그인 후 이용해주세요.");
            return;
        }

        System.out.print("게시글 ID를 입력하세요: ");
        int boardId = Integer.parseInt(scanner.nextLine());
        System.out.print("댓글 내용을 입력하세요: ");
        String content = scanner.nextLine();

        Comment comment = new Comment(boardId, currentMember.getId(), content);
        commentService.addComment(comment);
        System.out.println("댓글이 작성되었습니다.");
    }

    private static void addHeart(Scanner scanner) {
        if (currentMember == null) {
            System.out.println("로그인 후 이용해주세요.");
            return;
        }

        System.out.print("게시글 ID를 입력하세요: ");
        int boardId = Integer.parseInt(scanner.nextLine());

        BoardHeart boardHeart = new BoardHeart(0, boardId, currentMember.getId());
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

    private static void deleteBoard(Scanner scanner) {
        if (currentMember == null) {
            System.out.println("로그인 후 이용해주세요.");
            return;
        }

        System.out.print("삭제할 게시글 ID를 입력하세요: ");
        int boardId = Integer.parseInt(scanner.nextLine());
        boardService.deleteBoard(boardId);
        System.out.println("게시글이 삭제되었습니다.");
    }

    private static void exit() {
        System.out.println("프로그램을 종료합니다.");
        System.exit(0);
    }
}