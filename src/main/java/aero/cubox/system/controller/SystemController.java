package aero.cubox.system.controller;

import aero.cubox.cmmn.service.CommonService;
import aero.cubox.core.vo.*;
import aero.cubox.system.service.SystemService;
import aero.cubox.terminal.service.TerminalService;
import aero.cubox.util.CommonUtils;
import aero.cubox.util.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value="/system")
public class SystemController {

    @Resource(name = "systemService")
    private SystemService systemService;

    @Resource(name = "commonService")
    private CommonService commonService;

    @Resource(name = "commonUtils")
    private CommonUtils commonUtils;

    @Resource(name = "terminalService")
    private TerminalService terminalService;

    @RequestMapping(value="/privacy/list.do")
    public String privacyList(ModelMap model, @RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try{
            PrivacyVO vo = new PrivacyVO();

            String srchPage       = StringUtil.nvl(commandMap.get("srchPage"), "1");
            String srchRecPerPage = StringUtil.nvl(commandMap.get("srchRecPerPage"), "10");
            String fromDt = StringUtil.nvl(commandMap.get("fromDt"), "");
            String toDt = StringUtil.nvl(commandMap.get("toDt"), "");
            String keyword1 = StringUtil.nvl(commandMap.get("keyword1"), "");
            String keyword2 = StringUtil.nvl(commandMap.get("keyword2"), "");

            if( fromDt.equals("")){
                Date now = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(now);

                fromDt = commonUtils.getToday("yyyy-MM-dd");
                toDt = commonUtils.getStringDate(cal.getTime(), "yyyy-MM-dd");
            }

            vo.setSrchPage(Integer.parseInt(srchPage));
            vo.setSrchCnt(Integer.parseInt(srchRecPerPage));
            vo.autoOffset();

            vo.setFromDt(fromDt);
            vo.setToDt(toDt);
            vo.setKeyword1(keyword1);
            vo.setKeyword2(keyword2);

            int totalCnt = systemService.getPrivacyListCount(vo);
            List<PrivacyVO> privacyList = systemService.getPrivacyList(vo);

            PaginationVO pageVO = new PaginationVO();
            pageVO.setCurPage(vo.getSrchPage());
            pageVO.setRecPerPage(vo.getSrchCnt());
            pageVO.setTotRecord(totalCnt);
            pageVO.setUnitPage(vo.getCurPageUnit());
            pageVO.calcPageList();

            model.addAttribute("privacyList", privacyList);
            model.addAttribute("data", vo);
            model.addAttribute("pagination", pageVO);

        } catch(Exception e) {
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return "cubox/system/privacy/list";
    }

    @ResponseBody
    @RequestMapping(value = "/privacy/getEmpDetail.do")
    public ModelAndView getEmpDetail(HttpServletRequest request, @RequestParam Map<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try {
            Map data = systemService.getEmpDetail(param);

            modelAndView.addObject("data", data);
            modelAndView.addObject("result", "success");
        } catch(Exception e) {
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/privacy/delAllPrivacy.do")
    public ModelAndView delAllPrivacy(HttpServletRequest request, @RequestParam Map<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try {
            systemService.delAllPrivacy(param);
            modelAndView.addObject("result", "success");
        } catch(Exception e) {
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/privacy/delSelectedPrivacy.do")
    public ModelAndView delSelectedPrivacy(HttpServletRequest request, @RequestParam Map<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try {
            systemService.delSelectedPrivacy(param);
            modelAndView.addObject("result", "success");
        } catch(Exception e) {
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return modelAndView;
    }

    @RequestMapping(value="/privacy/excelDownload.do", method = RequestMethod.POST)
    public void privacyExcelDownload(ModelMap model, @RequestParam Map<String, Object> commandMap, HttpServletResponse response) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        PrivacyVO vo = objectMapper.convertValue(commandMap, PrivacyVO.class);

        vo.setIsExcel("Y");

        List<PrivacyVO> privacyList = systemService.getPrivacyList(vo);

        ///// Create Excel /////
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        Row row = null;
        Cell cell = null;
        int rowNum = 0;

        //// Header ////
        final String[] colNames = {"No", "인사번호", "소속", "부서", "성명", "사용자 타입", "만료일자"};
        // Header size
        final int[] colWidths = {1500, 3000, 5000, 5000, 3000, 4000, 4000};
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
        for (int i = 0; i < privacyList.size(); i++) {
            row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(privacyList.get(i).getEmpCd());
            row.createCell(2).setCellValue(privacyList.get(i).getBelongNm());
            row.createCell(3).setCellValue(privacyList.get(i).getDeptNm());
            row.createCell(4).setCellValue(privacyList.get(i).getEmpNm());
            row.createCell(5).setCellValue(privacyList.get(i).getUserTypNm());
            row.createCell(6).setCellValue(privacyList.get(i).getExpiredDt());
        }

        // Date
        SimpleDateFormat fmt = new SimpleDateFormat("yyMMdd-HH_mm_ss");
        Date date = new Date();

        // File name
        String fileNm = "개인정보폐기_" + fmt.format(date) + ".xlsx";
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attatchment;filename=" + URLEncoder.encode(fileNm, "UTF-8"));
        wb.write(response.getOutputStream());
    }

    @RequestMapping(value="/actLog/list.do")
    public String actLogList(ModelMap model, @RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try{
//            PrivacyVO vo = new PrivacyVO();
//
//            String srchPage       = StringUtil.nvl(commandMap.get("srchPage"), "1");
//            String srchRecPerPage = StringUtil.nvl(commandMap.get("srchRecPerPage"), "10");
//            String fromDt = StringUtil.nvl(commandMap.get("fromDt"), "");
//            String toDt = StringUtil.nvl(commandMap.get("toDt"), "");
//            String keyword1 = StringUtil.nvl(commandMap.get("keyword"), "");
//
//            if( fromDt.equals("")){
//                Date now = new Date();
//                Calendar cal = Calendar.getInstance();
//                cal.setTime(now);
//
//                fromDt = commonUtils.getToday("yyyy-MM-dd");
//                toDt = commonUtils.getStringDate(cal.getTime(), "yyyy-MM-dd");
//            }
//
//            vo.setSrchPage(Integer.parseInt(srchPage));
//            vo.setSrchCnt(Integer.parseInt(srchRecPerPage));
//            vo.autoOffset();
//
//            vo.setFromDt(fromDt);
//            vo.setToDt(toDt);
//            vo.setKeyword1(keyword1);
//
//            int totalCnt = 34;
//            //List<PrivacyVO> actLogList = systemService.getPrivacyList(vo);
//            List<String> actLogList = null;
            PaginationVO pageVO = new PaginationVO();
            pageVO.setCurPage(1);
            pageVO.setRecPerPage(10);
            pageVO.setTotRecord(34);
            pageVO.setUnitPage(10);
            pageVO.calcPageList();

            //model.addAttribute("actLogList", actLogList);
//            model.addAttribute("data", vo);
            model.addAttribute("pagination", pageVO);

        } catch(Exception e) {
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return "cubox/system/actLog/list";
    }

    @RequestMapping(value="/stat/list.do")
    public String statList(ModelMap model, @RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try{
            EntHistStatVO vo = new EntHistStatVO();
            String srchPage       = StringUtil.nvl(commandMap.get("srchPage"), "1");
            String srchRecPerPage = StringUtil.nvl(commandMap.get("srchRecPerPage"), "10");
            String fromDt = StringUtil.nvl(commandMap.get("fromDt"), "");
            String toDt = StringUtil.nvl(commandMap.get("toDt"), "");
            String srchCond1 = StringUtil.nvl(commandMap.get("srchCond1"), "");
            String srchCond2 = StringUtil.nvl(commandMap.get("srchCond2"), "");
            String srchCond3 = StringUtil.nvl(commandMap.get("srchCond3"), "");
            String srchCond4 = StringUtil.nvl(commandMap.get("srchCond4"), "day");

            Date now = new Date();
            if("day".equals(srchCond4)){
                if(fromDt.equals("")){
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(now);
                    cal.add(Calendar.DATE, -6);
                    fromDt = commonUtils.getStringDate(cal.getTime(), "yyyy-MM-dd");
                }

                if(toDt.equals("")){
                    Calendar cal2 = Calendar.getInstance();
                    cal2.setTime(now);
                    cal2.add(Calendar.DATE, 1);
                    toDt = commonUtils.getStringDate(cal2.getTime(), "yyyy-MM-dd");
                }
            } else {
                if(fromDt.equals("")){
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(now);
                    cal.add(Calendar.MONTH, -11);
                    fromDt = commonUtils.getStringDate(cal.getTime(), "yyyy-MM");
                }

                if(toDt.equals("")){
                    Calendar cal2 = Calendar.getInstance();
                    cal2.setTime(now);
                    cal2.add(Calendar.MONTH, 1);
                    toDt = commonUtils.getStringDate(cal2.getTime(), "yyyy-MM");
                } else {
                    Date parseDt = StringToMonth(toDt);
                    Calendar cal2 = Calendar.getInstance();
                    cal2.setTime(parseDt);
                    cal2.add(Calendar.MONTH, 1);
                    toDt = commonUtils.getStringDate(cal2.getTime(), "yyyy-MM");
                }
            }

            vo.setSrchPage(Integer.parseInt(srchPage));
            vo.setSrchCnt(Integer.parseInt(srchRecPerPage));
            vo.autoOffset();

            vo.setFromDt(fromDt);
            vo.setToDt(toDt);
            vo.setSrchCond1(srchCond1);
            vo.setSrchCond2(srchCond2);
            vo.setSrchCond3(srchCond3);
            vo.setSrchCond4(srchCond4);

            List<EntHistStatVO> statList = systemService.getStatList(vo);
            int totalCnt = systemService.getStatListCount(vo);
            PaginationVO pageVO = new PaginationVO();
            pageVO.setCurPage(vo.getSrchPage());
            pageVO.setRecPerPage(vo.getSrchCnt());
            pageVO.setTotRecord(totalCnt);
            pageVO.setUnitPage(vo.getCurPageUnit());
            pageVO.calcPageList();

            if("day".equals(srchCond4)){

            } else {
                Date parseDt = StringToMonth(toDt);
                Calendar cal2 = Calendar.getInstance();
                cal2.setTime(parseDt);
                cal2.add(Calendar.MONTH, -1);
                toDt = commonUtils.getStringDate(cal2.getTime(), "yyyy-MM");

                vo.setFromDt(fromDt);
                vo.setToDt(toDt);
            }


            List<CommonVO> buildingCombList = terminalService.getBuildingList();
            List<CommonVO> cardTagTypList = commonService.getCommonCodeList("cardTagTyp");



            model.addAttribute("buildingCombList", buildingCombList);
            model.addAttribute("cardTagTypList", cardTagTypList);
            model.addAttribute("statList", statList);
            model.addAttribute("data", vo);
            model.addAttribute("pagination", pageVO);

        } catch(Exception e) {
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return "cubox/system/stat/list";
    }

    private Date StringToDate(String dateStr) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(dateStr);
        return date;
    }

    private Date StringToMonth(String dateStr) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        Date date = formatter.parse(dateStr);
        return date;
    }

}
