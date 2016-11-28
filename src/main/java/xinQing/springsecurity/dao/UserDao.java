package xinQing.springsecurity.dao;

import org.springframework.stereotype.Repository;
import xinQing.springsecurity.entity.User;

import java.util.List;

/**
 * UserDao
 *
 * Created by xuan on 16-11-23.
 */
@Repository
public interface UserDao {

    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     * @return 查询到的用户
     */
    User getByUsername(String username);

    /**
     * 更新用户信息
     * 如果属性不为空则更新
     *
     *
     * @param user　将要更新的用户信息,id必须赋值
     * @return 影响数据库行数
     */
    int updateNotNullById(User user);

    /**
     *
     * 更新用户的失败尝试次数
     * 失败数加一
     *
     * @param username　用户名
     * @return 影响数据库行数
     */
    int updateFailAttemptTimes(String username);

    /**
     * 重置用户的失败尝试次数
     *
     * @param username 用户名
     * @return 影响数据库行数
     */
    int resetFailAttemptTimes(String username);

    /**
     * 查询所有记录
     *
     * @return  所有User
     */
    List<User> selectAll();
}
