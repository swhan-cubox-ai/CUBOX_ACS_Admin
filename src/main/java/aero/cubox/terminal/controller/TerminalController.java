package aero.cubox.terminal.controller;


import aero.cubox.cmmn.service.CommonService;
import aero.cubox.core.vo.CommonVO;
import aero.cubox.core.vo.HolidayVO;
import aero.cubox.core.vo.PaginationVO;
import aero.cubox.core.vo.TerminalVO;
import aero.cubox.terminal.service.DigitTwinService;
import aero.cubox.terminal.service.TerminalService;
import aero.cubox.util.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TerminalController {

    @Resource(name = "terminalService")
    private TerminalService terminalService;

    @Resource(name = "commonService")
    private CommonService commonService;

    @Resource(name = "DigitTwinService")
    DigitTwinService digitTwinService;


    @RequestMapping(value = "/terminal/list.do")
    public String list(ModelMap model, @RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try{
            TerminalVO vo = new TerminalVO();

            String srchPage       = StringUtil.nvl(commandMap.get("srchPage"), "1");
            String srchRecPerPage = StringUtil.nvl(commandMap.get("srchRecPerPage"), "10");
            String srchCond1 = StringUtil.nvl(commandMap.get("srchCond1"), "");
            String srchCond2 = StringUtil.nvl(commandMap.get("srchCond2"), "");
            String keyword = StringUtil.nvl(commandMap.get("keyword"), "");

            vo.setSrchPage(Integer.parseInt(srchPage));
            vo.setSrchCnt(Integer.parseInt(srchRecPerPage));
            vo.autoOffset();

            vo.setSrchCond1(srchCond1);
            vo.setSrchCond2(srchCond2);
            vo.setKeyword(keyword);

            List<CommonVO> terminalTypCombList = commonService.getCommonCodeList("TerminalTyp");
            List<CommonVO> buildingCombList = terminalService.getBuildingList();

            int totalCnt = terminalService.getTerminalListCount(vo);
            List<TerminalVO> terminalList = terminalService.getTerminalList(vo);

            PaginationVO pageVO = new PaginationVO();
            pageVO.setCurPage(vo.getSrchPage());
            pageVO.setRecPerPage(vo.getSrchCnt());
            pageVO.setTotRecord(totalCnt);
            pageVO.setUnitPage(vo.getCurPageUnit());
            pageVO.calcPageList();

            model.addAttribute("terminalTypCombList", terminalTypCombList);
            model.addAttribute("buildingCombList", buildingCombList);
            model.addAttribute("terminalList", terminalList);
            model.addAttribute("data", vo);
            model.addAttribute("pagination", pageVO);

        }catch (Exception e){
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return "cubox/terminal/list";
    }

    @RequestMapping(value="/terminal/add.do", method= RequestMethod.GET)
    public String add(ModelMap model, HttpServletRequest request) throws Exception {
        List<CommonVO> doorCombList = terminalService.getDoorList();
        List<CommonVO> terminalTypCombList = commonService.getCommonCodeList("TerminalTyp");
        List<CommonVO> complexAuthTypCombList = commonService.getCommonCodeList("ComplexAuthTyp");
        List<CommonVO> faceAuthTypCombList = commonService.getCommonCodeList("FaceAuthTyp");
        List<CommonVO> opModeTypCombList = commonService.getCommonCodeList("OpModeTyp");

        model.addAttribute("doorCombList", doorCombList);
        model.addAttribute("terminalTypCombList", terminalTypCombList);
        model.addAttribute("complexAuthTypCombList", complexAuthTypCombList);
        model.addAttribute("faceAuthTypCombList", faceAuthTypCombList);
        model.addAttribute("opModeTypCombList", opModeTypCombList);

        return "cubox/terminal/add";
    }

    @RequestMapping(value="/terminal/detail/{id}", method= RequestMethod.GET)
    public String detail(ModelMap model, @PathVariable int id, HttpServletRequest request) throws Exception {
        HashMap data = terminalService.getTerminalDetail(id);

        model.addAttribute("isModify", false);
        model.addAttribute("data", data);

        return "cubox/terminal/detail";
    }

    @RequestMapping(value="/terminal/modify/{id}", method= RequestMethod.GET)
    public String modify(ModelMap model, @PathVariable int id, HttpServletRequest request) throws Exception {
        List<CommonVO> doorCombList = terminalService.getDoorList();
        List<CommonVO> terminalTypCombList = commonService.getCommonCodeList("TerminalTyp");
        List<CommonVO> complexAuthTypCombList = commonService.getCommonCodeList("ComplexAuthTyp");
        List<CommonVO> faceAuthTypCombList = commonService.getCommonCodeList("FaceAuthTyp");
        List<CommonVO> opModeTypCombList = commonService.getCommonCodeList("OpModeTyp");

        HashMap data = terminalService.getTerminalDetail(id);

        model.addAttribute("isModify", true);
        model.addAttribute("doorCombList", doorCombList);
        model.addAttribute("terminalTypCombList", terminalTypCombList);
        model.addAttribute("complexAuthTypCombList", complexAuthTypCombList);
        model.addAttribute("faceAuthTypCombList", faceAuthTypCombList);
        model.addAttribute("data", data);
        model.addAttribute("opModeTypCombList", opModeTypCombList);

        return "cubox/terminal/detail";
    }

    @ResponseBody
    @RequestMapping(value="/terminal/addTerminal.do")
    public ModelAndView addTerminal(HttpServletRequest request, @RequestParam HashMap<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try{
            int result = terminalService.addTerminal(param);
//            digitTwinService.sendToDigitTwin(param, "C");
            modelAndView.addObject("result", "success");
        }catch (Exception e){
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value="/terminal/deleteTerminal.do")
    public ModelAndView deleteTerminal(HttpServletRequest request, @RequestParam HashMap<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try{
            terminalService.deleteTerminal(param);
//            digitTwinService.sendToDigitTwin(param, "D");
            modelAndView.addObject("result", "success");
        }catch (Exception e){
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value="/terminal/modifyTerminal.do")
    public ModelAndView modifyTerminal(HttpServletRequest request, @RequestParam HashMap<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try{
            terminalService.modifyTerminal(param);
//            digitTwinService.sendToDigitTwin(param, "U");
            modelAndView.addObject("result", "success");
        }catch (Exception e){
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
            modelAndView.addObject("result", "fail");
        }

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/terminal/getDoorInfo.do")
    public ModelAndView getDoorInfo(HttpServletRequest request, @RequestParam Map<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try {
            Map data = terminalService.getDoorInfo(param);

            modelAndView.addObject("data", data);
            modelAndView.addObject("result", "success");
        } catch(Exception e) {
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/terminal/getBlackList.do")
    public ModelAndView getBlackList(HttpServletRequest request, @RequestParam Map<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try {
            List<Map> list = terminalService.getBlackList(param);

            modelAndView.addObject("list", list);

        } catch(Exception e) {
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/terminal/getWhiteList.do")
    public ModelAndView getWhiteList(HttpServletRequest request, @RequestParam Map<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try {
            List<Map> list = terminalService.getWhiteList(param);

            modelAndView.addObject("list", list);

        } catch(Exception e) {
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return modelAndView;
    }


    @RequestMapping(value="/terminal/black/modify/{id}", method= RequestMethod.GET)
    public String blackModify(ModelMap model, @PathVariable int id, HttpServletRequest request) throws Exception {
        model.addAttribute("wbTyp", "BLACK");
        model.addAttribute("id", id);

        return "cubox/terminal/wb";
    }

    @RequestMapping(value="/terminal/white/modify/{id}", method= RequestMethod.GET)
    public String whiteModify(ModelMap model, @PathVariable int id, HttpServletRequest request) throws Exception {
        model.addAttribute("wbTyp", "WHITE");
        model.addAttribute("id", id);

        return "cubox/terminal/wb";
    }

    @ResponseBody
    @RequestMapping(value = "/terminal/getEmpSourceList.do")
    public ModelAndView getEmpSourceList(HttpServletRequest request, @RequestParam Map<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try {
            List<Map> list = terminalService.getEmpSourceList(param);

            modelAndView.addObject("list", list);

        } catch(Exception e) {
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/terminal/getEmpTargetList.do")
    public ModelAndView getEmpTargetList(HttpServletRequest request, @RequestParam Map<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try {
            List<Map> list = terminalService.getEmpTargetList(param);

            modelAndView.addObject("list", list);

        } catch(Exception e) {
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value="/terminal/addWbList.do")
    public ModelAndView addWbList(HttpServletRequest request, @RequestParam HashMap<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try{
            terminalService.registWbList(param);
            modelAndView.addObject("result", "success");
        }catch (Exception e){
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return modelAndView;
    }

    @RequestMapping(value="/terminal/excelDownload.do", method = RequestMethod.POST)
    public void excelDownload(ModelMap model, @RequestParam Map<String, Object> commandMap, HttpServletResponse response) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        TerminalVO vo = objectMapper.convertValue(commandMap, TerminalVO.class);

        vo.setIsExcel("Y");

        List<TerminalVO> terminalList = terminalService.getTerminalList(vo);

        ///// Create Excel /////
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        Row row = null;
        Cell cell = null;
        int rowNum = 0;

        //// Header ////
        final String[] colNames = {"No", "건물", "층", "출입문명", "단말기코드", "관리번호", "단말기유형", "IP", "출입인증방식",
                                    "얼굴인증방식", "BlackList", "WhiteList", "등록일자", "사용"};
        // Header size
        final int[] colWidths = {1500, 3000, 3000, 8000, 4000, 3000, 3000, 5000, 5000, 3000, 3000, 3000, 3000, 1500};
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
        for (int i = 0; i < terminalList.size(); i++) {
            row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(terminalList.get(i).getBuildingNm());
            row.createCell(2).setCellValue(terminalList.get(i).getAreaNm());
            row.createCell(3).setCellValue(terminalList.get(i).getFloorNm());
            row.createCell(4).setCellValue(terminalList.get(i).getDoorNm());
            row.createCell(5).setCellValue(terminalList.get(i).getModelNm());
            row.createCell(6).setCellValue(terminalList.get(i).getMgmtNum());
            row.createCell(7).setCellValue(terminalList.get(i).getTerminalTypNm());
            row.createCell(8).setCellValue(terminalList.get(i).getIpAddr());
            row.createCell(9).setCellValue(terminalList.get(i).getComplexAuthTypNm());
            row.createCell(10).setCellValue(terminalList.get(i).getFaceAuthTypNm());
            row.createCell(11).setCellValue(terminalList.get(i).getBlackListCnt());
            row.createCell(12).setCellValue(terminalList.get(i).getWhiteListCnt());
            row.createCell(13).setCellValue(terminalList.get(i).getCreatedAt());
            row.createCell(14).setCellValue(terminalList.get(i).getUseYn());
        }

        // Date
        SimpleDateFormat fmt = new SimpleDateFormat("yyMMdd-HH_mm_ss");
        Date date = new Date();

        // File name
        String fileNm = "단말기관리목록_" + fmt.format(date) + ".xlsx";
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attatchment;filename=" + URLEncoder.encode(fileNm, "UTF-8"));
        wb.write(response.getOutputStream());
    }
}

