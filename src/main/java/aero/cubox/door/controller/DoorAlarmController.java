package aero.cubox.door.controller;

import aero.cubox.auth.service.AuthService;
import aero.cubox.cmmn.service.CommonService;
import aero.cubox.core.vo.PaginationVO;
import aero.cubox.door.service.DoorAlarmService;
import aero.cubox.door.service.DoorScheduleService;
import aero.cubox.door.service.DoorService;
import aero.cubox.terminal.service.TerminalService;
import aero.cubox.util.CommonUtils;
import aero.cubox.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "/door/alarm/")
public class DoorAlarmController {

    @Resource(name = "commonUtils")
    private CommonUtils commonUtils;

    @Resource(name = "commonService")
    private CommonService commonService;

    @Resource(name = "doorService")
    private DoorService doorService;

    @Resource(name = "doorAlarmService")
    private DoorAlarmService doorAlarmService;
    
    @Resource(name = "doorScheduleService")
    private DoorScheduleService doorScheduleService;

    @Resource(name = "terminalService")
    private TerminalService terminalService;

    @Resource(name = "authService")
    private AuthService authService;

    private static final Logger LOGGER = LoggerFactory.getLogger(DoorAlarmController.class);

    private int curPageUnit= 10; //한번에 표시할 페이지 번호 개수
    private String initSrchRecPerPage = "10"; //한번에 표시할 페이지 번호 개수

    public int autoOffset(int srchPage, int srchCnt ){
        int off = (srchPage - 1) * srchCnt;
        if(off<0) off = 0;
        return off;
    }
    /**
     * 출입문 알람 그룹 화면
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list.do")
    public String showAlarmGroupList(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try {
            int srchPage       = Integer.parseInt(StringUtil.nvl(commandMap.get("srchPage"), "1"));
            int srchRecPerPage = Integer.parseInt(StringUtil.nvl(commandMap.get("srchRecPerPage"), initSrchRecPerPage));
            String keyword = StringUtil.nvl(commandMap.get("keyword"), "");

            HashMap<String, Object> paramMap = new HashMap();

            paramMap.put("keyword", keyword);
            paramMap.put("srchCnt", srchRecPerPage);
            paramMap.put("offset", autoOffset(srchPage, srchRecPerPage));

            List<HashMap> doorAlarmGroupList = doorAlarmService.getDoorAlarmGrpList(paramMap);
            int totalCnt = doorAlarmService.getDoorAlarmGrpListCount(paramMap);

            PaginationVO pageVO = new PaginationVO();
            pageVO.setCurPage(srchPage);
            pageVO.setRecPerPage(srchRecPerPage);
            pageVO.setTotRecord(totalCnt);
            pageVO.setUnitPage(curPageUnit);
            pageVO.calcPageList();

            model.addAttribute("doorAlarmGroupList", doorAlarmGroupList);
            model.addAttribute("keyword", keyword);
            model.addAttribute("pagination", pageVO);

        } catch (Exception e){
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return "cubox/door/alarm/list";
    }

    /**
     * 출입문 알람 그룹 목록 조회
     *
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listAjax.do", method = RequestMethod.GET)
    public ModelAndView getAlarmGroupListAjax(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        String keyword = StringUtil.nvl(commandMap.get("keyword"), "");
        HashMap parmaMap = new HashMap();

        if( keyword.length() > 0){
            parmaMap.put("keyword",keyword);
        }

        List<HashMap> alarmGroupList = doorAlarmService.getDoorAlarmGrpList(parmaMap);
        modelAndView.addObject("alarmGroupList", alarmGroupList);

        return modelAndView;
    }




    // 출입문 알람 그룹 관리 상세
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(ModelMap model, @PathVariable int id, HttpServletRequest request) throws Exception {

        HashMap doorGroupDetail = doorAlarmService.getDoorAlarmGrpDetail(id);

        HashMap parmaMap = new HashMap();
        List<HashMap> scheduleList = doorScheduleService.getDoorScheduleList(parmaMap);      // 스케쥴 목록

        model.addAttribute("doorGroupDetail", doorGroupDetail);

        return "cubox/door/alarm/detail";
    }


    // 출입문 알람 그룹 등록 화면
    @RequestMapping(value = "/add.do", method = RequestMethod.GET)
    public String showAlarmGroupAddView(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        return "cubox/door/alarm/add";
    }



    // 출입문 알람 그룹 등록
    @ResponseBody
    @RequestMapping(value = "/save.do", method = RequestMethod.POST)
    public ModelAndView addAalarmGroup(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        String resultCode = "Y";
        try {
            doorAlarmService.addDoorAlarmGrp(commandMap);
        } catch (Exception e) {
            e.getStackTrace();
            resultCode = "N";
        }

        modelAndView.addObject("resultCode", resultCode);

        return modelAndView;
    }


    @ResponseBody
    @RequestMapping(value="/modify/{id}", method= RequestMethod.POST)
    public ModelAndView modify(ModelMap model, @PathVariable String id, HttpServletRequest request,@RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        String resultCode = "Y";

        if( id == null){
            resultCode = "N";
        }
        String nm = StringUtil.nvl(commandMap.get("nm"), "");
        String scheduleId = StringUtil.nvl(commandMap.get("scheduleId"), "");
        String doorIds = StringUtil.nvl(commandMap.get("doorIds"), "");

        HashMap param = new HashMap();

        param.put("id", Integer.parseInt(id));
        param.put("nm", nm);
        param.put("doorSchId", scheduleId);
        param.put("doorIds", doorIds);

        try {
            doorAlarmService.updateDoorAlarmGrp(param);

        } catch (Exception e) {
            e.getStackTrace();
            resultCode = "N";
        }

        model.addAttribute("resultCode", resultCode);

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value="/delete/{id}")
    public ModelAndView delete(ModelMap model, @PathVariable int id, HttpServletRequest request) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");
        String resultCode = "Y";

        try {
            doorAlarmService.deleteDoorAlarmGrp(id);
        } catch (Exception e) {
            e.getStackTrace();
            resultCode = "N";
        }


        model.addAttribute("resultCode", resultCode);

        return modelAndView;
    }


}
