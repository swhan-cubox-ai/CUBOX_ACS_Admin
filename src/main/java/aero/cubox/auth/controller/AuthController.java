package aero.cubox.auth.controller;


import aero.cubox.auth.service.AuthService;
import aero.cubox.cmmn.service.CommonService;
import aero.cubox.core.vo.*;
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


@Controller
@RequestMapping(value="/auth")
public class AuthController {

    @Resource(name = "authService")
    private AuthService authService;

    @Resource(name = "commonService")
    private CommonService commonService;

    @Resource(name = "commonUtils")
    private CommonUtils commonUtils;



    @RequestMapping(value="/emp/list.do")
    public String empList(ModelMap model, @RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try{
            EmpVO vo = new EmpVO();

            String srchPage       = StringUtil.nvl(commandMap.get("srchPage"), "1");
            String srchRecPerPage = StringUtil.nvl(commandMap.get("srchRecPerPage"), "10");
            String keyword1 = StringUtil.nvl(commandMap.get("keyword1"), "");
            String keyword2 = StringUtil.nvl(commandMap.get("keyword2"), "");

            vo.setSrchPage(Integer.parseInt(srchPage));
            vo.setSrchCnt(Integer.parseInt(srchRecPerPage));
            vo.autoOffset();

            vo.setKeyword1(keyword1);
            vo.setKeyword2(keyword2);

            int totalCnt = authService.getEmpListCount(vo);
            List<EmpVO> empList = authService.getEmpList(vo);

            PaginationVO pageVO = new PaginationVO();
            pageVO.setCurPage(vo.getSrchPage());
            pageVO.setRecPerPage(vo.getSrchCnt());
            pageVO.setTotRecord(totalCnt);
            pageVO.setUnitPage(vo.getCurPageUnit());
            pageVO.calcPageList();

            model.addAttribute("empList", empList);
            model.addAttribute("data", vo);
            model.addAttribute("pagination", pageVO);

        } catch(Exception e) {
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return "cubox/auth/emp/list";
    }

    @RequestMapping(value="/dept/list.do")
    public String deptList(ModelMap model, @RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try{
            DeptVO vo = new DeptVO();

            String srchPage       = StringUtil.nvl(commandMap.get("srchPage"), "1");
            String srchRecPerPage = StringUtil.nvl(commandMap.get("srchRecPerPage"), "10");
            String keyword = StringUtil.nvl(commandMap.get("keyword"), "");

            vo.setSrchPage(Integer.parseInt(srchPage));
            vo.setSrchCnt(Integer.parseInt(srchRecPerPage));
            vo.autoOffset();

            vo.setKeyword(keyword);

            int totalCnt = authService.getDeptListCount(vo);
            List<DeptVO> deptList = authService.getDeptList(vo);

            PaginationVO pageVO = new PaginationVO();
            pageVO.setCurPage(vo.getSrchPage());
            pageVO.setRecPerPage(vo.getSrchCnt());
            pageVO.setTotRecord(totalCnt);
            pageVO.setUnitPage(vo.getCurPageUnit());
            pageVO.calcPageList();

            model.addAttribute("deptList", deptList);
            model.addAttribute("data", vo);
            model.addAttribute("pagination", pageVO);

        } catch(Exception e) {
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return "cubox/auth/dept/list";
    }

    @RequestMapping(value="/door/list.do")
    public String doorList(ModelMap model, @RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try{
            AuthVO vo = new AuthVO();

            String srchPage       = StringUtil.nvl(commandMap.get("srchPage"), "1");
            String srchRecPerPage = StringUtil.nvl(commandMap.get("srchRecPerPage"), "10");
            String srchCond = StringUtil.nvl(commandMap.get("srchCond"), "");
            String keyword = StringUtil.nvl(commandMap.get("keyword"), "");

            vo.setSrchPage(Integer.parseInt(srchPage));
            vo.setSrchCnt(Integer.parseInt(srchRecPerPage));
            vo.autoOffset();

            vo.setSrchCond(srchCond);
            vo.setKeyword(keyword);

            int totalCnt = authService.getAuthListCount(vo);
            List<AuthVO> authList = authService.getAuthList(vo);

            List<CommonVO> authTypList = commonService.getCommonCodeList("AuthTyp");

            PaginationVO pageVO = new PaginationVO();
            pageVO.setCurPage(vo.getSrchPage());
            pageVO.setRecPerPage(vo.getSrchCnt());
            pageVO.setTotRecord(totalCnt);
            pageVO.setUnitPage(vo.getCurPageUnit());
            pageVO.calcPageList();

            model.addAttribute("authTypList", authTypList);
            model.addAttribute("authList", authList);
            model.addAttribute("data", vo);
            model.addAttribute("pagination", pageVO);

        } catch(Exception e) {
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return "cubox/auth/door/list";
    }


    @RequestMapping(value="/emp/detail/{id}", method= RequestMethod.GET)
    public String empDetail(ModelMap model, @PathVariable int id, HttpServletRequest request) throws Exception {
        List<Map> authEntMyList = authService.getAuthEntMyList(id);
        HashMap data = authService.getEmpDetail(id);

        model.addAttribute("isModify", false);
        model.addAttribute("authEntMyList", authEntMyList);
        model.addAttribute("data", data);

        return "cubox/auth/emp/detail";
    }

    @RequestMapping(value="/door/detail/{id}", method= RequestMethod.GET)
    public String doorDetail(ModelMap model, @PathVariable int id, HttpServletRequest request) throws Exception {
        List<Map> authEntMyList = authService.getAuthEntMyList(id);
        HashMap data = authService.getDoorDetail(id);

        model.addAttribute("isModify", false);
        model.addAttribute("authEntMyList", authEntMyList);
        model.addAttribute("data", data);

        return "cubox/auth/door/detail";
    }

    @RequestMapping(value="/door/modify/{id}", method= RequestMethod.GET)
    public String doorModify(ModelMap model, @PathVariable int id, HttpServletRequest request) throws Exception {
        List<Map> authEntMyList = authService.getAuthEntMyList(id);
        HashMap data = authService.getDoorDetail(id);

        model.addAttribute("isModify", true);
        model.addAttribute("authEntMyList", authEntMyList);
        model.addAttribute("data", data);

        return "cubox/auth/door/detail";
    }

    @RequestMapping(value="/emp/modify/{id}", method= RequestMethod.GET)
    public String empModify(ModelMap model, @PathVariable int id, HttpServletRequest request) throws Exception {
        List<Map> authEntMyList = authService.getAuthEntMyList(id);
        HashMap data = authService.getEmpDetail(id);

        model.addAttribute("isModify", true);
        model.addAttribute("authEntMyList", authEntMyList);
        model.addAttribute("data", data);

        return "cubox/auth/emp/detail";
    }

    @ResponseBody
    @RequestMapping(value="/door/modifyDoor.do")
    public ModelAndView modifyDoor(HttpServletRequest request, @RequestParam HashMap<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try{
            authService.modifyDoor(param);
            modelAndView.addObject("result", "success");
        }catch (Exception e){
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
            modelAndView.addObject("result", "fail");
        }

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value="/emp/modifyEmp.do")
    public ModelAndView modifyEmp(HttpServletRequest request, @RequestParam HashMap<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try{
            authService.modifyEmp(param);
            modelAndView.addObject("result", "success");
        }catch (Exception e){
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
            modelAndView.addObject("result", "fail");
        }

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value="/door/getAuthItem.do")
    public ModelAndView getAuthItem(HttpServletRequest request, @RequestParam HashMap<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try{
            int id = Integer.parseInt((String)param.get("id"));
            String authTyp = (String)param.get("authTyp");

            List<Map> authItemList;

            if("EAT002".equals(authTyp)){
                authItemList = authService.getAuthDoorGrpItem(id);
            }else if("EAT003".equals(authTyp)){
                authItemList = authService.getAuthDoorItem(id);
            }else{
                authItemList = authService.getAuthBuildingItem(id);
            }

            modelAndView.addObject("result", "success");
            modelAndView.addObject("authItemList", authItemList);
        }catch (Exception e){
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
            modelAndView.addObject("result", "fail");
        }

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value="/emp/getAuthEntList.do")
    public ModelAndView getAuthEntList(HttpServletRequest request, @RequestParam HashMap<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try{
            int id = Integer.parseInt((String)param.get("id"));
            List<Map> authEntAllList = authService.getAuthEntAllList();
            List<Map> authEntMyList = authService.getAuthEntMyList(id);

            modelAndView.addObject("result", "success");
            modelAndView.addObject("authEntAllList", authEntAllList);
            modelAndView.addObject("authEntMyList", authEntMyList);
        }catch (Exception e){
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
            modelAndView.addObject("result", "fail");
        }

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value="/door/getDoorList.do")
    public ModelAndView getDoorList(HttpServletRequest request, @RequestParam HashMap<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try{
            int id = Integer.parseInt((String)param.get("id"));
            List<Map> list = authService.getDoorList(id);

            modelAndView.addObject("result", "success");
            modelAndView.addObject("list", list);
        }catch (Exception e){
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
            modelAndView.addObject("result", "fail");
        }

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value="/door/getDoorGrpList.do")
    public ModelAndView getDoorGrpList(HttpServletRequest request, @RequestParam HashMap<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try{
            List<Map> list = authService.getDoorGrpList(param);

            modelAndView.addObject("result", "success");
            modelAndView.addObject("list", list);
        }catch (Exception e){
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
            modelAndView.addObject("result", "fail");
        }

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value="/door/getBuildingList.do")
    public ModelAndView getBuildingList(HttpServletRequest request, @RequestParam HashMap<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try{
            List<Map> list = authService.getBuildingList();

            modelAndView.addObject("result", "success");
            modelAndView.addObject("list", list);
        }catch (Exception e){
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
            modelAndView.addObject("result", "fail");
        }

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value="/emp/deleteEmp.do")
    public ModelAndView deleteEmp(HttpServletRequest request, @RequestParam HashMap<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try{
            authService.deleteEmp(param);
            modelAndView.addObject("result", "success");
        }catch (Exception e){
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return modelAndView;
    }

    @RequestMapping(value="/door/add", method= RequestMethod.GET)
    public String authAdd(ModelMap model, HttpServletRequest request) throws Exception {
        return "cubox/auth/door/add";
    }

    @ResponseBody
    @RequestMapping(value="/door/addDoor.do")
    public ModelAndView addDoor(HttpServletRequest request, @RequestParam HashMap<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try{
            authService.registAuthDoor(param);
            modelAndView.addObject("result", "success");
        }catch (Exception e){
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
            modelAndView.addObject("result", "fail");
        }

        return modelAndView;
    }

    @RequestMapping(value="/emp/excelDownload.do", method = RequestMethod.POST)
    public void empExcelDownload(ModelMap model, @RequestParam Map<String, Object> commandMap, HttpServletResponse response) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        EmpVO vo = objectMapper.convertValue(commandMap, EmpVO.class);

        vo.setIsExcel("Y");

        List<EmpVO> empList = authService.getEmpList(vo);

        ///// Create Excel /////
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        Row row = null;
        Cell cell = null;
        int rowNum = 0;

        //// Header ////
        final String[] colNames = {"No", "인사번호", "소속", "기관", "부서", "성명", "유효일자", "등록일자"};
        // Header size
        final int[] colWidths = {1500, 3000, 7000, 7000, 5000, 3000, 3000, 3000};
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
        for (int i = 0; i < empList.size(); i++) {
            row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(empList.get(i).getEmpCd());
            row.createCell(2).setCellValue(empList.get(i).getBelongNm());
            row.createCell(3).setCellValue(empList.get(i).getInsttNm());
            row.createCell(4).setCellValue(empList.get(i).getDeptNm());
            row.createCell(5).setCellValue(empList.get(i).getEmpNm());
            row.createCell(6).setCellValue(empList.get(i).getExpiredDt());
            row.createCell(7).setCellValue(empList.get(i).getCreatedAt());
        }

        // Date
        SimpleDateFormat fmt = new SimpleDateFormat("yyMMdd-HH_mm_ss");
        Date date = new Date();

        // File name
        String fileNm = "인사정보관리목록_" + fmt.format(date) + ".xlsx";
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attatchment;filename=" + URLEncoder.encode(fileNm, "UTF-8"));
        wb.write(response.getOutputStream());
    }

    @RequestMapping(value="/dept/excelDownload.do", method = RequestMethod.POST)
    public void deptExcelDownload(ModelMap model, @RequestParam Map<String, Object> commandMap, HttpServletResponse response) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        DeptVO vo = objectMapper.convertValue(commandMap, DeptVO.class);

        vo.setIsExcel("Y");

        List<DeptVO> deptList = authService.getDeptList(vo);

        ///// Create Excel /////
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        Row row = null;
        Cell cell = null;
        int rowNum = 0;

        //// Header ////
        final String[] colNames = {"No", "기관", "부서", "등록일자"};
        // Header size
        final int[] colWidths = {1500, 7000, 7000, 3000};
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
        for (int i = 0; i < deptList.size(); i++) {
            row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(deptList.get(i).getInsttNm());
            row.createCell(2).setCellValue(deptList.get(i).getDeptNm());
            row.createCell(3).setCellValue(deptList.get(i).getCreatedAt());
        }

        // Date
        SimpleDateFormat fmt = new SimpleDateFormat("yyMMdd-HH_mm_ss");
        Date date = new Date();

        // File name
        String fileNm = "부서관리목록_" + fmt.format(date) + ".xlsx";
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attatchment;filename=" + URLEncoder.encode(fileNm, "UTF-8"));
        wb.write(response.getOutputStream());
    }

    @RequestMapping(value="/door/excelDownload.do", method = RequestMethod.POST)
    public void doorExcelDownload(ModelMap model, @RequestParam Map<String, Object> commandMap, HttpServletResponse response) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        AuthVO vo = objectMapper.convertValue(commandMap, AuthVO.class);

        vo.setIsExcel("Y");

        List<AuthVO> authList = authService.getAuthList(vo);

        ///// Create Excel /////
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        Row row = null;
        Cell cell = null;
        int rowNum = 0;

        //// Header ////
        final String[] colNames = {"No", "출입권한그룹명", "유형", "사원수", "등록일자", "사용"};
        // Header size
        final int[] colWidths = {1500, 7000, 5000, 3000, 3000, 1500};
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
        for (int i = 0; i < authList.size(); i++) {
            row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(authList.get(i).getAuthNm());
            row.createCell(2).setCellValue(authList.get(i).getAuthTypNm());
            row.createCell(3).setCellValue(authList.get(i).getAuthEmpCnt());
            row.createCell(4).setCellValue(authList.get(i).getCreatedAt());
            row.createCell(5).setCellValue(authList.get(i).getUseYn());
        }

        // Date
        SimpleDateFormat fmt = new SimpleDateFormat("yyMMdd-HH_mm_ss");
        Date date = new Date();

        // File name
        String fileNm = "출입문권한그룹관리목록_" + fmt.format(date) + ".xlsx";
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attatchment;filename=" + URLEncoder.encode(fileNm, "UTF-8"));
        wb.write(response.getOutputStream());
    }

}
