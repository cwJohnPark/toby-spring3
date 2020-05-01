package springbook.user.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbook.user.dao.UserDao_RuntimeDependent;
import springbook.user.factory.DaoFactory_SpringCtx;

/**
 * 1-19 애플리케이션 컨텍스트를 적용한 UserDaoTest
 */
public class UserDaoTest_19 {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory_SpringCtx.class);
        UserDao_RuntimeDependent dao = context.getBean("userDao", UserDao_RuntimeDependent.class);

    }
}
