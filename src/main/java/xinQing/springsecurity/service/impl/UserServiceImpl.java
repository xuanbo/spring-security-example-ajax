package xinQing.springsecurity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xinQing.springsecurity.dao.UserDao;
import xinQing.springsecurity.entity.User;
import xinQing.springsecurity.service.UserService;

import java.util.List;

/**
 * Created by xuan on 16-11-24.
 */
@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Transactional(readOnly = true)
    @Override
    public List<User> selectAll() {
        return userDao.selectAll();
    }
}
