package springbook.user.test;

import springbook.user.dao.UserDao_RuntimeDependent;
import springbook.user.factory.DaoFactory_Basic2;

/**
 * 1-15 팩토리를 사용하도록 수정한 UserDaoTest
 */
public class UserDaoTest_15 {
    public static void main(String[] args) {
        UserDao_RuntimeDependent dao = new DaoFactory_Basic2().userDao();
    }
}
