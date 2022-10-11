package aero.cubox.report.controller;


import aero.cubox.cmmn.service.CommonService;
import aero.cubox.core.vo.AlarmHistVO;
import aero.cubox.core.vo.CommonVO;
import aero.cubox.core.vo.EntHistVO;
import aero.cubox.core.vo.PaginationVO;
import aero.cubox.report.service.ReportService;
import aero.cubox.terminal.service.TerminalService;
import aero.cubox.util.StringUtil;
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
@RequestMapping("/report")
public class ReportController {

    @Resource(name = "reportService")
    private ReportService reportService;

    @Resource(name = "terminalService")
    private TerminalService terminalService;

    @Resource(name = "commonService")
    private CommonService commonService;


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
            String keyword = StringUtil.nvl(commandMap.get("keyword"), "");

            String fromDt = StringUtil.nvl(commandMap.get("fromDt"), "");
            String toDt = StringUtil.nvl(commandMap.get("toDt"), "");

            vo.setSrchPage(Integer.parseInt(srchPage));
            vo.setSrchCnt(Integer.parseInt(srchRecPerPage));
            vo.autoOffset();

            vo.setSrchCond1(srchCond1);
            vo.setSrchCond2(srchCond2);
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

}
