package springbook.user.dao;

import springbook.user.domain.User;

import java.sql.*;

/**
 * UserDao의 관심사항
 * 1. DB와 연결을 위한 커넥션을 어떻게 가져올까
 * 2. 사용자 등록을 위해 DB에 보낼 SQL 문장을 담을 Statement를 만들고 실행하는 것
 * 3. 작업이 끝나면 리소스를 반환
 */
// 1-2 JDBC를 이용한 등록과 조회 기능이 있는 UserDao 클래스
public class UserDao_NoInheritance {
    // 1-3 테스트용 main() 메소드
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao_NoInheritance dao = new UserDao_NoInheritance();
        User user = new User();
        user.setId("whiteship");
        user.setName("백기선");
        user.setPassword("married");

        dao.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());
        System.out.println(user2.getId() + " 조회 성공");

    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        //Class.forName("com.mysql.cj.jdbc.Driver");
        //Connection c = DriverManager.getConnection("jdbc:mysql://localhost/springbook", "spring", "book");
        Connection c = getConnection();

        PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) value (?, ?, ?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    // 1-4 getConnection() 메소드를 추출하여 중복을 제거한 UserDao
    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost/springbook", "spring", "book");
        return c;
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        //Class.forName("com.mysql.cj.jdbc.Driver");
        //Connection c = DriverManager.getConnection("jdbc:mysql://localhost/springbook", "spring", "book");
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        c.close();

        return user;
    }
}
