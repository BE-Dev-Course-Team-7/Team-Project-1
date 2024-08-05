package day0805.src.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberImpl implements MemberDao{
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    @Override
    public int join(Member member) throws SQLException {
        int result = 0;
        try {
            String SQL = "INSERT INTO MEMBER(ACCOUNT,PASSWORD,NAME,LOCATION) VALUES(?,?,?,?)";
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, member.getAccount());
            pstmt.setString(2, member.getPassword());
            pstmt.setString(3, member.getName());
            pstmt.setString(4, member.getLocation());
            result = pstmt.executeUpdate();
        } catch(SQLException e) {
            System.out.println("insert error");
            throw e;
        } finally {
            DBUtil.close(conn, pstmt);
        }
        return result;
    }

    @Override
    public Member login(String account, String password) throws SQLException {
        Member member = null;
        try {
            String sql = "SELECT ACCOUNT FROM MEMBER WHERE ACCOUNT=" + account + " AND PASSWORD=" + password;
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            member = new Member();
        } catch (SQLException e) {
            System.out.println("login error");
            throw e;
        } finally {
            DBUtil.close(rs, conn, pstmt);
        }
        return member;
    }

    @Override
    public int updatePassword(Member member, String newPassword) throws SQLException {
        int result = 0;
        try {
            String SQL = "UPDATE MEMBER SET PASSWORD=? WHERE ID=?";
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, newPassword);
            pstmt.setInt(2, member.getId());
            result = pstmt.executeUpdate();
        } catch(SQLException e) {
            System.out.println("updatePassword error");
            throw e;
        } finally {
            DBUtil.close(conn, pstmt);
        }
        return result;
    }

    @Override
    public int updateName(Member member, String newName) throws SQLException {
        int result = 0;
        try {
            String SQL = "UPDATE MEMBER SET NAME=? WHERE ID=?";
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, newName);
            pstmt.setInt(2, member.getId());
            result = pstmt.executeUpdate();
        } catch(SQLException e) {
            System.out.println("updateName error");
            throw e;
        } finally {
            DBUtil.close(conn, pstmt);
        }
        return result;
    }

    @Override
    public int updateLocation(Member member, String newLocation) throws SQLException {
        int result = 0;
        try {
            String SQL = "UPDATE MEMBER SET LOCATION=? WHERE ID=?";
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, newLocation);
            pstmt.setInt(2, member.getId());
            result = pstmt.executeUpdate();
        } catch(SQLException e) {
            System.out.println("updateLocation error");
            throw e;
        } finally {
            DBUtil.close(conn, pstmt);
        }
        return result;
    }

    @Override
    public int deleteMember(Member member) throws SQLException {
       int result = 0;
       try {
           String SQL = "DELETE FROM MEMBER WHERE ID=?";
           conn = DBUtil.getConnection();
           pstmt = conn.prepareStatement(SQL);
           pstmt.setInt(1, member.getId());
           result = pstmt.executeUpdate();

       } catch(SQLException e) {
           System.out.println("deleteMember error");
           throw e;
       } finally {
           DBUtil.close(conn, pstmt);
       }
        return result;
    }
}
