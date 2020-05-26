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
     * 5-28 간결해진 upgradeLevel()
     */
    private void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
    }

    /**
     * 5-23 기본 작업 흐름만 남겨둔 upgradeLevels()
     */
    public void upgradeLevels() {
        List<User> users = userDao.getAll();
        for(User user : users) {
            if(canUpgradeLevel(user)) {
                upgradeLevel(user);
            }
        }
    }

    /**
     * 5-25 레벨 업그레이드 작업 메소드
     */
    private void upgradeLevel_NotOrdered(User user) {
        if (user.getLevel() == Level.BASIC) user.setLevel(Level.SILVER);
        else if (user.getLevel() == Level.SILVER) user.setLevel(Level.GOLD);
        userDao.update(user);
    }

    /**
     * 5-23 업그레이드 가능 확인 메소드
     */
    private boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();
        switch(currentLevel) {
            // 레벨 별로 구분하여 조건을 판단한다.
            case BASIC : return (user.getLogin() >= 50);
            case SILVER : return (user.getRecommend() >= 30);
            case GOLD : return false;
            // 현재 로직에서 다룰 수 없는 레벨이 주어지면 예외를 발생시킨다.
            // 새로운 레벨이 추가되고 로직을 수정하지 않으면 에러가 나서 확인할 수 있다.
            default : throw new IllegalArgumentException("Unknown Level: " + currentLevel);
        }
    }

    /**
     * 5-18 사용자 레벨 업그레이드 메소드
     */
    public void upgradeLevels_lowCohesion() {
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

    /**
     * 5-22 사용자 신규 등록 로직을 담은 add() 메소드
     */
    public void add(User user) {
        if (user.getLevel() == null) user.setLevel(Level.BASIC);
        userDao.add(user);
    }
}
