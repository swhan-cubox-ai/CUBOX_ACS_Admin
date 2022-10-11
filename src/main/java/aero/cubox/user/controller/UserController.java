package aero.cubox.user.controller;

import aero.cubox.cmmn.service.CommonService;
//import aero.cubox.core.vo.LoginVO;
import aero.cubox.core.vo.*;
import aero.cubox.menu.service.MenuService;
import aero.cubox.menuAuth.service.MenuAuthService;
import aero.cubox.user.service.UserService;
import aero.cubox.util.CommonUtils;
import aero.cubox.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
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

	@Resource(name = "menuAuthService")
	private MenuAuthService menuAuthService;

	@Resource(name = "userService")
	private UserService userService;


	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	/**
	 * 사용자등록 임시
	 */
   /* @RequestMapping(value="/user/addUser.do")
    public String actionLogin(ModelMap model, @RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {

        String login_id = (String) commandMap.get("login_id");
        String login_pwd = (String) commandMap.get("login_pwd");

        UserVO userVO = new UserVO();
        userVO.setLogin_id(login_id);
        userVO.setLogin_pwd(login_pwd);

        userService.addUser(userVO);

        return "cubox/common/login";

    }*/

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
	@RequestMapping(value="/user/list.do")
	public String list(ModelMap model, @RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsonView");

		try {
			UserVO vo = new UserVO();

			String srchPage       = StringUtil.nvl(commandMap.get("srchPage"), "1");
			String srchRecPerPage = StringUtil.nvl(commandMap.get("srchRecPerPage"), "10");

			String srchCond = StringUtil.nvl(commandMap.get("srchCond"), "");
			String keyword = StringUtil.nvl(commandMap.get("keyword"), "");

			vo.setSrchCond(srchCond);
			vo.setKeyword(keyword);

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

		return "cubox/user/list";
	}


	@RequestMapping(value="/user/detail/{id}", method= RequestMethod.GET)
	public String detail(ModelMap model, @PathVariable int id, HttpServletRequest request) throws Exception {

		System.out.println("################## id : " + id);

		UserVO data = userService.getUserDetail(id);

		model.addAttribute("isModify", false);
		model.addAttribute("data", data);

		return "cubox/user/detail";
	}

	@RequestMapping(value="/user/modify/{id}", method= RequestMethod.GET)
	public String modify(ModelMap model, @PathVariable int id, HttpServletRequest request) throws Exception {

		System.out.println("################## id : " + id);

		UserVO data = userService.getUserDetail(id);

		model.addAttribute("isModify", true);
		model.addAttribute("data", data);

		return "cubox/user/detail";
	}

	@RequestMapping(value="/user/add.do", method= RequestMethod.GET)
	public String add(ModelMap model, HttpServletRequest request) throws Exception {
		return "cubox/user/add";
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

	@ResponseBody
	@RequestMapping(value = "/user/getUserAuthList.do")
	public ModelAndView getUserAuthList(HttpServletRequest request, @RequestParam HashMap<String, Object> param) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsonView");

		try {
			List<MenuAuthVO> list = menuAuthService.getUserAuthList(param);

			modelAndView.addObject("list", list);

		} catch(Exception e) {
			e.printStackTrace();
			modelAndView.addObject("message", e.getMessage());
		}

		return modelAndView;
	}

	@ResponseBody
	@RequestMapping(value = "/user/getRoleList.do")
	public ModelAndView getRoleList(HttpServletRequest request, @RequestParam HashMap<String, Object> param) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsonView");

		try {
			List<MenuAuthVO> list = menuAuthService.getRoleList();

			modelAndView.addObject("list", list);

		} catch(Exception e) {
			e.printStackTrace();
			modelAndView.addObject("message", e.getMessage());
		}

		return modelAndView;
	}

	@ResponseBody
	@RequestMapping(value = "/user/getUserRoleList.do")
	public ModelAndView getUserRoleList(HttpServletRequest request, @RequestParam HashMap<String, Object> param) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsonView");

		try {
			List<MenuAuthVO> list1 = menuAuthService.getRoleList();
			List<MenuAuthVO> list2 = menuAuthService.getRoleListByUser(param);

			modelAndView.addObject("list1", list1);
			modelAndView.addObject("list2", list2);
		} catch(Exception e) {
			e.printStackTrace();
			modelAndView.addObject("message", e.getMessage());
		}

		return modelAndView;
	}

	@ResponseBody
	@RequestMapping(value="/user/modifyUser.do")
	public ModelAndView modifyUser(HttpServletRequest request, @RequestParam HashMap<String, Object> param) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsonView");

		try{
			String userId = (String) param.get("userId");
			String userNm = (String) param.get("userNm");
			String deptNm = (String) param.get("deptNm");
			String contactNo = (String) param.get("contactNo");
			String activeYn = (String) param.get("activeYn");
			String roleStr = (String) param.get("roleStr");
			String isModRole = (String) param.get("isModRole");

			//권한목록 저장여부 체크 후 저장처리..
			if("Y".equals(isModRole)){
				String[] roleArray =roleStr.split(",");
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("userId", userId);

				menuAuthService.delUserRole(map);
				if(!"nan".equals(roleStr)){
					for(int i=0;i<roleArray.length;i++){
						map.put("roleId", roleArray[i]);
						menuAuthService.addUserRole(map);
					}
				}
			}

			UserVO userVO = new UserVO();
			userVO.setId(Integer.parseInt(userId));
			userVO.setUserNm(userNm);
			userVO.setDeptNm(deptNm);
			userVO.setContactNo(contactNo);
			userVO.setActiveYn(activeYn);

			userService.modifyUser(userVO);

		}catch (Exception e){
			e.printStackTrace();
			modelAndView.addObject("message", e.getMessage());
		}

		return modelAndView;
	}


	@ResponseBody
	@RequestMapping(value="/user/addUser.do")
	public ModelAndView addUser(HttpServletRequest request, @RequestParam HashMap<String, Object> param) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsonView");

		try{
			String loginId = (String) param.get("loginId");
			String userNm = (String) param.get("userNm");
			String deptNm = (String) param.get("deptNm");
			String contactNo = (String) param.get("contactNo");
			String activeYn = (String) param.get("activeYn");
			String roleStr = (String) param.get("roleStr");
			String isAddRole = (String) param.get("isAddRole");

			String pwd = "1234";

			UserVO userVO = new UserVO();
			userVO.setLoginId(loginId);
			userVO.setLoginPwd(pwd);
			userVO.setUserNm(userNm);
			userVO.setDeptNm(deptNm);
			userVO.setContactNo(contactNo);
			userVO.setActiveYn(activeYn);

			userService.addUser(userVO);

			int userId = userService.getUserId(userVO);

			//권한목록 저장여부 체크 후 저장처리..
			if("Y".equals(isAddRole)){
				String[] roleArray =roleStr.split(",");
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("userId", userId);

				for(int i=0;i<roleArray.length;i++){
					map.put("roleId", roleArray[i]);
					menuAuthService.addUserRole(map);
				}
			}

		}catch (Exception e){
			e.printStackTrace();
			modelAndView.addObject("message", e.getMessage());
		}

		return modelAndView;
	}

	@ResponseBody
	@RequestMapping(value="/user/checkLoginId.do")
	public ModelAndView checkLoginId(HttpServletRequest request, @RequestParam HashMap<String, Object> param) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsonView");

		try{
			int dupCnt = userService.checkLoginId(param);
			String isDup = (dupCnt > 0) ? "Y" : "N";

			modelAndView.addObject("isDup", isDup);
		}catch (Exception e){
			e.printStackTrace();
			modelAndView.addObject("message", e.getMessage());
		}

		return modelAndView;
	}

}
