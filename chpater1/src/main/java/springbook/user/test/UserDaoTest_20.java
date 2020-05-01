package springbook.user.test;

import springbook.user.dao.UserDao_RuntimeDependent;
import springbook.user.factory.DaoFactory_Basic2;

/**
 * 1-20 직접 생성한 DaoFactory 오브젝트 출력 코드
 */
public class UserDaoTest_20 {
    public static void main(String[] args) {
        DaoFactory_Basic2 facotry = new DaoFactory_Basic2();
        UserDao_RuntimeDependent dao1 = facotry.userDao();
        UserDao_RuntimeDependent dao2 = facotry.userDao();
        System.out.println(dao1);
        System.out.println(dao2);
        System.out.println(dao1 == dao2);
    }


}
