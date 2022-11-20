package aero.cubox.system.controller;

import aero.cubox.cmmn.service.CommonService;
import aero.cubox.core.vo.EmpVO;
import aero.cubox.core.vo.PaginationVO;
import aero.cubox.core.vo.PrivacyVO;
import aero.cubox.system.service.SystemService;
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

}
