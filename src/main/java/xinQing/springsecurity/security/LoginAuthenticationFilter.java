package xinQing.springsecurity.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import xinQing.springsecurity.exception.CaptchaException;
import xinQing.springsecurity.util.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录过滤
 * 实现验证码
 *
 * Created by xuan on 16-11-25.
 */
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 获取用户输入的验证码
        String verification_code = request.getParameter(SessionUtil.CAPTCHA);
        // Session中的验证码
        String session_verification_code = (String) request.getSession().getAttribute(SessionUtil.CAPTCHA);
        // 验证码不正确，抛出CaptchaException
        if(verification_code == null || !verification_code.equalsIgnoreCase(session_verification_code)) {
            throw new CaptchaException("验证码不正确");
        }
        // 调用父类的认证
        return super.attemptAuthentication(request, response);
    }
}
