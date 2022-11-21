package aero.cubox.holiday.controller;


import aero.cubox.cmmn.service.CommonService;
import aero.cubox.core.vo.*;
import aero.cubox.holiday.service.HolidayService;
import aero.cubox.util.CommonUtils;
import aero.cubox.util.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
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

		try {
			HolidayVO vo = new HolidayVO();

			String srchPage       = StringUtil.nvl(commandMap.get("srchPage"), "1");
			String srchRecPerPage = StringUtil.nvl(commandMap.get("srchRecPerPage"), "10");
			String srchCond = StringUtil.nvl(commandMap.get("srchCond"), "");
			String keyword = StringUtil.nvl(commandMap.get("keyword"), "");
			String startDt = StringUtil.nvl(commandMap.get("startDt"), "");
			String endDt = StringUtil.nvl(commandMap.get("endDt"), "");

			if(startDt.equals("")){
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

	@RequestMapping(value="/holiday/excelDownload.do", method = RequestMethod.POST)
	public void excelDownload(ModelMap model, @RequestParam Map<String, Object> commandMap, HttpServletResponse response) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		HolidayVO vo = objectMapper.convertValue(commandMap, HolidayVO.class);

		vo.setIsExcel("Y");

		List<HolidayVO> holidayList = holidayService.getHolidayList(vo);

		///// Create Excel /////
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet();
		Row row = null;
		Cell cell = null;
		int rowNum = 0;

		//// Header ////
		final String[] colNames = {"No", "유형", "공휴일 명", "일자", "사용", "등록일자"};
		// Header size
		final int[] colWidths = {1500, 3000, 5000, 4000, 3000, 4000};
		// Header font
		Font fontHeader = wb.createFont();
		fontHeader.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// Header style
		CellStyle styleHeader = wb.createCellStyle();
		styleHeader.setAlignment(CellStyle.ALIGN_CENTER);
		styleHeader.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		styleHeader.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		styleHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		styleHeader.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleHeader.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleHeader.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleHeader.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleHeader.setFont(fontHeader);

		row = sheet.createRow(rowNum++);
		row.setHeight((short) 500);  // 행 높이

		for (int i = 0; i < colNames.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(colNames[i]);
			cell.setCellStyle(styleHeader);
			sheet.setColumnWidth(i, colWidths[i]);
		}

		//// Body ////
		for (int i = 0; i < holidayList.size(); i++) {
			row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(i + 1);
			row.createCell(1).setCellValue(holidayList.get(i).getHolidayTypNm());
			row.createCell(2).setCellValue(holidayList.get(i).getHolidayNm());
			row.createCell(3).setCellValue(holidayList.get(i).getHoliday());
			row.createCell(4).setCellValue(holidayList.get(i).getUseYn());
			row.createCell(5).setCellValue(holidayList.get(i).getCreatedAt());
		}

		// Date
		SimpleDateFormat fmt = new SimpleDateFormat("yyMMdd-HH_mm_ss");
		Date date = new Date();

		// File name
		String fileNm = "공휴일관리목록_" + fmt.format(date) + ".xlsx";
		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attatchment;filename=" + URLEncoder.encode(fileNm, "UTF-8"));
		wb.write(response.getOutputStream());
	}
}
