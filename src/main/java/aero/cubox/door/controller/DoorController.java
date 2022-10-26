package aero.cubox.door.controller;

import aero.cubox.auth.service.AuthService;
import aero.cubox.cmmn.service.CommonService;
import aero.cubox.core.vo.AuthVO;
import aero.cubox.door.service.DoorAlarmService;
import aero.cubox.door.service.DoorGroupService;
import aero.cubox.door.service.DoorScheduleService;
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

    @Resource(name = "commonUtils")
    private CommonUtils commonUtils;

    @Resource(name = "commonService")
    private CommonService commonService;

    @Resource(name = "doorService")
    private DoorService doorService;

    @Resource(name = "doorGroupService")
    private DoorGroupService doorGroupService;

    @Resource(name = "doorScheduleService")
    private DoorScheduleService doorScheduleService;

    @Resource(name = "doorAlarmService")
    private DoorAlarmService doorAlarmService;

    @Resource(name = "terminalService")
    private TerminalService terminalService;

    @Resource(name = "authService")
    private AuthService authService;


    private static final Logger LOGGER = LoggerFactory.getLogger(DoorController.class);

    /**
     * 출입문관리 - view
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/management.do", method = RequestMethod.GET)
    public String doorManagementDetail(ModelMap model) throws Exception {
        //todo 세션처리

        HashMap paramMap = new HashMap();
        List<Map> workplaceList = doorService.getWorkplaceList(paramMap); //사업장 목록
        List<Map> buildingList = doorService.getBuildingList(paramMap);   //빌딩 목록
        List<Map> areaList = doorService.getAreaList(paramMap);           //지역 목록
        List<HashMap> floorList = doorService.getFloorList(paramMap);     //층 목록

        // List<HashMap> scheduleList = doorScheduleService.getDoorScheduleList(paramMap);      // 스케쥴 목록
        List<HashMap> doorGroupList = doorGroupService.getDoorGroupList(paramMap);      // 스케쥴 목록
        List<HashMap> doorAlarmGrpList = doorAlarmService.getDoorAlarmGrpList(paramMap); // 출입물 알람 그룹 목록

        model.addAttribute("workplaceList", workplaceList);
        model.addAttribute("buildingList", buildingList);
        model.addAttribute("areaList", areaList);
        model.addAttribute("floorList", floorList);
        model.addAttribute("doorAlarmGrpList", doorAlarmGrpList);
        model.addAttribute("doorGroupList", doorGroupList);
        //model.addAttribute("scheduleList", scheduleList);

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

        String keyword = StringUtil.nvl(commandMap.get("keyword"), "");
        HashMap parmaMap = new HashMap();

        if( keyword.length() > 0 ){
            parmaMap.put("keyword",keyword);
        }

        List<Map> workplaceList = doorService.getWorkplaceList(parmaMap); //사업장 목록
        List<Map>  buildingList = doorService.getBuildingList(parmaMap);  //빌딩 목록
        List<Map>      areaList = doorService.getAreaList(parmaMap);      //지역 목록
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
     * 출입문 정보 상세 조회
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
     * @param commandMap
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/add.do", method = RequestMethod.POST)
    public ModelAndView addDoor( @RequestParam Map<String, Object> commandMap) throws Exception {
        LOGGER.debug("출입문 등록");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        String doorNm = StringUtil.nvl(commandMap.get("doorNm"), "");
        String buildingId = StringUtil.nvl(commandMap.get("buildingId"), "");
        String areaId = StringUtil.nvl(commandMap.get("areaId"), "");
        String floorId = StringUtil.nvl(commandMap.get("floorId"), "");
        String doorGroupId = StringUtil.nvl(commandMap.get("doorGroupId"), "");
        String scheduleId = StringUtil.nvl(commandMap.get("scheduleId"), "");
        String alarmGroupId = StringUtil.nvl(commandMap.get("alarmGroupId"), "");
        String terminalIds = StringUtil.nvl(commandMap.get("terminalIds"), "");
        String authGrIds = StringUtil.isNullToString(commandMap.get("authGrIds"));

        HashMap param = new HashMap();

        param.put("doorNm", doorNm);        //출입문 명
        param.put("buildingId", buildingId);//빌딩 ID
        param.put("areaId", areaId);        //지역 ID
        param.put("floorId", floorId);      //층 ID
        param.put("doorScheduleId", scheduleId); //출입문 스케쥴 ID
        param.put("doorGroupId", doorGroupId); //출입문 스케쥴 ID
        param.put("alarmGroupId", alarmGroupId); //알람 그룹 ID
        param.put("terminalIds", terminalIds);   //단말기 ID - 복수저장?
        param.put("authGrIds", authGrIds);       //권한그룹ID - 복수저장?

        String newDoorId = "";
        try {
            newDoorId = doorService.addDoor(param);
        } catch (Exception e) {
            e.getStackTrace();
            modelAndView.addObject("resultCode", "N");
        }
        modelAndView.addObject("newDoorId", newDoorId );
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
    @ResponseBody
    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
    public ModelAndView updateDoor(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        HashMap param = new HashMap();

        if (StringUtil.isEmpty(commandMap.get("doorId").toString() ) ){
            modelAndView.addObject("resultCode", "N");
            modelAndView.addObject("resultMsg", "no id");
        } else {
            String doorId = commandMap.get("doorId").toString();
            String doorNm = StringUtil.nvl(commandMap.get("doorNm"), "");;
            String buildingId = StringUtil.nvl(commandMap.get("buildingId"), "");
            String areaId = StringUtil.nvl(commandMap.get("areaId"), "");
            String floorId = StringUtil.nvl(commandMap.get("floorId"), "");
            String doorGroupId = StringUtil.nvl(commandMap.get("doorGroupId"), "");
            String scheduleId = StringUtil.nvl(commandMap.get("scheduleId"), "");
            String alarmGroupId = StringUtil.nvl(commandMap.get("alarmGroupId"), "");
            String terminalIds = StringUtil.nvl(commandMap.get("terminalIds"), "");
            String authGrIds = StringUtil.nvl(commandMap.get("authGrIds"), "");

            param.put("id", doorId);
            param.put("doorNm", doorNm);
            param.put("buildingId", buildingId);
            param.put("areaId", areaId);
            param.put("floorId", floorId);
            param.put("doorScheduleId", scheduleId);
            param.put("alarmGroupId", alarmGroupId);
            param.put("terminalIds", terminalIds);
            param.put("authGrIds", authGrIds);
            param.put("doorGroupId", doorGroupId);
        }
        try {
            doorService.updateDoor(param);
            modelAndView.addObject("resultCode", "Y");
        } catch (Exception e) {
            e.getStackTrace();
            modelAndView.addObject("resultCode", "N");
            modelAndView.addObject("resultMsg", e.getStackTrace());
        }


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
    @ResponseBody
    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    public ModelAndView deleteDoor(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

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
     * 단말기 목록 조회(검색)
     *
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/terminal/list.do", method = RequestMethod.GET)
    public ModelAndView searchTerminal(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

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


    // 일괄 등록 양식 다운로드(출입문)
    @RequestMapping(value = "/batch/registrationForm.do")
    public ModelAndView downloadRegistrationForm(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        return modelAndView;
    }

    // 일괄 등록 (출입문 Excel)
    @RequestMapping(value = "/batch/insert.do")
    public ModelAndView insertBatch(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        return modelAndView;
    }


    /**
     * 구역 목록 조회
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/area/list.do", method = RequestMethod.GET)
    public ModelAndView getAreaList(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        List<HashMap> areaList = doorService.getAreaList(commandMap);

        modelAndView.addObject("areaList", areaList);

        return modelAndView;
    }

    /**
     * 층 목록 조회
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/floor/list.do", method = RequestMethod.GET)
    public ModelAndView getFloorList(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        List<HashMap> floorList = doorService.getFloorList(commandMap);

        modelAndView.addObject("floorList", floorList);

        return modelAndView;
    }

    /**
     * 권한 그룹 목록 조회
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/authGroup/list.do", method = RequestMethod.GET)
    public ModelAndView getAuthList(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        AuthVO vo = new AuthVO();

        String keyword = StringUtil.nvl(commandMap.get("keyword"), "");
        String srchCond = StringUtil.nvl(commandMap.get("authType"), "");

        vo.setKeyword(keyword);
        vo.setSrchCond(srchCond);
        List<AuthVO> authList = authService.getAuthList(vo);

        modelAndView.addObject("authList", authList);

        return modelAndView;
    }


    /**
     * 빌딩 추가
     * @param commandMap
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/building/add.do", method = RequestMethod.POST)
    public ModelAndView addBuilding( @RequestParam Map<String, Object> commandMap) throws Exception {
        LOGGER.debug("빌딩 추가");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        String  buildingNm = StringUtil.nvl(commandMap.get("buildingNm"), "");;
        String workplaceId = StringUtil.nvl(commandMap.get("workplaceId"), "");

        HashMap param = new HashMap();

        param.put( "buildingNm", buildingNm );        //출입문 명
        param.put("workplaceId", workplaceId );//빌딩 ID

        String newBuildingId = "";
        try {
            newBuildingId = doorService.addBuilding(param);
        } catch (Exception e) {
            e.getStackTrace();
            modelAndView.addObject("resultCode", "N");
        }
        modelAndView.addObject("newBuildingId", newBuildingId );
        modelAndView.addObject("resultCode", "Y");

        return modelAndView;
    }

    /**
     * 구역 추가
     * @param commandMap
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/area/add.do", method = RequestMethod.POST)
    public ModelAndView addArea( @RequestParam Map<String, Object> commandMap) throws Exception {
        LOGGER.debug("구역 추가");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        String areaNm = StringUtil.nvl(commandMap.get("areaNm"), "");;
        String buildingId = StringUtil.nvl(commandMap.get("buildingId"), "");

        HashMap param = new HashMap();

        param.put("areaNm", areaNm);        //출입문 명
        param.put("buildingId", buildingId);//빌딩 ID

        String newAreaId = "";
        try {
            newAreaId = doorService.addArea(param);
        } catch (Exception e) {
            e.getStackTrace();
            modelAndView.addObject("resultCode", "N");
        }
        modelAndView.addObject("newAreaId", newAreaId );
        modelAndView.addObject("resultCode", "Y");

        return modelAndView;
    }

    /**
     * 층 추가
     * @param commandMap
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/floor/add.do", method = RequestMethod.POST)
    public ModelAndView addFloor( @RequestParam Map<String, Object> commandMap) throws Exception {
        LOGGER.debug("층 추가");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        String floorNm = StringUtil.nvl(commandMap.get("floorNm"), "");;
        String buildingId = StringUtil.nvl(commandMap.get("buildingId"), "");
        String areaId = StringUtil.nvl(commandMap.get("areaId"), "");

        HashMap param = new HashMap();

        param.put("floorNm", floorNm);        //출입문 명
        param.put("buildingId", buildingId);//빌딩 ID
        param.put("areaId", areaId);        //지역 ID

        String newfloorId = "";
        try {
            newfloorId = doorService.addFloor(param);
        } catch (Exception e) {
            e.getStackTrace();
            modelAndView.addObject("resultCode", "N");
        }
        modelAndView.addObject("newfloorId", newfloorId );
        modelAndView.addObject("resultCode", "Y");

        return modelAndView;
    }


    @RequestMapping(value = "/building/detail.do", method = RequestMethod.GET)
    public ModelAndView getBuildingInformation(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        LOGGER.debug("getBuildingInformation");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        LOGGER.debug("param buildingId=" + commandMap.get("buildingId"));
        HashMap<String, Object> param = new HashMap<String, Object>();

        param.put("id", commandMap.get("buildingId"));

        HashMap doorInfo = (HashMap) doorService.getBuildingDetail(param);

        modelAndView.addObject("doorInfo", doorInfo);

        return modelAndView;
    }


    @RequestMapping(value = "/floor/detail.do", method = RequestMethod.GET)
    public ModelAndView getFloorInformation(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        LOGGER.debug("getFloorInformation");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        LOGGER.debug("param floorId=" + commandMap.get("floorId"));
        HashMap<String, Object> param = new HashMap<String, Object>();

        param.put("id", commandMap.get("floorId"));

        HashMap doorInfo = (HashMap) doorService.getFloorDetail(param);

        modelAndView.addObject("doorInfo", doorInfo);

        return modelAndView;
    }

    @RequestMapping(value = "/area/detail.do", method = RequestMethod.GET)
    public ModelAndView getArearInformation(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        LOGGER.debug("getArearInformation");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        LOGGER.debug("param areaId=" + commandMap.get("areaId"));
        HashMap<String, Object> param = new HashMap<String, Object>();

        param.put("id", commandMap.get("areaId"));

        HashMap doorInfo = (HashMap) doorService.getAreaDetail(param);

        modelAndView.addObject("doorInfo", doorInfo);

        return modelAndView;
    }

    /**
     * 빌딩 수정
     *
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/building/update.do", method = RequestMethod.POST)
    public ModelAndView updateBuilding(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        HashMap param = new HashMap();

        if (StringUtil.isEmpty(commandMap.get("buildingId").toString() ) ){
            modelAndView.addObject("resultCode", "N");
            modelAndView.addObject("resultMsg", "no id");
        } else {
            String buildingId = StringUtil.nvl(commandMap.get("buildingId"), "");
            String buildingNm = StringUtil.nvl(commandMap.get("buildingNm"), "");;
            String workplaceId = StringUtil.nvl(commandMap.get("workplaceId"), "");
            String authGrIds = StringUtil.nvl(commandMap.get("authGrIds"), "");


            param.put("buildingId", buildingId);
            param.put("buildingNm", buildingNm);
            param.put("workplaceId", workplaceId);;
            param.put("authGrIds", authGrIds);
        }
        try {
            doorService.updateBuilding(param);

        } catch (Exception e) {
            e.getStackTrace();
            modelAndView.addObject("resultCode", "N");
            modelAndView.addObject("resultMsg", e.getStackTrace());
        }
        modelAndView.addObject("resultCode", "Y");

        return modelAndView;
    }


    /**
     * 빌딩 삭제
     *
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/building/delete.do", method = RequestMethod.POST)
    public ModelAndView deleteBuilding(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try {
            doorService.deleteBuilding(commandMap);
        } catch (Exception e) {
            e.getStackTrace();
            modelAndView.addObject("resultCode", "N");
        }
        modelAndView.addObject("resultCode", "Y");

        return modelAndView;
    }

    /**
     * 구역 수정
     *
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/area/update.do", method = RequestMethod.POST)
    public ModelAndView updateArea(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        HashMap param = new HashMap();

        if (StringUtil.isEmpty(commandMap.get("areaId").toString() ) ){
            modelAndView.addObject("resultCode", "N");
            modelAndView.addObject("resultMsg", "no id");
        } else {
            String areaId = StringUtil.nvl(commandMap.get("areaId"), "");
            String areaNm = StringUtil.nvl(commandMap.get("areaNm"), "");;
            String buildingId = StringUtil.nvl(commandMap.get("buildingId"), "");;


            param.put("areaId", areaId);
            param.put("areaNm", areaNm);
            param.put("buildingId", buildingId);
        }
        try {
            doorService.updateArea(param);

        } catch (Exception e) {
            e.getStackTrace();
            modelAndView.addObject("resultCode", "N");
            modelAndView.addObject("resultMsg", e.getStackTrace());
        }
        modelAndView.addObject("resultCode", "Y");

        return modelAndView;
    }


    /**
     * 빌딩 삭제
     *
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/area/delete.do", method = RequestMethod.POST)
    public ModelAndView deleteArea(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try {
            doorService.deleteArea(commandMap);
        } catch (Exception e) {
            e.getStackTrace();
            modelAndView.addObject("resultCode", "N");
        }
        modelAndView.addObject("resultCode", "Y");

        return modelAndView;
    }

    /**
     *  수정
     *
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/floor/update.do", method = RequestMethod.POST)
    public ModelAndView updateFloor(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        HashMap param = new HashMap();

        if (StringUtil.isEmpty(commandMap.get("floorId").toString() ) ){
            modelAndView.addObject("resultCode", "N");
            modelAndView.addObject("resultMsg", "no id");
        } else {
            String floorId = StringUtil.nvl(commandMap.get("floorId"), "");
            String floorNm = StringUtil.nvl(commandMap.get("floorNm"), "");
            String buildingId = StringUtil.nvl(commandMap.get("buildingId"), "");
            String areaId = StringUtil.nvl(commandMap.get("areaId"), "");

            param.put("floorId", floorId);
            param.put("floorNm", floorNm);
            param.put("buildingId", buildingId);
            param.put("areaId", areaId);
        }

        try {
            doorService.updateFloor(param);

        } catch (Exception e) {
            e.getStackTrace();
            modelAndView.addObject("resultCode", "N");
            modelAndView.addObject("resultMsg", e.getStackTrace());
        }
        modelAndView.addObject("resultCode", "Y");

        return modelAndView;
    }


    /**
     * floor 삭제
     *
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/floor/delete.do", method = RequestMethod.POST)
    public ModelAndView deleteFloor(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try {
            doorService.deleteFloor(commandMap);
        } catch (Exception e) {
            e.getStackTrace();
            modelAndView.addObject("resultCode", "N");
        }
        modelAndView.addObject("resultCode", "Y");

        return modelAndView;
    }


    @RequestMapping(value = "/terminal/confirmUse.do", method = RequestMethod.GET)
    public ModelAndView getTerminalConfirmUse(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");
        HashMap<String, Object> param = new HashMap<String, Object>();

        param.put("id", commandMap.get("terminalId"));

        int terminalUseCnt = doorService.getTerminalUseCnt(param);

        modelAndView.addObject("terminalUseCnt", terminalUseCnt);

        return modelAndView;
    }

    @RequestMapping(value = "/building/name/verification.do", method = RequestMethod.GET)
    public ModelAndView getBuildingNameValidation(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");
        HashMap<String, Object> param = new HashMap<String, Object>();

        param.put("buildingNm", commandMap.get("buildingNm"));

        int buildingNameVerificationCnt = doorService.getBuildingNameVerification(param);

        modelAndView.addObject("buildingNameVerificationCnt", buildingNameVerificationCnt);

        return modelAndView;
    }


    @RequestMapping(value = "/area/name/verification.do", method = RequestMethod.GET)
    public ModelAndView getAreaNameValidation(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");
        HashMap<String, Object> param = new HashMap<String, Object>();

        param.put("areaNm", commandMap.get("areaNm"));

        int areaNameVerificationCnt = doorService.getAreaNameVerification(param);

        modelAndView.addObject("areaNameVerificationCnt", areaNameVerificationCnt);

        return modelAndView;
    }


    @RequestMapping(value = "/floor/name/verification.do", method = RequestMethod.GET)
    public ModelAndView getFloorNameValidation(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");
        HashMap<String, Object> param = new HashMap<String, Object>();

        param.put("floorNm", commandMap.get("floorNm"));

        int floorNameVerificationCnt = doorService.getFloorNameVerification(param);

        modelAndView.addObject("floorNameVerificationCnt", floorNameVerificationCnt);

        return modelAndView;
    }


    @RequestMapping(value = "/name/verification.do", method = RequestMethod.GET)
    public ModelAndView getDoorNameValidation(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");
        HashMap<String, Object> param = new HashMap<String, Object>();

        param.put("doorNm", commandMap.get("doorNm"));

        int doorNameVerificationCnt = doorService.getDoorNameVerification(param);

        modelAndView.addObject("doorNameVerificationCnt", doorNameVerificationCnt);

        return modelAndView;
    }



}
