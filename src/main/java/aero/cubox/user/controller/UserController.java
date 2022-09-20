package aero.cubox.user.controller;

import aero.cubox.cmmn.service.CommonService;
//import aero.cubox.core.vo.LoginVO;
import aero.cubox.core.vo.LoginVO;
import aero.cubox.core.vo.PaginationVO;
import aero.cubox.core.vo.UserVO;
import aero.cubox.menu.service.MenuService;
import aero.cubox.user.service.UserService;
import aero.cubox.util.CommonUtils;
import aero.cubox.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
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

    /**
     //	 * 비밀번호변경
     //	 * @param commandMap 파라메터전달용 commandMap
     //	 * @return modelAndView
     //	 * @throws Exception
     //	 */
	@ResponseBody
	@RequestMapping(value = "/user/passwdChangeSave.do")
	public ModelAndView passwdChangeSave(@RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {

		ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("jsonView");

		String currentpasswd = (String) commandMap.get("currentpasswd");
		String fpasswd = (String) commandMap.get("fpasswd");

		LoginVO loginVO = (LoginVO)request.getSession().getAttribute("loginVO");

		if (loginVO != null && loginVO.getLogin_id() != null && !loginVO.getLogin_id().equals("")) {

			UserVO userVO = new UserVO();
			userVO.setLogin_id(loginVO.getLogin_id());
			userVO.setLogin_pwd(currentpasswd);

			int checkPwd = userService.checkPwd(userVO);

			if(checkPwd > 0){
				userVO.setLogin_pwd(fpasswd);
				int passwdCnt = userService.passwdChangeSave(userVO);

				modelAndView.addObject("checkPwdError", "N");

			}else{
				modelAndView.addObject("checkPwdError", "Y");
			}
		}

		return modelAndView;

	}

	/**
	 *	시스템관리 - 사용자관리
	 */
	@RequestMapping(value="/user/userManagement.do")
	public String userManagement(ModelMap model, @RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsonView");

		try {
			UserVO vo = new UserVO();

			String srchPage       = StringUtil.nvl(commandMap.get("srchPage"), "1");
			String srchRecPerPage = StringUtil.nvl(commandMap.get("srchRecPerPage"), "10");

			vo.setSrchPage(Integer.parseInt(srchPage));
			vo.setSrchCnt(Integer.parseInt(srchRecPerPage));
			vo.autoOffset();

			List<UserVO> list = userService.getUserList(vo);
			int totalCnt = userService.getUserListCount(vo);

			PaginationVO pageVO = new PaginationVO();
			pageVO.setCurPage(vo.getSrchPage());
			pageVO.setRecPerPage(vo.getSrchCnt());
			pageVO.setTotRecord(totalCnt);
			pageVO.setUnitPage(vo.getCurPageUnit());
			pageVO.calcPageList();

			//model.addAttribute("option", option);
			model.addAttribute("userList", list);
			model.addAttribute("cntPerPage", "10");
			//model.addAttribute("cntPerPage", cntPerPage);
			model.addAttribute("pagination", pageVO);

		} catch(Exception e) {
			e.printStackTrace();
			modelAndView.addObject("message", e.getMessage());
		}

		return "cubox/systemManagement/user_management";
	}

	@ResponseBody
	@RequestMapping(value = "/user/getUserList.do")
	public ModelAndView getUserList(@RequestParam Map<String, Object> param, ModelMap model) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsonView");

		try {
			UserVO vo = new UserVO();

			String srchPage       = StringUtil.nvl(param.get("srchPage"), "1");
			String srchRecPerPage = StringUtil.nvl(param.get("srchRecPerPage"), "10");

			vo.setSrchPage(Integer.parseInt(srchPage));
			vo.setSrchCnt(Integer.parseInt(srchRecPerPage));
			vo.autoOffset();

			List<UserVO> list = userService.getUserList(vo);
			int totalCnt = userService.getUserListCount(vo);

			PaginationVO pageVO = new PaginationVO();
			pageVO.setCurPage(vo.getSrchPage());
			pageVO.setRecPerPage(vo.getSrchCnt());
			pageVO.setTotRecord(totalCnt);
			pageVO.setUnitPage(vo.getCurPageUnit());
			pageVO.calcPageList();

			//model.addAttribute("option", option);
			modelAndView.addObject("userList", list);
			model.addAttribute("userList", list);
			model.addAttribute("cntPerPage", "10");
			//model.addAttribute("cntPerPage", cntPerPage);
			model.addAttribute("pagination", pageVO);

		} catch(Exception e) {
			e.printStackTrace();
			modelAndView.addObject("message", e.getMessage());
		}

		return modelAndView;
	}



}
