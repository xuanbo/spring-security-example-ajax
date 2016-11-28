package xinQing.springsecurity.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码错误异常
 *
 * Created by xuan on 16-11-25.
 */
public class CaptchaException extends AuthenticationException {

    public CaptchaException(String msg, Throwable t) {
        super(msg, t);
    }

    public CaptchaException(String msg) {
        super(msg);
    }
}
