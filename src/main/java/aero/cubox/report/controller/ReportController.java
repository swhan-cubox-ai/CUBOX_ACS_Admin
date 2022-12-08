package aero.cubox.report.controller;


import aero.cubox.cmmn.service.CommonService;
import aero.cubox.core.vo.*;
import aero.cubox.report.service.ReportService;
import aero.cubox.terminal.service.TerminalService;
import aero.cubox.util.AES256Util;
import aero.cubox.util.CommonUtils;
import aero.cubox.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping("/report")
public class ReportController {

    @Resource(name = "reportService")
    private ReportService reportService;

    @Resource(name = "terminalService")
    private TerminalService terminalService;

    @Resource(name = "commonService")
    private CommonService commonService;

    @Resource(name = "commonUtils")
    private CommonUtils commonUtils;

    @Value("#{property['Globals.aes256.key']}")
    private String AES_KEY;


    @RequestMapping(value = "/entHist/list.do")
    public String entHistlist(ModelMap model, @RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try {
            EntHistVO vo = new EntHistVO();

            String srchPage = StringUtil.nvl(commandMap.get("srchPage"), "1");
            String srchRecPerPage = StringUtil.nvl(commandMap.get("srchRecPerPage"), "10");

            String srchCond1 = StringUtil.nvl(commandMap.get("srchCond1"), "");
            String srchCond2 = StringUtil.nvl(commandMap.get("srchCond2"), "");
            String srchCond3 = StringUtil.nvl(commandMap.get("srchCond3"), "");
            String keyword = StringUtil.nvl(commandMap.get("keyword"), "");

            String fromDt = StringUtil.nvl(commandMap.get("fromDt"), "");
            String toDt = StringUtil.nvl(commandMap.get("toDt"), "");

            Date now = new Date();
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
            } else {
                Date parseDt = StringToDate(toDt);
                Calendar cal2 = Calendar.getInstance();
                cal2.setTime(parseDt);
                cal2.add(Calendar.DATE, 1);
                toDt = commonUtils.getStringDate(cal2.getTime(), "yyyy-MM-dd");
            }

            vo.setSrchPage(Integer.parseInt(srchPage));
            vo.setSrchCnt(Integer.parseInt(srchRecPerPage));
            vo.autoOffset();

            vo.setSrchCond1(srchCond1);
            vo.setSrchCond2(srchCond2);
            vo.setSrchCond3(srchCond3);
            vo.setKeyword(keyword);

            vo.setFromDt(fromDt);
            vo.setToDt(toDt);

            int totalCnt = reportService.getEntHistListCount(vo);
            List<EntHistVO> entHistList = reportService.getEntHistList(vo);

            List<CommonVO> entEvtTypCombList = commonService.getCommonCodeList("EntEvtTyp");
            List<CommonVO> buildingCombList = terminalService.getBuildingList();

            PaginationVO pageVO = new PaginationVO();
            pageVO.setCurPage(vo.getSrchPage());
            pageVO.setRecPerPage(vo.getSrchCnt());
            pageVO.setTotRecord(totalCnt);
            pageVO.setUnitPage(vo.getCurPageUnit());
            pageVO.calcPageList();

            Date parseDt = StringToDate(toDt);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(parseDt);
            cal2.add(Calendar.DATE, -1);
            toDt = commonUtils.getStringDate(cal2.getTime(), "yyyy-MM-dd");

            vo.setFromDt(fromDt);
            vo.setToDt(toDt);

            model.addAttribute("entEvtTypCombList", entEvtTypCombList);
            model.addAttribute("buildingCombList", buildingCombList);
            model.addAttribute("entHistList", entHistList);
            model.addAttribute("data", vo);
            model.addAttribute("pagination", pageVO);

        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return "cubox/report/entHist/list";
    }

    @RequestMapping(value="/entHist/detail")
    public ModelAndView detail(ModelMap model, @RequestParam Map<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");
        Integer id =  Integer.parseInt(param.get("id").toString());
        Integer faceId =  Integer.parseInt(param.get("faceId").toString());
        String empCd =  (String) param.get("empCd").toString();
        EntHistBioVO vo = new EntHistBioVO();
        vo.setEnt_hist_id(id);
        EntHistBioVO data = reportService.selectEntFaceOne(vo);
        int faceIsFlag = data.getEnt_face_img().length();
        if(faceIsFlag != 0){
            byte[] img = byteArrDecode(data.getEnt_face_img());
            String bioFace = new String(Base64.getEncoder().encode(img));
            modelAndView.addObject("bioFace", bioFace);
        } else {
            modelAndView.addObject("bioFace", null);
        }


        FaceVO faceVO = reportService.selectFaceOne(faceId);
        if(faceVO != null) {
            byte[] regImg = faceVO.getFace_img();
            String regFace = new String(Base64.getEncoder().encode(regImg));
            modelAndView.addObject("regFace", regFace);
        }

        return modelAndView;
    }


