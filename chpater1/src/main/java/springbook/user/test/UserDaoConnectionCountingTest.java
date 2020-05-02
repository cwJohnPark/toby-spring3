package springbook.user.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbook.user.connection.CountingConnectionMaker;
import springbook.user.dao.UserDao_RuntimeDependent;
import springbook.user.factory.CountingDaoFactory;

/**
 * 1-32 CountingConnectionMaker에 대한 테스트 클래스
 */
public class UserDaoConnectionCountingTest {
    public static void main(String[] args) {
        // hide cglib illegal reflective access
        System.err.close();
        System.setErr(System.out);
        //--
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
        UserDao_RuntimeDependent dao = context.getBean("userDao", UserDao_RuntimeDependent.class);

        // DAO 사용 코드
        /// DL(의존관계 검색)을 사용하면 이름을 이용해 어떤 빈이든 가져올 수 있다.
        CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
        System.out.println("Connection counter : " + ccm.getCounter());
    }
}
