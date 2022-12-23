package aero.cubox.cmmn.controller;

import aero.cubox.cmmn.service.MainService;
import aero.cubox.core.vo.EntHistVO;
import aero.cubox.core.vo.LoginVO;
import aero.cubox.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class MainController {

	@Resource(name="mainService")
	private MainService mainService;

	private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);


	@SuppressWarnings("serial")
	@RequestMapping(value="/main.do")
	public String main(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes, HttpSession session) throws Exception {
		//자동새로고침때문에 추가
		String reloadYn = StringUtil.isNullToString(commandMap.get("reloadYn")).matches("Y") ? StringUtil.isNullToString(commandMap.get("reloadYn")) : "N";
		String intervalSecond = StringUtil.isNullToString(commandMap.get("intervalSecond")).matches("(^[0-9]+$)") ? StringUtil.isNullToString(commandMap.get("intervalSecond")) : "5";

		model.addAttribute("reloadYn", reloadYn);
		model.addAttribute("intervalSecond", intervalSecond);

		String ssAuthorId = ((LoginVO)session.getAttribute("loginVO")).getRole_id();

		return "cubox/common/main";
	}

	/**
	 * 대쉬보드 차트1
	 * @param
	 * @return ModelAndView
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/main/getMainStatus01.do")
	public ModelAndView getMainStatus01(@RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsonView");

		List<HashMap> mainStatus01 = mainService.getMainStatus01();

		modelAndView.addObject("mainStatus01", mainStatus01);
		return modelAndView;
	}


	/**
	 * 대쉬보드 차트2
	 * @param
	 * @return ModelAndView
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/main/getMainStatus02.do")
	public ModelAndView getMainStatus02(@RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsonView");

		List<HashMap> mainStatus02 = mainService.getMainStatus02();

		modelAndView.addObject("mainStatus02", mainStatus02);
		return modelAndView;
	}

	@RequestMapping(value="/main/entHist")
	public ModelAndView mainEntHistList() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsonView");

		List<EntHistVO> entHistList = mainService.getMainEntHistList();

		modelAndView.addObject("entHistList", entHistList);

		return modelAndView;
	}

	@RequestMapping(value="/main/alarmHist")
	public ModelAndView mainAlarmHistList() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsonView");

		List<HashMap> alarmHistList = mainService.getMainAlarmHistList();

		modelAndView.addObject("alarmHistList", alarmHistList);

		return modelAndView;
	}
}
