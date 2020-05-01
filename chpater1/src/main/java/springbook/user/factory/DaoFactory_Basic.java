package springbook.user.factory;

import springbook.user.dao.UserDao_RuntimeDependent;
import springbook.user.connection.ConnectionMaker;
import springbook.user.connection.DConnectionMaker;

/**
 * 1-14 UserDao의 생성 책임을 맡은 팩토리 클래스
 */
public class DaoFactory_Basic {
    public UserDao_RuntimeDependent userDao() {
        ConnectionMaker connectionMaker = new DConnectionMaker();
        UserDao_RuntimeDependent userDao = new UserDao_RuntimeDependent(connectionMaker);
        return userDao;
    }
}
