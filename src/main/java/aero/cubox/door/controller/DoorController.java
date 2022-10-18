package aero.cubox.door.controller;

import aero.cubox.auth.service.AuthService;
import aero.cubox.cmmn.service.CommonService;
import aero.cubox.core.vo.AuthVO;
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
import org.springframework.web.bind.annotation.ResponseBody;
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

    @Resource(name = "commonUtils")
    private CommonUtils commonUtils;

    @Resource(name = "commonService")
    private CommonService commonService;

    @Resource(name = "doorService")
    private DoorService doorService;


    @Resource(name = "terminalService")
    private TerminalService terminalService;

    @Resource(name = "authService")
    private AuthService authService;

    private int srchPage   =  1; //조회할 페이지 번호 기본 1페이지
    private int srchCnt	   = 10; //조회할 페이지 수
    private int offset	   =  0;
    private int curPage	   =  1; //조회할 페이지 번호 기본 1페이지
    private int curPageUnit= 10; //한번에 표시할 페이지 번호 개수

    public int autoOffset(int srchPage, int srchCnt ){
        int off = (srchPage - 1) * srchCnt;
        if(off<0) off = 0;
        return off;
    }

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

        String keyword = StringUtil.nvl(commandMap.get("keyword"), "");
        HashMap parmaMap = new HashMap();

        if( keyword.length() > 0){
            parmaMap.put("keyword",keyword);
        }

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
    @RequestMapping(value = "/add.do")
    public ModelAndView addDoor( @RequestParam Map<String, Object> commandMap) throws Exception {
        LOGGER.debug("출입문 등록");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        String doorNm = StringUtil.nvl(commandMap.get("doorNm"), "");;
        String buildingId = StringUtil.nvl(commandMap.get("buildingId"), "");
        String areaId = StringUtil.nvl(commandMap.get("areaId"), "");
        String floorId = StringUtil.nvl(commandMap.get("floorId"), "");
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
        }
        try {
            doorService.updateDoor(param);

        } catch (Exception e) {
            e.getStackTrace();
            modelAndView.addObject("resultCode", "N");
            modelAndView.addObject("resultMsg", e.getStackTrace());
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
     * 출입문 그룹 목록 조회
     *
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/group/listView.do", method = RequestMethod.GET)
    public String showDoorGroupListView(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        //todo 세션
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try {

            String srchPage       = StringUtil.nvl(commandMap.get("srchPage"), "1");
            String srchRecPerPage = StringUtil.nvl(commandMap.get("srchRecPerPage"), "10");
            String keyword = StringUtil.nvl(commandMap.get("keyword"), "");

            List<HashMap> doorGroupList = doorService.getDoorGroupList(commandMap);
            int totalCnt = doorService.getDoorGroupListCount(commandMap);

            PaginationVO pageVO = new PaginationVO();
            pageVO.setCurPage(Integer.parseInt(srchPage));
            pageVO.setRecPerPage(Integer.parseInt(srchRecPerPage));
            pageVO.setTotRecord(totalCnt);
            pageVO.setUnitPage(curPageUnit);
            pageVO.calcPageList();

            model.addAttribute("doorGroupList", doorGroupList);
            model.addAttribute("keyword", keyword);
            model.addAttribute("pagination", pageVO);

        } catch (Exception e){
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

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
    public ModelAndView getDoorGroupList(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        String keyword = StringUtil.nvl(commandMap.get("keyword"), "");
        int srchPage = 1;
        if( StringUtil.isNullToString(commandMap.get("srchPage")) !="" ) {
            srchPage = String.valueOf(commandMap.get("srchPage")).matches("(^[0-9]*$)") ? Integer.valueOf((String) commandMap.get("srchPage")) : 1;
        }

        HashMap paramMap = new HashMap();

        paramMap.put( "srchPage", srchPage );
        paramMap.put(  "srchCnt", srchCnt  );
        paramMap.put(   "offset", autoOffset( srchPage, srchCnt ) );

        if( keyword.length() > 0){
            paramMap.put("keyword",keyword);
        }

        List<HashMap> doorGroupList = doorService.getDoorGroupList(commandMap);

        int totalCnt = 0;
        totalCnt = doorGroupList.size();

        PaginationVO pageVO = new PaginationVO();
        pageVO.setCurPage( srchPage );	//현재 페이지번호
        pageVO.setRecPerPage( srchCnt );	//한페이지당 레코드 개수
        pageVO.setTotRecord( totalCnt );	//총페이지수
        pageVO.setUnitPage( PaginationVO.unitPage ); //한번에 보여줄 페이지 개수
        pageVO.calcPageList();

        model.addAttribute("pagination", pageVO);//페이징 설정값 수정
        modelAndView.addObject("doorGroupList", doorGroupList);

        return modelAndView;
    }


    // 출입문 그룹 관리 상세
    @RequestMapping(value = "/group/detail.do", method = RequestMethod.GET)
    public String showGroupDetail(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

//        HashMap doorGroupDetail = doorService.getDoorGroupDetail(commandMap);

        return "cubox/door/groupDetail";
    }

    // 출입문 그룹 관리 등록화면
    @RequestMapping(value = "/group/addView.do", method = RequestMethod.GET)
    public String showGroupAddView(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {


        return "cubox/door/groupAdd";
    }

    // 출입문 그룹 관리 등록/수정
    @ResponseBody
    @RequestMapping(value = "/group/add.do", method = RequestMethod.POST)
    public ModelAndView addGroup(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        String resultCode = "Y";

        String nm = commandMap.get("nm").toString();
        String scheduleId = commandMap.get("scheduleId").toString();
        String doorIds = commandMap.get("doorIds").toString();

        HashMap param = new HashMap();

        param.put("nm", nm);
        param.put("doorSchId", scheduleId);
        param.put("doorIds", doorIds);

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
    public String showScheduleList(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {
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
    @RequestMapping(value = "/schedule/list.do", method = RequestMethod.GET)
    public ModelAndView getScheduleList(ModelMap model, @RequestParam Map<String, Object> commandMap) throws
            Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        String keyword = StringUtil.nvl(commandMap.get("keyword"), "");
        HashMap parmaMap = new HashMap();

        if( keyword.length() > 0){
            parmaMap.put("keyword",keyword);
        }

        List<HashMap> scheduleList = doorService.getScheduleList(parmaMap);
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
    public String showScheduleAddView(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        return "cubox/door/scheduleAdd";
    }

    @ResponseBody
    @RequestMapping(value = "/schedule/add.do", method = RequestMethod.POST)
    public ModelAndView addSchedule(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

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
    @RequestMapping(value = "/schedule/detail.do", method = RequestMethod.GET)
    public String showScheduleDetail(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

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
    @ResponseBody
    @RequestMapping(value = "/schedule/day/add.do", method = RequestMethod.POST)
    public ModelAndView addScheduleByDay(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

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
    @ResponseBody
    @RequestMapping(value = "/schedule/delete.do", method = RequestMethod.POST)
    public ModelAndView deleteSchedule(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        String resultCode = "N";

        resultCode = "Y";
        model.addAttribute("resultCode", resultCode);
        return modelAndView;
    }

    /**
     * 출입문 알람 그룹 화면
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/alarmGroup/listView.do", method = RequestMethod.GET)
    public String showAlarmGroupList(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {
        //todo 세션처리

        return "cubox/door/alarmGroupList";
    }

    /**
     * 출입문 알람 그룹 목록 조회
     *
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/alarmGroup/list.do", method = RequestMethod.GET)
    public ModelAndView getAlarmGroupList(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        String keyword = StringUtil.nvl(commandMap.get("keyword"), "");
        HashMap parmaMap = new HashMap();

        if( keyword.length() > 0){
            parmaMap.put("keyword",keyword);
        }

        List<HashMap> alarmGroupList = doorService.getDoorAlarmGrpList(parmaMap);
        modelAndView.addObject("alarmGroupList", alarmGroupList);

        return modelAndView;
    }

    // 출입문 알람 그룹 상세
    @RequestMapping(value = "/alarmGroup/detail.do", method = RequestMethod.GET)
    public String showAlarmDetail(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        return "cubox/door/alarmGroupDetail";
    }

    // 출입문 알람 그룹 등록 화면
    @RequestMapping(value = "/alarmGroup/addView.do", method = RequestMethod.GET)
    public String showAlarmGroupAddView(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        return "cubox/door/alarmAdd";
    }



    // 출입문 알람 그룹 등록
    @ResponseBody
    @RequestMapping(value = "/alarmGroup/add.do", method = RequestMethod.POST)
    public ModelAndView addAalarmGroup(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

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

        vo.setKeyword(keyword);
        List<AuthVO> authList = authService.getAuthList(vo);

        modelAndView.addObject("authList", authList);

        return modelAndView;
    }



}
