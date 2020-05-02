package springbook.user.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import springbook.user.connection.DConnectionMaker;
import springbook.user.dao.UserDao_SetterDI;

/**
 * 1-40 XML을 이용한 애플리케이션 컨텍스트
 */
public class UserDaoTest_XML {
    public static void main(String[] args) {
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext-1-40.xml");

        UserDao_SetterDI dao = context.getBean("userDao", UserDao_SetterDI.class);
        DConnectionMaker ccm = context.getBean("connectionMaker", DConnectionMaker.class);

        System.out.println(dao);
        System.out.println(ccm);
    }
}
