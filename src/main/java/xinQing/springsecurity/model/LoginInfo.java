package xinQing.springsecurity.model;

/**
 * 登录认证返回的信息
 *
 * Created by xinQing on 2016/11/28.
 */
public class LoginInfo {

    private Boolean flag;

    private String msg;

    public LoginInfo() {
    }

    public LoginInfo(Boolean flag, String msg) {
        this.flag = flag;
        this.msg = msg;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "flag=" + flag +
                ", msg='" + msg + '\'' +
                '}';
    }
}
