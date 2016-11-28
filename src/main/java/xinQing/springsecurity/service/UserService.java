package xinQing.springsecurity.service;

import org.springframework.security.access.prepost.PreAuthorize;
import xinQing.springsecurity.entity.User;

import java.util.List;

/**
 * Created by xuan on 16-11-24.
 */
public interface UserService {

    /**
     * 查询所有记录
     * 具有ROLE_ADMIN角色的用户才能访问
     *
     * @return  所有User
     */
    @PreAuthorize("hasRole('ADMIN')")
    List<User> selectAll();
}
