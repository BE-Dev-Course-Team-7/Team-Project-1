package member;

import java.sql.SQLException;

public interface MemberDao {
    int join(Member member) throws SQLException;
    Member login(String account, String password) throws SQLException;
    int updatePassword(Member member, String newPassword) throws SQLException;
    int updateName(Member member, String newName) throws SQLException;
    int updateLocation(Member member, String newLocation) throws SQLException;
    int deleteMember(Member member) throws SQLException;
    String serachMember(int id);
}
