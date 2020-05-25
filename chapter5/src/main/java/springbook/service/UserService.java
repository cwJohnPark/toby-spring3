package springbook.service;

import springbook.dao.UserDao;
import springbook.domain.Level;
import springbook.domain.User;

import java.util.List;

public class UserService {
    UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * 5-18 사용자 레벨 업그레이드 메소드
     */
    public void upgradeLevels() throws Exception {
        List<User> users = userDao.getAll();
        for(User user : users) {
            Boolean changed = null; // 레벨의 변화가 있는지를 확인하는 플래그
            if (user.getLevel() == Level.BASIC && user.getLogin() >= 50) {
                user.setLevel(Level.SILVER);
                changed = true;
            } else if(user.getLevel() == Level.SILVER && user.getRecommend() >= 30) {
                user.setLevel(Level.GOLD);
                changed = true; // 레벨 변경 플래그 설정
            } else if(user.getLevel() == Level.GOLD) { // GOLD 이상의 레벨은 없다.
                changed = false;
            } else {
                changed = false;
            }

            // 레벨의 변경이 있는 경우에만 update() 호출
            if(changed) {
                userDao.update(user);
            }

        }
    }
}
