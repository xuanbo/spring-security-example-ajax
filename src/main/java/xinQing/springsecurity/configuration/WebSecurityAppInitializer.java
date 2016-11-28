package xinQing.springsecurity.configuration;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * 注册SpringSecurityFilterChain Filter
 *
 * Created by xuan on 16-11-23.
 */
public class WebSecurityAppInitializer extends AbstractSecurityWebApplicationInitializer {

    /**
     * 相当于在web.xml中配置org.springframework.security.web.session.HttpSessionEventPublisher监听器
     *
     * @return true
     */
    @Override
    protected boolean enableHttpSessionEventPublisher() {
        return true;
    }
}