    @RequestMapping(value = "/alarmHist/list.do")
    public String alarmHistList(ModelMap model, @RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try {
            AlarmHistVO vo = new AlarmHistVO();

            String srchPage = StringUtil.nvl(commandMap.get("srchPage"), "1");
            String srchRecPerPage = StringUtil.nvl(commandMap.get("srchRecPerPage"), "10");

            String srchCond = StringUtil.nvl(commandMap.get("srchCond"), "");
            String keyword = StringUtil.nvl(commandMap.get("keyword"), "");

            String fromDt = StringUtil.nvl(commandMap.get("fromDt"), "");
            String toDt = StringUtil.nvl(commandMap.get("toDt"), "");

            vo.setSrchPage(Integer.parseInt(srchPage));
            vo.setSrchCnt(Integer.parseInt(srchRecPerPage));
            vo.autoOffset();

            vo.setSrchCond(srchCond);
            vo.setKeyword(keyword);

            vo.setFromDt(fromDt);
            vo.setToDt(toDt);

            int totalCnt = reportService.getAlarmHistListCount(vo);
            List<AlarmHistVO> alarmHistList = reportService.getAlarmHistList(vo);

            List<CommonVO> doorAlarmEvtTypCombList = commonService.getCommonCodeList("DoorAlarmTyp");

            PaginationVO pageVO = new PaginationVO();
            pageVO.setCurPage(vo.getSrchPage());
            pageVO.setRecPerPage(vo.getSrchCnt());
            pageVO.setTotRecord(totalCnt);
            pageVO.setUnitPage(vo.getCurPageUnit());
            pageVO.calcPageList();

            model.addAttribute("doorAlarmEvtTypCombList", doorAlarmEvtTypCombList);
            model.addAttribute("alarmHistList", alarmHistList);
            model.addAttribute("data", vo);
            model.addAttribute("pagination", pageVO);

        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return "cubox/report/alarmHist/list";
    }

    @ResponseBody
    @RequestMapping(value = "/alarmHist/getDoorDetail.do")
    public ModelAndView getDoorDetail(HttpServletRequest request, @RequestParam HashMap<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try {
            HashMap data = reportService.getDoorDetail(param);

            modelAndView.addObject("data", data);
            modelAndView.addObject("result", "success");
        } catch(Exception e) {
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
            modelAndView.addObject("result", "fail");
        }

        return modelAndView;
    }


    @RequestMapping(value="/imagView/{id}")
    public void imgView(HttpServletRequest request, @PathVariable int id, HttpServletResponse response){
        try {
            HashMap<String, Object> dataMap = reportService.getEntHistBioImg(id);

            if(dataMap != null){
                byte[] imgByte = (byte[]) dataMap.get("ent_face_img");
                response.setContentType("image/gif");
                OutputStream ops = response.getOutputStream();
                ops.write(imgByte);
                ops.flush();
                ops.close();
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    @RequestMapping(value="/err/faceFeatureList.do")
    public String list(ModelMap model, @RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try {
            FaceFeatureErrVO vo = new FaceFeatureErrVO();
            String srchPage       = StringUtil.nvl(commandMap.get("srchPage"), "1");
            String srchRecPerPage = StringUtil.nvl(commandMap.get("srchRecPerPage"), "10");

            String empCd = StringUtil.nvl(commandMap.get("emp_cd"), null);
            String featureTyp = StringUtil.nvl(commandMap.get("feature_typ"), null);
            String empNm = StringUtil.nvl(commandMap.get("emp_nm"), null);


            vo.setFace_feature_typ(featureTyp);
            vo.setEmp_cd(empCd);
            vo.setEmp_nm(empNm);

            vo.setSrchPage(Integer.parseInt(srchPage));
            vo.setSrchCnt(Integer.parseInt(srchRecPerPage));
            vo.autoOffset();


            List<FaceFeatureErrVO> list = reportService.selectFaceFeatureErrList(vo);
            int totalCnt = reportService.getFaceFeatureErrCount(vo);

            PaginationVO pageVO = new PaginationVO();
            pageVO.setCurPage(vo.getSrchPage());
            pageVO.setRecPerPage(vo.getSrchCnt());
            pageVO.setTotRecord(totalCnt);
            pageVO.setUnitPage(vo.getCurPageUnit());
            pageVO.calcPageList();


            List<CommonVO> featureTypList = commonService.getCommonCodeList("FaceFeatureTyp");

            model.addAttribute("featureTypList", featureTypList);
            model.addAttribute("errList", list);
            model.addAttribute("cntPerPage", "10");
            model.addAttribute("data", vo);
            model.addAttribute("pagination", pageVO);

        } catch(Exception e) {
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return "cubox/report/featureErr/list";
    }

    @RequestMapping(value="/err/faceFeature/detail")
    public ModelAndView detailErr(ModelMap model, @RequestParam Map<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");
        Integer id =  Integer.parseInt(param.get("id").toString());
        FaceFeatureErrVO vo = new FaceFeatureErrVO();
        vo.setId(id);
        FaceFeatureErrVO data = reportService.selectFaceFeatureErrOne(vo);

        byte[] errImg = data.getFace_img();
        String errorFace = new String(Base64.getEncoder().encode(errImg));

        modelAndView.addObject("errorFace", errorFace);

        return modelAndView;
    }


    public String byteArrEncode(byte[] bytes) throws Exception {
        AES256Util aes256Util = new AES256Util();
        String result =  aes256Util.byteArrEncode(bytes, AES_KEY);
        return result;
    }

    private byte[] byteArrDecode(String encoded) throws Exception {
        AES256Util aes256Util = new AES256Util();
        byte[] result =  aes256Util.byteArrDecode(encoded, AES_KEY);
        return result;
    }

    private Date StringToDate(String dateStr) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(dateStr);
        return date;
    }



}
