package aero.cubox.door.controller;

import aero.cubox.door.service.DoorService;
import aero.cubox.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 출입문관리
 */
@Controller
@RequestMapping(value = "/door/")
public class DoorController {

    @Resource(name = "doorService")
    private DoorService doorService;

    @Resource(name = "commonUtils")
    private CommonUtils commonUtils;

    private static final Logger LOGGER = LoggerFactory.getLogger(DoorController.class);

    /**
     * 출입문관리 - view
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/management.do")
    public String doorManagementDetail(ModelMap model) throws Exception {
        //todo 세션처리
        return "cubox/door/doorManagementDetail";
    }


    /**
     * 출입문 목록 조회
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/list.do", method= RequestMethod.GET)
    public ModelAndView getDoorList(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        //List<Map> areaList = doorService.getAreaList(commandMap);
        List<Map> workplaceList = doorService.getWorkplaceList(commandMap); //사업장 목록
        List<Map> buildingList = doorService.getBuildingList(commandMap);   //빌딩 목록
        List<HashMap> floorList = doorService.getFloorList(commandMap);         //층 목록
        List<Map> doorList = doorService.getDoorList(commandMap);           //출입문 목록

        modelAndView.addObject("workplaceList", workplaceList);
        modelAndView.addObject("buildingList", buildingList);
        modelAndView.addObject("floorList", floorList);
        modelAndView.addObject("doorList", doorList);

        return modelAndView;
    }

    /**
     * 출입문 정보 조회
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/detail.do", method= RequestMethod.GET)
    public ModelAndView getDoorInformation(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        System.out.println("getDoorInformation");
        LOGGER.debug("getDoor");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        LOGGER.debug("param doorId="+commandMap.get("doorId"));
        HashMap<String, Object> param = new HashMap<String, Object>();

        param.put("id", commandMap.get("doorId") );

        HashMap doorInfo = (HashMap) doorService.getDoorInformation(param);

        modelAndView.addObject("doorInfo", doorInfo);

        return modelAndView;
    }

    /**
     * 출입문 정보 등록
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/add.do", method= RequestMethod.POST)
    public ModelAndView addDoor(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        String doorNm = commandMap.get("doorNm").toString();
        String doorScheduleId = commandMap.get("doorSchedule").toString();
        String doorAlarmGroupId = commandMap.get("doorAlarmGroup").toString();
        String terminalCd = commandMap.get("terminalCd").toString();
        String mgmtNum = commandMap.get("mgmtNum").toString();
        String doorGrId = commandMap.get("doorGrId").toString();

        HashMap param = new HashMap();

        param.put("doorNm", doorNm );
        param.put("doorScheduleId", doorScheduleId);
        param.put("doorAlarmGroupId", doorAlarmGroupId);
        param.put("terminalCd", terminalCd);
        param.put("mgmtNum", mgmtNum);
        param.put("doorGrId", doorGrId);

        try {
            doorService.addDoor(param);

        } catch(Exception e) {
            e.getStackTrace();
            modelAndView.addObject("result", "N");
        }
        modelAndView.addObject("result", "Y");


        return modelAndView;
    }


    /**
     * 출입문 정보 수정
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/update.do", method= RequestMethod.POST)
    public ModelAndView updateDoor(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        String doorId = commandMap.get("doorId").toString();
        String doorNm = commandMap.get("doorNm").toString();
        String doorScheduleId = commandMap.get("doorSchedule").toString();
        String doorAlarmGroupId = commandMap.get("doorAlarmGroup").toString();
        String terminalCd = commandMap.get("terminalCd").toString();
        String mgmtNum = commandMap.get("mgmtNum").toString();
        String doorGrId = commandMap.get("doorGrId").toString();

        try {


        } catch(Exception e) {
            e.getStackTrace();
            modelAndView.addObject("result", "N");
        }
        modelAndView.addObject("result", "Y");

        return modelAndView;
    }


    /**
     * 출입문 삭제
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/delete.do")
    public ModelAndView deleteDoor(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try {
            doorService.deleteDoorInformation(commandMap);
        } catch(Exception e) {
            e.getStackTrace();
            modelAndView.addObject("result", "N");
        }
        modelAndView.addObject("result", "Y");

        return modelAndView;
    }




    /**
     * 출입문 그룹 목록 조회
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/group/listView.do", method= RequestMethod.GET)
    public String getDoorGroupListView(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        //todo 세션

        return "cubox/door/groupList";
    }

    /**
     * 출입문 그룹 목록 조회
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/group/list.do", method= RequestMethod.GET)
    public ModelAndView getDoorGroupList(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        List<HashMap> doorGroupList = doorService.getDoorGroupList(commandMap);

        modelAndView.addObject("doorGroupList", doorGroupList);

        return modelAndView;
    }



    // 출입문 그룹 관리 상세
    @RequestMapping(value="/group/detail.do")
    public String groupDetail(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {


        return "cubox/door/groupDetail";
    }

    // 출입문 그룹 관리 등록/수정
    @RequestMapping(value = "/group/add.do")
    public String groupAdd(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        if (commandMap.get("editMode").equals("edit")) {

        }
        model.addAttribute("editMode", commandMap.get("editMode"));

        return "groupAdd";
    }






    /**
     * 스케줄 관리 - view
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/schedule.do")
    public String schedule(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {
        //todo 세션처리

        return "cubox/door/scheduleList";
    }


    /**
     * 스케줄 목록 조회
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/schedule/list.do", method= RequestMethod.GET)
    public ModelAndView getScheduleList(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        List<HashMap> scheduleList = doorService.getScheduleList(commandMap);
        modelAndView.addObject("scheduleList", scheduleList);

        return modelAndView;
    }

    /**
     * 스케쥴 추가
     * @param model
     * @param commandMap
     * @param redirectAttributes
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/schedule/add.do", method= RequestMethod.POST)
    public String scheduleAdd(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        if (commandMap.get("editMode").equals("edit")) {
            model.addAttribute("schName", commandMap.get("schName"));
            model.addAttribute("schUseYn", commandMap.get("schUseYn"));
            model.addAttribute("gateGroup", commandMap.get("gateGroup"));
        }
        model.addAttribute("editMode", commandMap.get("editMode"));

        LOGGER.debug("editMode : " + model.get("editMode"));
        LOGGER.debug("schName : " + model.get("schName"));
        return "scheduleAdd";
    }

    /**
     * 스케줄 목록 상세
     * @param model
     * @param commandMap
     * @param redirectAttributes
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/schedule/detail.do", method= RequestMethod.GET)
    public String showScheduleDetail(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        String[] days = {"월", "화", "수", "목", "금", "토", "일"};
        String[] days_eng = {"mon", "tue", "wed", "thu", "fri", "sat", "sun"};

        model.addAttribute("days", days);
        model.addAttribute("days_eng", days_eng);
        return "cubox/door/scheduleDetail";
    }


    /**
     * 스케줄 삭제
     * @param model
     * @param commandMap
     * @param redirectAttributes
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/schedule/delete.do")
    public ModelAndView deleteSchedule(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        String result= "N";

        result = "Y";
        model.addAttribute("result", result);
        return modelAndView;
    }





    // 출입문 알람 그룹
    @RequestMapping(value="/alarmGroup/list.do")
    public String alarm(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {
        //todo 세션처리

        return "cubox/door/alarmGroupList";
    }

    // 출입문 알람 그룹 상세
    @RequestMapping(value="/alarmGroup/detail.do")
    public String viewAlarmDetail(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        return "alarmGroupDetail";
    }

    // 출입문 알람 그룹 등록
    @RequestMapping(value = "/alarmGroup/add.do")
    public String alarmAdd(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        if (commandMap.get("editMode").equals("edit")) {
            model.addAttribute("alNm", commandMap.get("alNm"));
            model.addAttribute("alType", commandMap.get("alType"));
            model.addAttribute("alTime", commandMap.get("alTime"));
            model.addAttribute("alUseYn", commandMap.get("alUseYn"));
            model.addAttribute("alDoorCnt", commandMap.get("alDoorCnt"));
        }
        model.addAttribute("editMode", commandMap.get("editMode"));

        return "addAlarm";
    }





    /**
     * 단말기 목록 조회(검색)
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/terminal/list.do", method= RequestMethod.GET)
    public ModelAndView searchTerminal(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        List<HashMap> terminalList = doorService.getTerminalList(commandMap);

        modelAndView.addObject("terminalList", terminalList);

        return modelAndView;
    }


    /**
     * 단말기 정보 조회
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/terminal/detail.do", method= RequestMethod.GET)
    public ModelAndView getTerminal(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        HashMap terminalInfo= doorService.getTerminal(commandMap);
        modelAndView.addObject("terminalInfo", terminalInfo);

        return modelAndView;
    }



    // 일괄 등록 양식 다운로드(출입문)
    @RequestMapping(value="/batch/registrationForm.do")
    public ModelAndView downloadRegistrationForm(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        return modelAndView;
    }

    // 일괄 등록 (출입문 Excel)
    @RequestMapping(value="/batch/insert.do")
    public ModelAndView insertBatch(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        return modelAndView;
    }

}
