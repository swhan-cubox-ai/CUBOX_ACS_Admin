package aero.cubox.cmmn.controller;

import aero.cubox.cmmn.service.CommonService;
import aero.cubox.core.vo.LoginVO;
import aero.cubox.link.service.MdmService;
import aero.cubox.menu.service.MenuService;
import aero.cubox.util.AuthorManager;
import aero.cubox.util.CommonUtils;
import aero.cubox.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class CommonController {

	/** commonService */
	@Resource(name = "commonService")
	private CommonService commonService;

	@Resource(name = "commonUtils")
	private CommonUtils commonUtils;
	
	@Resource(name = "MenuService")
	private MenuService menuService;


	@Resource(name="MdmService")
	private MdmService mdmService;


	private static final Logger LOGGER = LoggerFactory.getLogger(CommonController.class);


	@RequestMapping(value="/login.do")
	public String login(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

//		String mdmTest = mdmService.getTmMdmCpgnList();
//		model.addAttribute("mdmTest", mdmTest);

		return "cubox/common/login";
	}

	@RequestMapping(value="/common/loginProc.do")
	public String actionLogin(ModelMap model, @RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {

		String login_id = (String) commandMap.get("login_id");
		String login_pwd = (String) commandMap.get("login_pwd");

		LoginVO loginVO = new LoginVO();
		loginVO.setLogin_id(login_id);
		loginVO.setLogin_pwd(login_pwd);

		loginVO.setDirect_yn("N");

		LoginVO resultVO = commonService.actionLogin(loginVO);
		resultVO.setFlastaccip(commonUtils.getIPFromRequest(request));
		resultVO.setFlastaccdt(commonUtils.getToday("yyyyMMddHHmmss"));

		LOGGER.debug("[LAST_ACC_IP] :" + resultVO.getFlastaccip());
		LOGGER.debug("[LAST_ACC_DE] :" + resultVO.getFlastaccdt());

		if (resultVO != null && resultVO.getLogin_id() != null && !resultVO.getLogin_id().equals("")) {
			// 로그인 로그 설계시 작업.
			// commonService.lastConnect(resultVO); //로그인시 마지막 접속일 변경




			request.getSession().setAttribute("loginVO", resultVO);

			return "redirect:/main.do";
		} else {
			model.addAttribute("resultMsg", "loginError");

			return "cubox/common/login";
		}
	}

	@RequestMapping(value="/common/loginSession.do")
	public String directSessionLogin(ModelMap model, @RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {

		String empCd = (String) commandMap.get("empCd");

		LoginVO loginVO = new LoginVO();

		Map deptMap = commonService.getDeptInfo(empCd);

		if(deptMap == null){
			model.addAttribute("resultMsg", "loginError");
			return "cubox/common/login";
		}else{
			loginVO.setDept_cd(deptMap.get("deptCd") + "");
			loginVO.setDept_nm(deptMap.get("deptNm") + "");
			loginVO.setUser_nm(deptMap.get("empNm") + "");
			loginVO.setRole_id("19");
			loginVO.setFlastaccip(commonUtils.getIPFromRequest(request));
			loginVO.setFlastaccdt(commonUtils.getToday("yyyyMMddHHmmss"));

			loginVO.setDirect_yn("Y");

			LOGGER.debug("[DIRECT_LAST_ACC_IP] :" + loginVO.getFlastaccip());
			LOGGER.debug("[DIRECT_LAST_ACC_DE] :" + loginVO.getFlastaccdt());

			request.getSession().setAttribute("loginVO", loginVO);

			return "redirect:/main.do";
		}
	}

	@SuppressWarnings("serial")
	@RequestMapping(value="/main.do")
	public String main(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes, HttpSession session) throws Exception {
		//자동새로고침때문에 추가
		String reloadYn = StringUtil.isNullToString(commandMap.get("reloadYn")).matches("Y") ? StringUtil.isNullToString(commandMap.get("reloadYn")) : "N";
		String intervalSecond = StringUtil.isNullToString(commandMap.get("intervalSecond")).matches("(^[0-9]+$)") ? StringUtil.isNullToString(commandMap.get("intervalSecond")) : "5";




//	@ResponseBody
//	@RequestMapping(value = "/main/getMainNoticeList.do")
//	public ModelAndView getMainNoticeList (@RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.setViewName("jsonView");
//
//		//출입이력
//		List<BoardVO> noticeList = commonService.getMainNoticeList();
//		modelAndView.addObject("noticeList", noticeList);
//		return modelAndView;
//	}
//
//
//	@ResponseBody
//	@RequestMapping(value = "/main/getMainQaList.do")
//	public ModelAndView getMainQaList (@RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.setViewName("jsonView");
//
//		//출입이력
//		List<BoardVO> qaList = commonService.getMainQaList();
//		modelAndView.addObject("qaList", qaList);
//		return modelAndView;
//	}
	
	@RequestMapping(value = "/logout.do")
	public String actionLogout(HttpServletRequest request, ModelMap model) throws Exception {

		HttpSession session = request.getSession();
		LoginVO loginVO = (LoginVO) session.getAttribute("loginVO");
		session.setAttribute("loginVO", null);
		session.invalidate();
		AuthorManager.getInstance().clear();

		return "redirect:/login.do";
	}

	/**
	 * 모니터링
	 * @param commandMap 파라메터전달용 commandMap
	 * @return common/index
	 * @throws Exception
	 */
//	@RequestMapping(value="/index.do")
//	public String index(ModelMap model, @RequestParam Map<String, Object> commandMap,
//			HttpServletRequest request) throws Exception {
//		LoginVO loginVO = (LoginVO)request.getSession().getAttribute("loginVO");
//
//		if (loginVO != null && loginVO.getFsiteid() != null && !loginVO.getFsiteid().equals("")) {
//			model.addAttribute("menuPath", "common/index");
//			return "cubox/cuboxSubContents";
//		}else{
//			return "redirect:/login.do";
//		}
//	}
}
