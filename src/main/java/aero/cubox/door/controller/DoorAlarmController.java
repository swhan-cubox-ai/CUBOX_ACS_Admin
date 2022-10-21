package aero.cubox.door.controller;

import aero.cubox.auth.service.AuthService;
import aero.cubox.cmmn.service.CommonService;
import aero.cubox.door.service.DoorAlarmService;
import aero.cubox.door.service.DoorService;
import aero.cubox.terminal.service.TerminalService;
import aero.cubox.util.CommonUtils;
import aero.cubox.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
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

    @Resource(name = "terminalService")
    private TerminalService terminalService;

    @Resource(name = "authService")
    private AuthService authService;

    private static final Logger LOGGER = LoggerFactory.getLogger(DoorAlarmController.class);

    /**
     * 출입문 알람 그룹 화면
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listView.do", method = RequestMethod.GET)
    public String showAlarmGroupList(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {
        //todo 세션처리

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
    @RequestMapping(value = "/list.do", method = RequestMethod.GET)
    public ModelAndView getAlarmGroupList(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

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

    // 출입문 알람 그룹 상세
    @RequestMapping(value = "/detail.do", method = RequestMethod.GET)
    public String showAlarmDetail(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        return "cubox/door/alarm/detail";
    }

    // 출입문 알람 그룹 등록 화면
    @RequestMapping(value = "/addView.do", method = RequestMethod.GET)
    public String showAlarmGroupAddView(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        return "cubox/door/alarm/add";
    }



    // 출입문 알람 그룹 등록
    @ResponseBody
    @RequestMapping(value = "/add.do", method = RequestMethod.POST)
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

}
