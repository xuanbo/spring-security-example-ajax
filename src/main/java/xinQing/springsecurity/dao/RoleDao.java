package xinQing.springsecurity.dao;

import org.springframework.stereotype.Repository;
import xinQing.springsecurity.entity.Role;

/**
 * RoleDao
 *
 * Created by xuan on 16-11-23.
 */
@Repository
public interface RoleDao {

    /**
     * 根据id查询角色
     *
     * @param id 角色id
     * @return 查询到的角色
     */
    Role getById(Integer id);
}
