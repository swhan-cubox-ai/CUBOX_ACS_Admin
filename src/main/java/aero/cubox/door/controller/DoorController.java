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
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

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
//        List<Map> workplaceList = doorService.getWorkplaceList(paramMap); //사업장 목록
//        List<Map> buildingList = doorService.getBuildingList(paramMap);   //빌딩 목록
//       // List<Map> areaList = doorService.getAreaList(paramMap);           //지역 목록
//        List<Map> floorList = doorService.getFloorList(paramMap);     //층 목록

        // List<HashMap> scheduleList = doorScheduleService.getDoorScheduleList(paramMap);      // 스케쥴 목록
        List<HashMap> doorGroupList = doorGroupService.getDoorGroupList(paramMap);      // 스케쥴 목록
        List<HashMap> doorAlarmGrpList = doorAlarmService.getDoorAlarmGrpList(paramMap); // 출입물 알람 그룹 목록

//        model.addAttribute("workplaceList", workplaceList);
//        model.addAttribute("buildingList", buildingList);
//       // model.addAttribute("areaList", areaList);
//        model.addAttribute("floorList", floorList);
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
        //List<Map>      areaList = doorService.getAreaList(parmaMap);      //지역 목록
        List<Map> floorList = doorService.getFloorList(parmaMap);     //층 목록

        List<Map> doorList = doorService.getDoorList(parmaMap);           //출입문 목록

