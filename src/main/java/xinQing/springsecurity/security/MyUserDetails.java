package xinQing.springsecurity.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import xinQing.springsecurity.entity.User;

import java.util.Collection;
import java.util.Date;

/**
 * 实现UserDetails
 * <p>
 * Created by xuan on 16-11-23.
 */
public class MyUserDetails implements UserDetails {

    private User user;

    private Collection<? extends GrantedAuthority> authorities;

    public MyUserDetails(User user, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * 账号过期时间大于当前时间则没有过期
     *
     * @return 账号过期返回true
     */
    @Override
    public boolean isAccountNonExpired() {
        return user.getAccountExpiredDate().getTime() > new Date().getTime();
    }

    /**
     * 失败尝试登录5次后锁定账号
     *
     * @return 小于等于5次返回true
     */
    @Override
    public boolean isAccountNonLocked() {
        return user.getAttemptTimes() <= 5;
    }

    /**
     * 密码过期时间大于当前时间则没有过期
     *
     * @return 密码过期返回true
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return user.getCredentialsExpiredDate().getTime() > new Date().getTime();
    }

    /**
     * 用户是否可用
     *
     * @return 可用返回true
     */
    @Override
    public boolean isEnabled() {
        return user.getEnable();
    }

    public boolean equals(Object rhs) {
        return rhs instanceof MyUserDetails ? getUsername().equals(((MyUserDetails) rhs).getUsername()) : false;
    }

    public int hashCode() {
        return getUsername().hashCode();
    }
}
