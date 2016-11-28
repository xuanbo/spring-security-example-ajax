package xinQing.springsecurity.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import xinQing.springsecurity.configuration.WebMvcConfig;
import xinQing.springsecurity.model.LoginInfo;
import xinQing.springsecurity.util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定义登录认证失败处理
 * 认证失败后修改为JSON异常信息
 *
 * Created by xuan on 16-11-28.
 */
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    /**
     * 自定义身份认证失败处理
     *
     * @param httpServletRequest    HttpServletRequest
     * @param httpServletResponse   HttpServletResponse
     * @param e AuthenticationException，身份认证异常
     * @throws IOException  I/O exception
     * @throws ServletException Servlet Exception
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        // 获取登录认证失败异常信息
        String error = e.getMessage();
        // 登录错误信息
        LoginInfo loginErrorInfo = new LoginInfo(false, error);
        httpServletResponse.setCharacterEncoding(WebMvcConfig.CHARACTER_ENCODING);
        // 设置返回json
        httpServletResponse.setContentType("application/json");
        PrintWriter writer = httpServletResponse.getWriter();
        writer.append(JSONUtil.toJSONString(loginErrorInfo));
        writer.flush();
    }
}
