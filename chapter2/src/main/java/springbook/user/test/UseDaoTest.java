package springbook.user.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import springbook.user.dao.UserDao_DataSource;
import springbook.user.domain.User;

import java.sql.SQLException;

/**
 * 2-21 main() 메소드로 작성된 테스트
 */
public class UseDaoTest {
    // 자바에서 가장 손쉽게 실행 가능한 main() 메소드를 이용한다.
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext-2-1.xml");

        // 테스트할 대상인 UserDao의 오브젝트를 가져와 메소드를 호출한다.
        UserDao_DataSource dao = context.getBean("userDao", UserDao_DataSource.class);

        // 테스터에 사용할 입력 값(User 오브젝트)을 직접 코드에서 만들어 넣어준다.
        User user = new User();
        user.setId("user");
        user.setName("백기선");
        user.setPassword("married");

        dao.add(user);

        // 2-2 수정 전 테스트 코드
        // 테스트 결과를 직접 콘솔에 출력한다.
        System.out.println(user.getId() + " 등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());

        // 각 단계 작업이 에러 없이 끝나면 콘솔에 성공 메시지로 출력한다.
        System.out.println(user2.getId() + " 조회 성공");

        // 2-3드 수정 후 테스트 코드
        if (!user.getName().equals(user2.getName())) {
            System.out.println("테스트 실패 (name)");
        } else if (!user.getPassword().equals(user2.getPassword())) {
            System.out.println("테스트 실패 (password)");
        } else {
            System.out.println("조회 테스트 성공");
        }

    }
}
