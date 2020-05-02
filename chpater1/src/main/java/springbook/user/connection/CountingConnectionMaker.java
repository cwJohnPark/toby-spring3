package springbook.user.connection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 1-30 연결횟수 카운팅 기능이 있는 클래스
 */
public class CountingConnectionMaker implements ConnectionMaker {
    int counter = 0;
    private ConnectionMaker realConnectionMaker;

    public CountingConnectionMaker(ConnectionMaker realConnectionMaker) {
        this.realConnectionMaker = realConnectionMaker;
    }

    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        this.counter++;
        // 직접 DB 커넥션을 만들지 않는다.
        return realConnectionMaker.makeConnection();
    }

    public int getCounter() {
        return this.counter;
    }
}
