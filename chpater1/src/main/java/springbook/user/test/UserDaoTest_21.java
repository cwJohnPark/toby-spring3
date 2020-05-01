package springbook.user.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbook.user.dao.UserDao_RuntimeDependent;
import springbook.user.factory.DaoFactory_SpringCtx;

/**
 * 1-21 스프링 컨텍스트로부터 가져온 오브젝트 출력 코드
 */
public class UserDaoTest_21 {
    public static void main(String[] args) {
        // hide cglib illegal reflective access
        System.err.close();
        System.setErr(System.out);
        //--

        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory_SpringCtx.class);

        UserDao_RuntimeDependent dao3 = context.getBean("userDao", UserDao_RuntimeDependent.class);
        UserDao_RuntimeDependent dao4 = context.getBean("userDao", UserDao_RuntimeDependent.class);

        System.out.println(dao3);
        System.out.println(dao4);
        System.out.println(dao3 == dao4);
    }
}