//        model.addAttribute("workplaceList", workplaceList);
//        model.addAttribute("buildingList", buildingList);
//        //model.addAttribute("areaList", areaList);
//        model.addAttribute("floorList", floorList);
//        model.addAttribute("doorList", doorList);

        modelAndView.addObject("workplaceList", workplaceList);
        modelAndView.addObject("buildingList", buildingList);
        modelAndView.addObject("floorList", floorList);
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
        //String areaId = StringUtil.nvl(commandMap.get("areaId"), "");
        String floorId = StringUtil.nvl(commandMap.get("floorId"), "");
        String doorGroupId = StringUtil.nvl(commandMap.get("doorGroupId"), "");
        String scheduleId = StringUtil.nvl(commandMap.get("scheduleId"), "");
        String alarmGroupId = StringUtil.nvl(commandMap.get("alarmGroupId"), "");
        String terminalIds = StringUtil.nvl(commandMap.get("terminalIds"), "");
        String authGrIds = StringUtil.isNullToString(commandMap.get("authGrIds"));

        HashMap param = new HashMap();

        param.put("doorNm", doorNm);        //출입문 명
        param.put("buildingId", buildingId);//빌딩 ID
        //param.put("areaId", areaId);        //지역 ID
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
            //String areaId = StringUtil.nvl(commandMap.get("areaId"), "");
            String floorId = StringUtil.nvl(commandMap.get("floorId"), "");
            String doorGroupId = StringUtil.nvl(commandMap.get("doorGroupId"), "");
            String scheduleId = StringUtil.nvl(commandMap.get("scheduleId"), "");
            String alarmGroupId = StringUtil.nvl(commandMap.get("alarmGroupId"), "");
            String terminalIds = StringUtil.nvl(commandMap.get("terminalIds"), "");
            String authGrIds = StringUtil.nvl(commandMap.get("authGrIds"), "");

            param.put("id", doorId);
            param.put("doorNm", doorNm);
            param.put("buildingId", buildingId);
            //param.put("areaId", areaId);
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

        String  buildingCd = StringUtil.nvl(commandMap.get("buildingCd"), "");;
        String  buildingNm = StringUtil.nvl(commandMap.get("buildingNm"), "");;
        String workplaceId = StringUtil.nvl(commandMap.get("workplaceId"), "");

        HashMap param = new HashMap();

        param.put( "buildingCd", buildingCd );        //빌딩 코드
        param.put( "buildingNm", buildingNm );        //빌딩 명
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
        String buildingCd = StringUtil.nvl(commandMap.get("buildingCd"), "");
        String floorCd = StringUtil.nvl(commandMap.get("floorCd"), "");
        String buildingId = StringUtil.nvl(commandMap.get("buildingId"), "");
        //String areaId = StringUtil.nvl(commandMap.get("areaId"), "");

        HashMap param = new HashMap();

        param.put("floorNm", floorNm);        //출입문 명
        param.put("buildingId", buildingId);  //빌딩 ID
        param.put("buildingCd", buildingCd);  //빌딩 코드
        param.put("floorCd", floorCd);        //층 코드
        //param.put("areaId", areaId);        //지역 ID

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
            //String areaId = StringUtil.nvl(commandMap.get("areaId"), "");

            param.put("floorId", floorId);
            param.put("floorNm", floorNm);
            param.put("buildingId", buildingId);
            //param.put("areaId", areaId);
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


    @ResponseBody
    @RequestMapping(value = "/excel/upload.do", method = RequestMethod.POST)
    public ModelAndView excelUpload(MultipartHttpServletRequest request) throws Exception {

        System.out.println("excel file upload controller");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        MultipartFile file = null;

        Iterator<String> iterator = request.getFileNames();
        if (iterator.hasNext()) {
            file = request.getFile(iterator.next());
        }
        LOGGER.debug("File === ", file);

        try {
            Workbook wb = WorkbookFactory.create(file.getInputStream());
            Sheet sheet = wb.getSheetAt(0);

            int cnt = 0;
            String newBuildingId = "";
            String newFloorId = "";
            String newDoorId = "";
            HashMap paramMap;
            HashMap buildingMap = new HashMap();
            HashMap floorMap = new HashMap();

            // 전체 삭제
            doorService.deleteDoorAll();
            doorService.deleteFloorAll();
            doorService.deleteAreaAll();
            doorService.deleteBuildingAll();

            // 1. Building
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                // 행이 없으면 패스
                if (row == null) {
                    continue;
                }

                if (row.getCell(0) != null && !row.getCell(1).equals("")) {
                    String buildingNm = getValue(row.getCell(1)).replaceAll("\n", "<br>");                     // 빌딩 명
                    String buildingCd = String.format("%02d", Integer.parseInt(getValue(row.getCell(5)).replaceAll("\n", "<br>")));      // 빌딩 코드

                    if (!buildingMap.containsValue(buildingCd)) { // buildingCd가 없는 경우
                        buildingMap.put(buildingNm, buildingCd);

                        HashMap param = new HashMap();
                        param.put("buildingNm", buildingNm);
                        param.put("buildingCd", buildingCd);
                        param.put("workplaceId", 1);

                        try {
                            newBuildingId = doorService.addBuilding(param);
                            LOGGER.debug("newBuildingId === " + newBuildingId);
                        } catch (Exception e) {
                            e.getStackTrace();
                        }
                    }
                }
            }

            // Building List
            paramMap = new HashMap();
            List<Map> buildingList = doorService.getBuildingList(paramMap);   //빌딩 목록

            // 2. Floor
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                // 행이 없으면 패스
                if (row == null) {
                    continue;
                }

                if (row.getCell(0) != null && !row.getCell(2).equals("")) {
                    String buildingId = "";
                    String buildingNm = getValue(row.getCell(1)).replaceAll("\n", "<br>");   // 빌딩 명
                    String buildingCd = String.format("%02d", Integer.parseInt(getValue(row.getCell(5)).replaceAll("\n", "<br>")));   // 빌딩 코드
                    String floorNm = getValue(row.getCell(2)).replaceAll("\n", "<br>");      // 층 명
                    String floorCd = getValue(row.getCell(6)).replaceAll("\n", "<br>");      // 층 코드

                    for (int j = 0; j < buildingList.size(); j++) {
                        if (buildingList.get(j).get("building_nm").equals(buildingNm) && buildingList.get(j).get("building_cd").equals(buildingCd)) {
                            buildingId = buildingList.get(j).get("id").toString();
                            break;
                        }
                    }
                    if (floorCd.length() == 1) {
                        floorCd = "0" + floorCd;
                    }

                    if (!floorMap.containsValue(buildingCd + "_" + floorCd)) {
                        floorMap.put(buildingNm + "_" + floorNm, buildingCd + "_" + floorCd);

                        HashMap param = new HashMap();
//                        param.put("floorNm", buildingNm + " " + floorNm);
                        param.put("floorNm", floorNm);
                        param.put("floorCd", floorCd);
                        param.put("buildingId", buildingId);
                        param.put("buildingCd", buildingCd);
                        try {
                            newFloorId = doorService.addFloor(param);
                            LOGGER.debug("newFloorId === " + newFloorId);
                        } catch (Exception e) {
                            e.getStackTrace();
                        }
                    }
                }
            }
