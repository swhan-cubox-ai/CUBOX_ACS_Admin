package aero.cubox.door.controller;

import aero.cubox.cmmn.service.CommonService;
import aero.cubox.core.vo.CommonVO;
import aero.cubox.core.vo.PaginationVO;
import aero.cubox.core.vo.TerminalVO;
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

    @Resource(name = "terminalService")
    private TerminalService terminalService;

    @Resource(name = "commonService")
    private CommonService commonService;

    private static final Logger LOGGER = LoggerFactory.getLogger(DoorController.class);

    /**
     * 출입문관리 - view
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/management.do")
    public String doorManagementDetail(ModelMap model) throws Exception {
        //todo 세션처리

        // TODO : scheduleList, alarmGroupList 넘기기
        HashMap parmaMap = new HashMap();
        List<Map> workplaceList = doorService.getWorkplaceList(parmaMap); //사업장 목록
        List<Map> buildingList = doorService.getBuildingList(parmaMap);   //빌딩 목록
        List<Map> areaList = doorService.getAreaList(parmaMap);           //지역 목록
        List<HashMap> floorList = doorService.getFloorList(parmaMap);     //층 목록
        List<HashMap> scheduleList = doorService.getScheduleList(parmaMap);         //스케쥴 목록
        List<HashMap> doorAlarmGrpList = doorService.getDoorAlarmGrpList(parmaMap); // 출입물 알람 그룹 목록

        model.addAttribute("workplaceList", workplaceList);
        model.addAttribute("buildingList", buildingList);
        model.addAttribute("areaList", areaList);
        model.addAttribute("floorList", floorList);
        model.addAttribute("scheduleList", scheduleList);
        model.addAttribute("doorAlarmGrpList", doorAlarmGrpList);

        return "cubox/door/doorManagementDetail";
    }


    /**
     * 출입문 목록 조회
     *
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list.do", method = RequestMethod.GET)
    public ModelAndView getDoorList(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        HashMap parmaMap = new HashMap();

        List<Map> workplaceList = doorService.getWorkplaceList(parmaMap); //사업장 목록
        List<Map> buildingList = doorService.getBuildingList(parmaMap);   //빌딩 목록
        List<Map> areaList = doorService.getAreaList(parmaMap);           //지역 목록
        List<HashMap> floorList = doorService.getFloorList(parmaMap);     //층 목록
        List<Map> doorList = doorService.getDoorList(parmaMap);           //출입문 목록

        model.addAttribute("workplaceList", workplaceList);
        model.addAttribute("buildingList", buildingList);
        model.addAttribute("areaList", areaList);
        model.addAttribute("floorList", floorList);
        modelAndView.addObject("doorList", doorList);

        return modelAndView;
    }

    /**
     * 출입문 정보 조회
     *
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/detail.do", method = RequestMethod.GET)
    public ModelAndView getDoorInformation(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        LOGGER.debug("getDoor");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        LOGGER.debug("param doorId=" + commandMap.get("doorId"));
        HashMap<String, Object> param = new HashMap<String, Object>();

        param.put("id", commandMap.get("doorId"));

        HashMap doorInfo = (HashMap) doorService.getDoorDetail(param); // TODO: 스케쥴 이름은 필요 없음

        modelAndView.addObject("doorInfo", doorInfo);

        return modelAndView;
    }

    /**
     * 출입문 정보 등록
     *
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/add.do", method = RequestMethod.POST)
    public ModelAndView addDoor(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {
        LOGGER.debug("출입문 등록");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        String doorNm = commandMap.get("doorNm").toString();
        String buildingId = commandMap.get("buildingId").toString();
        String areaId = commandMap.get("areaId").toString();
        String floorId = commandMap.get("floorId").toString();
        String scheduleId = commandMap.get("scheduleId").toString();
        String alarmGroupId = commandMap.get("alarmGroupId").toString();
        String terminalCd = commandMap.get("terminalCd").toString();
        String mgmtNum = commandMap.get("mgmtNum").toString();
        String doorGrId = commandMap.get("doorGrId").toString();

        HashMap param = new HashMap();

        param.put("doorNm", doorNm);
        param.put("buildingId", buildingId);
        param.put("areaId", areaId);
        param.put("floorId", floorId);
        param.put("doorScheduleId", scheduleId);
        param.put("alarmGroupId", alarmGroupId);
        param.put("terminalCd", terminalCd);
        param.put("mgmtNum", mgmtNum);
        param.put("doorGrId", doorGrId);

        try {
            doorService.addDoor(param);

        } catch (Exception e) {
            e.getStackTrace();
            modelAndView.addObject("resultCode", "N");
        }
        modelAndView.addObject("resultCode", "Y");

        return modelAndView;
    }


    /**
     * 출입문 정보 수정
     *
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
    public ModelAndView updateDoor(ModelMap model, @RequestParam Map<String, Object> commandMap) throws
            Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        String doorId = commandMap.get("doorId").toString();
        String doorNm = commandMap.get("doorNm").toString();
        String buildingId = commandMap.get("buildingId").toString();
        String areaId = commandMap.get("areaId").toString();
        String floorId = commandMap.get("floorId").toString();
        String scheduleId = commandMap.get("scheduleId").toString();
        String alarmGroupId = commandMap.get("alarmGroupId").toString();
        String terminalCd = commandMap.get("terminalCd").toString();
        String mgmtNum = commandMap.get("mgmtNum").toString();
        String doorGrId = commandMap.get("doorGrId").toString();

        HashMap param = new HashMap();

        param.put("doorId", doorId);
        param.put("doorNm", doorNm);
        param.put("buildingId", buildingId);
        param.put("areaId", areaId);
        param.put("floorId", floorId);
        param.put("doorScheduleId", scheduleId);
        param.put("alarmGroupId", alarmGroupId);
        param.put("terminalCd", terminalCd);
        param.put("mgmtNum", mgmtNum);
        param.put("doorGrId", doorGrId);

        try {
            doorService.updateDoor(param);

        } catch (Exception e) {
            e.getStackTrace();
            modelAndView.addObject("resultCode", "N");
        }
        modelAndView.addObject("resultCode", "Y");

        return modelAndView;
    }


    /**
     * 출입문 삭제
     *
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delete.do")
    public ModelAndView deleteDoor(ModelMap model, @RequestParam Map<String, Object> commandMap) throws
            Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");


        try {
            doorService.deleteDoor(commandMap);
        } catch (Exception e) {
            e.getStackTrace();
            modelAndView.addObject("resultCode", "N");
        }
        modelAndView.addObject("resultCode", "Y");

        return modelAndView;
    }


    /**
     * 출입문 그룹 목록 조회
     *
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/group/listView.do", method = RequestMethod.GET)
    public String showDoorGroupListView(ModelMap model, @RequestParam Map<String, Object> commandMap) throws
            Exception {

        //todo 세션

        return "cubox/door/groupList";
    }

    /**
     * 출입문 그룹 목록 조회
     *
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/group/list.do", method = RequestMethod.GET)
    public ModelAndView getDoorGroupList(ModelMap model, @RequestParam Map<String, Object> commandMap) throws
            Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        List<HashMap> doorGroupList = doorService.getDoorGroupList(commandMap);

        modelAndView.addObject("doorGroupList", doorGroupList);

        return modelAndView;
    }


    // 출입문 그룹 관리 상세
    @RequestMapping(value = "/group/detail.do")
    public String showGroupDetail(ModelMap
                                          model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

//        HashMap doorGroupDetail = doorService.getDoorGroupDetail(commandMap);

        return "cubox/door/groupDetail";
    }

    // 출입문 그룹 관리 등록화면
    @RequestMapping(value = "/group/addView.do", method = RequestMethod.GET)
    public String showGroupAddView(ModelMap
                                           model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {


        return "cubox/door/groupAdd";
    }

    // 출입문 그룹 관리 등록/수정
    @RequestMapping(value = "/group/add.do", method = RequestMethod.POST)
    public ModelAndView addGroup(ModelMap
                                         model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        String resultCode = "Y";
        try {

            doorService.addDoorGroup(commandMap);
        } catch (Exception e) {
            e.getStackTrace();
            resultCode = "N";
        }

        modelAndView.addObject("resultCode", resultCode);

        return modelAndView;
    }


    /**
     * 스케줄 관리 - view
     *
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/schedule/listView.do", method = RequestMethod.GET)
    public String showScheduleList(ModelMap model, @RequestParam Map<String, Object> commandMap) throws
            Exception {
        //todo 세션처리

        return "cubox/door/scheduleList";
    }


    /**
     * 스케줄 목록 조회
     *
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/schedule/list.do")
    public ModelAndView getScheduleList(ModelMap model, @RequestParam Map<String, Object> commandMap) throws
            Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        List<HashMap> scheduleList = doorService.getScheduleList(commandMap);
        modelAndView.addObject("scheduleList", scheduleList);

        return modelAndView;
    }

    /**
     * 스케쥴 추가
     *
     * @param model
     * @param commandMap
     * @param redirectAttributes
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/schedule/addView.do", method = RequestMethod.GET)
    public String showScheduleAddView(ModelMap
                                              model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        return "cubox/door/scheduleAdd";
    }

    @RequestMapping(value = "/schedule/add.do", method = RequestMethod.POST)
    public ModelAndView addSchedule(ModelMap
                                            model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        String resultCode = "Y";
        try {
            doorService.addSchedule(commandMap);
        } catch (Exception e) {
            e.getStackTrace();
            resultCode = "N";
        }

        modelAndView.addObject("resultCode", resultCode);

        return modelAndView;
    }


    /**
     * 스케줄 목록 상세
     *
     * @param model
     * @param commandMap
     * @param redirectAttributes
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/schedule/detail.do")
    public String showScheduleDetail(ModelMap
                                             model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        return "cubox/door/scheduleDetail";
    }


    /**
     * 요일별 스케쥴 등록
     *
     * @param model
     * @param commandMap
     * @param redirectAttributes
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/schedule/day/add.do", method = RequestMethod.POST)
    public ModelAndView addScheduleByDay(ModelMap
                                                 model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");


        String resultCode = "Y";
        try {
            doorService.addScheduleByDay(commandMap);
        } catch (Exception e) {
            e.getStackTrace();
            resultCode = "N";
        }

        modelAndView.addObject("resultCode", resultCode);

        return modelAndView;
    }


    /**
     * 스케줄 삭제
     *
     * @param model
     * @param commandMap
     * @param redirectAttributes
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/schedule/delete.do")
    public ModelAndView deleteSchedule(ModelMap
                                               model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        String resultCode = "N";

        resultCode = "Y";
        model.addAttribute("resultCode", resultCode);
        return modelAndView;
    }

    // 출입문 알람 그룹
    @RequestMapping(value = "/alarmGroup/listView.do")
    public String showAlarmGroupList(ModelMap model, @RequestParam Map<String, Object> commandMap) throws
            Exception {
        //todo 세션처리

        return "cubox/door/alarmGroupList";
    }

    // 출입문 알람 그룹 상세
    @RequestMapping(value = "/alarmGroup/detail.do")
    public String showAlarmDetail(ModelMap
                                          model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        return "cubox/door/alarmGroupDetail";
    }

    // 출입문 알람 그룹 등록 화면
    @RequestMapping(value = "/alarmGroup/addView.do", method = RequestMethod.GET)
    public String showAlarmGroupAddView(ModelMap
                                                model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        return "cubox/door/alarmAdd";
    }


    // 출입문 알람 그룹 등록
    @RequestMapping(value = "/alarmGroup/add.do", method = RequestMethod.POST)
    public ModelAndView addAalarmGroup(ModelMap
                                               model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        String resultMsg = "success";
        try {

            doorService.addDoorGroup(commandMap);
        } catch (Exception e) {
            e.getStackTrace();
            resultMsg = "fail";
        }

        modelAndView.addObject("result", resultMsg);

        return modelAndView;
    }


    /**
     * 단말기 목록 조회(검색)
     *
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/terminal/list.do", method = RequestMethod.GET)
    public ModelAndView searchTerminal(ModelMap model, @RequestParam Map<String, Object> commandMap) throws
            Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try {

            String keyword = StringUtil.nvl(commandMap.get("keyword"), "");
            String registrationionStatus = StringUtil.nvl(commandMap.get("registrationionStatus"), "");

            HashMap param = new HashMap<>();

            if (keyword.length() > 0) {
                param.put("keyword", keyword);
            }

            if (registrationionStatus.length() > 0) {
                param.put("registrationionStatus", registrationionStatus);

            }

            List<HashMap> terminalList = doorService.getTerminalList(param);
            model.addAttribute("terminalList", terminalList);

        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return modelAndView;
    }


    /**
     * 권한 그룹 목록 조회
     *
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/authGroup/list.do", method = RequestMethod.GET)
    public ModelAndView authGroupList(ModelMap model, @RequestParam Map<String, Object> commandMap) throws
            Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try {
            TerminalVO vo = new TerminalVO();

            String srchPage = StringUtil.nvl(commandMap.get("srchPage"), "1");
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

            List<CommonVO> authGroupTypCombList = commonService.getCommonCodeList("");

            List<TerminalVO> authGroupList = null;

            model.addAttribute("authGroupList", authGroupList);
            model.addAttribute("data", vo);

        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return modelAndView;

    }


    // 일괄 등록 양식 다운로드(출입문)
    @RequestMapping(value = "/batch/registrationForm.do")
    public ModelAndView downloadRegistrationForm(ModelMap model, @RequestParam Map<String, Object> commandMap) throws
            Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        return modelAndView;
    }

    // 일괄 등록 (출입문 Excel)
    @RequestMapping(value = "/batch/insert.do")
    public ModelAndView insertBatch(ModelMap model, @RequestParam Map<String, Object> commandMap) throws
            Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        return modelAndView;
    }

}
