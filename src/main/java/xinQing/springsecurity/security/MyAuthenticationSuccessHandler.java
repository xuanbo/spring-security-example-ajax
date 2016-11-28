package xinQing.springsecurity.security;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import xinQing.springsecurity.configuration.WebMvcConfig;
import xinQing.springsecurity.model.LoginInfo;
import xinQing.springsecurity.util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * 登录认证成功处理
 * 返回认证成功的JSON信息
 *
 * Created by xinQing on 2016/11/28.
 */
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * 自定义身份认证成功处理
     *
     * @param httpServletRequest HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param authentication Authentication
     * @throws IOException  I/O exception
     * @throws ServletException Servlet Exception
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        // 登录成功信息
        LoginInfo loginSuccessInfo = new LoginInfo(true, "认证成功！");
        httpServletResponse.setCharacterEncoding(WebMvcConfig.CHARACTER_ENCODING);

        // 设置返回json
        httpServletResponse.setContentType("application/json");
        PrintWriter writer = httpServletResponse.getWriter();
        writer.append(JSONUtil.toJSONString(loginSuccessInfo));
        writer.flush();
    }

}
