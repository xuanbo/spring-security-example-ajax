package xinQing.springsecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import xinQing.springsecurity.dao.RoleDao;
import xinQing.springsecurity.dao.UserDao;
import xinQing.springsecurity.entity.Role;
import xinQing.springsecurity.entity.User;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Spring Security获取用户信息，从而进行身份认证
 *
 * Created by xuan on 16-11-23.
 */
public class MyUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("账号不存在");
        }
        Role role = roleDao.getById(user.getRoleId());
        if (role == null) {
            throw new UsernameNotFoundException("账号还没有授予权限");
        }
        return new MyUserDetails(user, getAuthorities(role));
    }

    /**
     * 获取权限
     * 我这里用户跟角色是一对一
     *
     * @param role 角色
     * @return Collection<GrantedAuthority>
     */
    private Collection<GrantedAuthority> getAuthorities(Role role) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getName());
        grantedAuthorities.add(grantedAuthority);
        return grantedAuthorities;
    }

}
