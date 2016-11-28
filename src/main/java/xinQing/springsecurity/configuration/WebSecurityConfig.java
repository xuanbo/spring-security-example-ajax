package xinQing.springsecurity.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import xinQing.springsecurity.security.LimitLoginAuthenticationProvider;
import xinQing.springsecurity.security.LoginAuthenticationFilter;
import xinQing.springsecurity.security.MyAuthenticationFailureHandler;
import xinQing.springsecurity.security.MyAuthenticationSuccessHandler;
import xinQing.springsecurity.security.MyUserDetailsServiceImpl;

import javax.sql.DataSource;


/**
 * Spring Security配置
 *
 * Created by xuan on 16-11-23.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String REMEMBER_ME_KEY = "remember-me";
    private static final String LOGIN_PAGE = "/login";
    private static final String LOGOUT_URL = "/logout";

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/welcome").permitAll()// 首页不需要认证
                .antMatchers("/static/**").permitAll()// 静态资源不需要认证
                .antMatchers("/favicon.ico").permitAll()// 图标不需要认证
                .antMatchers("/captcha").permitAll()// 验证码不需要认证
                .antMatchers("/admin").hasRole("ADMIN")//   需要ROLE_ADMIN角色
                .anyRequest().authenticated()
                .and()
                .formLogin()//  自定义登录页面
                .loginPage(LOGIN_PAGE)
                .permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(authenticationSuccessHandler())// 登录成功处理
                .failureHandler(authenticationFailureHandler())// 登录失败处理
                .and()
                .logout()// 注销，如果不禁用csrf，那么需要post请求才能注销；可以自己在Controller中处理/logout，注销session
                .logoutUrl(LOGOUT_URL)// 退出成功后默认重定向到/login?logout
                .permitAll()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .and()
                .rememberMe()
                .and()
                // 自定义UsernamePasswordAuthenticationFilter，过滤验证码
                .addFilterAt(loginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                // 如果自定义了UsernamePasswordAuthenticationFilter，则要添加到UsernamePasswordAuthenticationFilter后面
                .addFilterAfter(rememberMeAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                .maximumSessions(1)// 一个用户只能登录一次，多次登录会让上次的session无效
                .sessionRegistry(sessionRegistry());// 注册
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 自定义AuthenticationProvider
        auth
                .authenticationProvider(authenticationProvider())
                .authenticationProvider(rememberMeAuthenticationProvider());
    }

    /**
     * 全局安全方法必须配置authenticationManagerBean
     *
     * @return AuthenticationManagerDelegator
     * @throws Exception 异常信息
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 自定义UserDetailsService
     * 主要是根据username获取UserDetails信息
     * 跟Shiro的Realm类似
     *
     * @return MyUserDetailsServiceImpl
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsServiceImpl();
    }

    /**
     * 自定义身份认证Provider
     * 默认是DaoAuthenticationProvider
     * 实现AuthenticationProvider，自定义自己的身份认证Provider
     *
     * @return LimitLoginAuthenticationProvider 对登录失败尝试限制
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        LimitLoginAuthenticationProvider authenticationProvider = new LimitLoginAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /**
     * 自定义认证成功处理
     * 实现了AuthenticationSuccessHandler，返回成功的json数据
     *
     * @return MyAuthenticationSuccessHandler 返回成功的json数据
     */
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new MyAuthenticationSuccessHandler();
    }

    /**
     * 自定义认证失败处理
     * 实现了AuthenticationFailureHandler，根据身份认证Provider抛出的身份认证异常做不同的处理，返回失败的json数据
     *
     * @return MyAuthenticationFailureHandler   返回身份认证失败信息，返回失败的json数据
     */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new MyAuthenticationFailureHandler();
    }

    /**
     * RememberMeService
     * 使用TokenBasedRememberMeServices替换PersistentTokenBasedRememberMeServices
     *
     * @return TokenBasedRememberMeServices
     */
    @Bean
    public RememberMeServices rememberMeServices() {
        TokenBasedRememberMeServices tokenBasedRememberMeServices =
                new TokenBasedRememberMeServices(REMEMBER_ME_KEY, userDetailsService());
        tokenBasedRememberMeServices.setTokenValiditySeconds(60 * 60 * 24 * 7);
        return tokenBasedRememberMeServices;
    }

    /**
     * RememberMeAuthenticationFilter
     * 由于自定义了UsernamePasswordAuthenticationFilter，
     * 因此，需要将rememberMeAuthenticationFilter添加在UsernamePasswordAuthenticationFilter后面
     *
     * @return RememberMeAuthenticationFilter
     * @throws Exception 异常信息
     */
    @Bean
    public RememberMeAuthenticationFilter rememberMeAuthenticationFilter() throws Exception {
        return new RememberMeAuthenticationFilter(authenticationManager(), rememberMeServices());
    }

    /**
     * RememberMeAuthenticationProvider
     * 要添加在provider列表中
     *
     * @return RememberMeAuthenticationProvider
     */
    @Bean
    public RememberMeAuthenticationProvider rememberMeAuthenticationProvider() {
        return new RememberMeAuthenticationProvider(REMEMBER_ME_KEY);
    }

    /**
     * 加密
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(9);
    }

    /**
     * 登录过滤，认证验证码是否正确
     * 然后调用super.attemptAuthentication(request, response)
     * 必须设置AuthenticationManager
     *
     * @return LoginAuthenticationFilter
     */
    @Bean
    public LoginAuthenticationFilter loginAuthenticationFilter() throws Exception {
        LoginAuthenticationFilter loginAuthenticationFilter = new LoginAuthenticationFilter();
        loginAuthenticationFilter.setAuthenticationManager(authenticationManager());
        // 设置自定义AuthenticationFailureHandler，默认会401状态码
        loginAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
        loginAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        // 由于自定义filter，为了使RememberMe有效，必须在这里设置rememberMeServices
        loginAuthenticationFilter.setRememberMeServices(rememberMeServices());
        return loginAuthenticationFilter;
    }


    /**
     * 自定义SessionRegistry
     * 可以跟踪活跃的session,统计在线人数,显示在线用户.
     *
     * @return SessionRegistryImpl
     */
    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }

}
