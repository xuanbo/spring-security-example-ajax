package xinQing.springsecurity.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import xinQing.springsecurity.service.UserService;

/**
 * Created by xuan on 16-11-24.
 */
@Controller
public class UserController {

    private static final Logger log = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * user页面
     *
     * @return  /WEB-INF/views/user.jsp
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String user() {
        userService.selectAll().forEach(log::debug);
        return "user";
    }
}
