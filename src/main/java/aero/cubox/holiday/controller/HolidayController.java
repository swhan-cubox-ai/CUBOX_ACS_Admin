package aero.cubox.holiday.controller;


import aero.cubox.cmmn.service.CommonService;
import aero.cubox.core.vo.*;
import aero.cubox.holiday.service.HolidayService;
import aero.cubox.util.CommonUtils;
import aero.cubox.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 공휴일 관리
 * @author bhs
 *
 *
 */
@Controller
public class HolidayController {

	@Resource(name = "holidayService")
	private HolidayService holidayService;

	@Resource(name = "commonService")
	private CommonService commonService;

	@Resource(name = "commonUtils")
	private CommonUtils commonUtils;



	@RequestMapping(value="/holiday/list.do")
	public String list(ModelMap model, @RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsonView");

		try{
			HolidayVO vo = new HolidayVO();

			String srchPage       = StringUtil.nvl(commandMap.get("srchPage"), "1");
			String srchRecPerPage = StringUtil.nvl(commandMap.get("srchRecPerPage"), "10");
			String srchCond = StringUtil.nvl(commandMap.get("srchCond"), "");
			String keyword = StringUtil.nvl(commandMap.get("keyword"), "");
			String startDt = StringUtil.nvl(commandMap.get("startDt"), "");
			String endDt = StringUtil.nvl(commandMap.get("endDt"), "");

			if( StringUtil.isEmpty(startDt)){
				Date now = new Date();
				Calendar cal = Calendar.getInstance();
				cal.setTime(now);
				cal.add(Calendar.YEAR, 1);

				startDt = commonUtils.getToday("yyyy-MM-dd");
				endDt = commonUtils.getStringDate(cal.getTime(), "yyyy-MM-dd");
			}

			vo.setSrchCond(srchCond);
			vo.setKeyword(keyword);

			vo.setStartDt(startDt);
			vo.setEndDt(endDt);

			vo.setSrchPage(Integer.parseInt(srchPage));
			vo.setSrchCnt(Integer.parseInt(srchRecPerPage));
			vo.autoOffset();

			List<CommonVO> holidayTypList = commonService.getCommonCodeList("HolidayTyp");
			int totalCnt = holidayService.getHolidayListCount(vo);
			List<HolidayVO> holidayList = holidayService.getHolidayList(vo);

			PaginationVO pageVO = new PaginationVO();
			pageVO.setCurPage(vo.getSrchPage());
			pageVO.setRecPerPage(vo.getSrchCnt());
			pageVO.setTotRecord(totalCnt);
			pageVO.setUnitPage(vo.getCurPageUnit());
			pageVO.calcPageList();

			model.addAttribute("holidayTypList", holidayTypList);
			model.addAttribute("holidayList", holidayList);
			model.addAttribute("data", vo);
			model.addAttribute("pagination", pageVO);

		} catch(Exception e) {
			e.printStackTrace();
			modelAndView.addObject("message", e.getMessage());
		}

		return "cubox/holiday/list";
	}

	@RequestMapping(value="/holiday/add.do", method= RequestMethod.GET)
	public String add(ModelMap model, HttpServletRequest request) throws Exception {
		List<CommonVO> holidayTypList = commonService.getCommonCodeList("HolidayTyp");

		model.addAttribute("holidayTypList", holidayTypList);

		return "cubox/holiday/add";
	}

	@RequestMapping(value="/holiday/detail/{id}", method= RequestMethod.GET)
	public String detail(ModelMap model, @PathVariable int id, HttpServletRequest request) throws Exception {
		HashMap data = holidayService.getHolidayDetail(id);

		model.addAttribute("isModify", false);
		model.addAttribute("data", data);

		return "cubox/holiday/detail";
	}

	@RequestMapping(value="/holiday/modify/{id}", method= RequestMethod.GET)
	public String modify(ModelMap model, @PathVariable int id, HttpServletRequest request) throws Exception {
		List<CommonVO> holidayTypList = commonService.getCommonCodeList("HolidayTyp");
		HashMap data = holidayService.getHolidayDetail(id);

		model.addAttribute("isModify", true);
		model.addAttribute("holidayTypList", holidayTypList);
		model.addAttribute("data", data);

		return "cubox/holiday/detail";
	}

	@ResponseBody
	@RequestMapping(value="/holiday/modifyHoliday.do")
	public ModelAndView modifyHoliday(HttpServletRequest request, @RequestParam HashMap<String, Object> param) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsonView");

		try{
			holidayService.modifyHoliday(param);

			modelAndView.addObject("result", "success");
		}catch (Exception e){
			e.printStackTrace();
			modelAndView.addObject("message", e.getMessage());
			modelAndView.addObject("result", "fail");
		}

		return modelAndView;
	}

	@ResponseBody
	@RequestMapping(value="/holiday/addHoliday.do")
	public ModelAndView addHoliday(HttpServletRequest request, @RequestParam HashMap<String, Object> param) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsonView");

		try{
			int result = holidayService.addHoliday(param);

			modelAndView.addObject("result", "success");
		}catch (Exception e){
			e.printStackTrace();
			modelAndView.addObject("message", e.getMessage());
		}

		return modelAndView;
	}

	@ResponseBody
	@RequestMapping(value="/holiday/deleteHoliday.do")
	public ModelAndView deleteHoliday(HttpServletRequest request, @RequestParam HashMap<String, Object> param) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsonView");

		try{
			holidayService.deleteHoliday(param);
			modelAndView.addObject("result", "success");
		}catch (Exception e){
			e.printStackTrace();
			modelAndView.addObject("message", e.getMessage());
		}

		return modelAndView;
	}

	@ResponseBody
	@RequestMapping(value = "/holiday/getHolidayNmList.do")
	public ModelAndView getHolidayNmList(HttpServletRequest request, @RequestParam HashMap<String, Object> param) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsonView");

		try {
			List<Map> list = holidayService.getHolidayNmList(param);

			modelAndView.addObject("list", list);
			modelAndView.addObject("result", "success");
		} catch(Exception e) {
			e.printStackTrace();
			modelAndView.addObject("message", e.getMessage());
			modelAndView.addObject("result", "fail");
		}

		return modelAndView;
	}

}
