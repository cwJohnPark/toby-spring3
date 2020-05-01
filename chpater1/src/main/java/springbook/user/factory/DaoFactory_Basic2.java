package springbook.user.factory;

import springbook.user.connection.ConnectionMaker;
import springbook.user.connection.DConnectionMaker;
import springbook.user.dao.UserDao_RuntimeDependent;

/**
 * 1-17 생성 오브젝트 코드 수정
 */
public class DaoFactory_Basic2 {
    public UserDao_RuntimeDependent userDao() {
        return new UserDao_RuntimeDependent(connectionMaker());
    }
    public UserDao_RuntimeDependent accountDao() {
        return new UserDao_RuntimeDependent(connectionMaker());
    }
    public UserDao_RuntimeDependent messageDao() {
        return new UserDao_RuntimeDependent(connectionMaker());
    }

    private ConnectionMaker connectionMaker() {
        return new DConnectionMaker();
    }
}