//
            // Floor List
            paramMap = new HashMap();
            List<HashMap> floorList = doorService.getFloorList(paramMap);
            LOGGER.debug("floorList == " + floorList);

            // 3. Door
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                // 행이 없으면 패스
                if (row == null) {
                    continue;
                }

                if (row.getCell(0) != null && !row.getCell(3).equals("")) {
                    String buildingId = "";
                    String floorId = "";
                    String buildingNm = getValue(row.getCell(1)).replaceAll("\n", "<br>");                                              // 빌딩 명
                    String floorNm = getValue(row.getCell(2)).replaceAll("\n", "<br>");                                                 // 층 명
                    String doorNm = getValue(row.getCell(3)).replaceAll("\n", "<br>");                                                  // 출입문 명
                    String terminalCd = getValue(row.getCell(4)).replaceAll("\n", "<br>");                                              // 단말기 코드
                    String buildingCd = String.format("%02d", Integer.parseInt(getValue(row.getCell(5)).replaceAll("\n", "<br>")));     // 빌딩 코드
                    String floorCd = getValue(row.getCell(6)).replaceAll("\n", "<br>");                                                 // 층 코드 (2자리로 넣어야함)
                    String doorCd = getValue(row.getCell(7)).replaceAll("\n", "<br>");                                                  // 출입문 코드

                    // buildingId 가져오기
                    for (int j = 0; j < buildingList.size(); j++) {
                        if (buildingList.get(j).get("building_nm").equals(buildingNm) && buildingList.get(j).get("building_cd").equals(buildingCd)) {
                            buildingId = buildingList.get(j).get("id").toString();
                            break;
                        }
                    }

                    // floorCd 2자리수 변형
                    if (floorCd.length() == 1) {
                        floorCd = "0" + floorCd;
                    }

                    // doorCd 6자리수 변형
                    if (doorCd.length() < 6) {
                        String preNum = "";
                        int num = 6 - doorCd.length();
                        for (int j = 0; j < num; j++) {
                            preNum += "0";
                        }
                        doorCd = preNum + doorCd;
                    }

                    // floorId 가져오기
                    for (int j = 0; j < floorList.size(); j++) {
//                       if (floorList.get(j).get("floor_nm").equals(buildingNm + " " + floorNm) && floorList.get(j).get("floor_cd").equals(floorCd)) {
                        if (floorList.get(j).get("floor_nm").equals(floorNm) && floorList.get(j).get("floor_cd").equals(floorCd) && floorList.get(j).get("building_cd").equals(buildingCd)) {
                            floorId = floorList.get(j).get("id").toString();
                            break;
                        }
                    }
                    if (doorNm.length() > 0) {
                        HashMap param = new HashMap();
                        param.put("buildingCd", buildingCd);
                        param.put("floorCd", floorCd);
                        param.put("doorCd", doorCd);
                        param.put("buildingId", buildingId);
                        param.put("areaId", null);
                        param.put("floorId", floorId);
                        param.put("doorNm", doorNm);
//                        param.put("terminalCd", terminalCd);
//                        param.put("alarmGroupId", );
                        LOGGER.debug("door map : {}", param);
                        try {
                            newDoorId = doorService.addDoor(param);
                            LOGGER.debug("newDoorId === {}", newDoorId);
                            if (newDoorId != "") cnt++;
                        } catch (Exception e) {
                            e.getStackTrace();
                        }
                    }
                }
            }

            if (cnt > 0) {
                if (cnt == sheet.getLastRowNum()) {
                    modelAndView.addObject("resultCode", "Y");
                    modelAndView.addObject("message", "Success");
                } else {
                    modelAndView.addObject("resultCode", "N");
                    modelAndView.addObject("message", cnt + "행까지 등록됨");
                }
            } else {
                modelAndView.addObject("resultCode", "N");
                modelAndView.addObject("message", "Fail");
            }

        } catch (Exception e) {
            modelAndView.addObject("resultCode", "N");
            modelAndView.addObject("message", e);
            e.printStackTrace();
        }

        return modelAndView;
    }


    public static String getValue(Cell cell) {
        String value = "";

        if (cell == null) {
            value = "";
        } else {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_FORMULA:
                    value = cell.getCellFormula();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    value = String.valueOf((long)cell.getNumericCellValue());
                    // TODO: Date타입 format처리
                    break;
                case Cell.CELL_TYPE_STRING:
                    value = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    value = cell.getBooleanCellValue() + "";
                    break;
                case Cell.CELL_TYPE_ERROR:
                    value = cell.getErrorCellValue() + "";
                    break;
                case Cell.CELL_TYPE_BLANK:
                    value = "";
                    break;
                default:
                    value = cell.getStringCellValue();
            }
        }
        return value;
    }


    @RequestMapping(value = "/excel/download.do", method = RequestMethod.GET)
    public ModelAndView excelFormDownload(@RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {

        String filePath = request.getSession().getServletContext().getRealPath("/excel/");
        // C:\Dev\IdeaProjects\CUBOX_ACS_Admin\target\CUBOX_ACS_Admin\excel\

        File downloadFile = new File(filePath + "excelupload_door_sample.xlsx");
        String fileOrigin = "엑셀업로드_출입문_양식샘플.xlsx";

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("fileDownloadView");
        modelAndView.addObject("downloadFile", downloadFile);
        modelAndView.addObject("fileOrigin", fileOrigin);

        return modelAndView;
    }



}
