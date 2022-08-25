package aero.cubox.user.controller;

import aero.cubox.cmmn.service.CommonService;
//import aero.cubox.core.vo.LoginVO;
import aero.cubox.core.vo.UserVO;
import aero.cubox.menu.service.MenuService;
import aero.cubox.user.service.UserService;
import aero.cubox.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Controller
public class UserController {

    /** commonService */
    @Resource(name = "commonService")
    private CommonService commonService;

    @Resource(name = "commonUtils")
    private CommonUtils commonUtils;

    @Resource(name = "MenuService")
    private MenuService menuService;

    @Resource(name = "userService")
    private UserService userService;


    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    /**
     * 사용자등록 임시
     */
    @RequestMapping(value="/user/addUser.do")
    public String actionLogin(ModelMap model, @RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {

        String login_id = (String) commandMap.get("login_id");
        String login_pwd = (String) commandMap.get("login_pwd");

        UserVO userVO = new UserVO();
        userVO.setLogin_id(login_id);
        userVO.setLogin_pwd(login_pwd);

        userService.addUser(userVO);

        return "cubox/common/login";

    }


}
