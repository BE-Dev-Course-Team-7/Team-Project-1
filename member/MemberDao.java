package day0805.member;

import java.sql.SQLException;

public interface MemberDao {
    int join(Member member) throws SQLException;
    Member login(String account, String password) throws SQLException;
    int updateMember(Member member, String category) throws SQLException;
    int deleteMember(Member member) throws SQLException;

}
