
package member;

import java.sql.Timestamp;

public class Member {
    private int id;
    private String account;
    private String password;
    private String name;
    private String location;
    private Timestamp joinDate;

    public Member() {

    }

    public Member(String account, String password, String name, String location) {
        this.account = account;
        this.password = password;
        this.name = name;
        this.location = location;
        this.joinDate = new Timestamp(System.currentTimeMillis());
    }

    public int getId() {
        return id;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public Timestamp getJoinDate() {
        return joinDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setJoinDate(Timestamp joinDate) {
        this.joinDate = joinDate;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", joinDate=" + joinDate +
                '}';
    }
}


