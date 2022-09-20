package aero.cubox.door.controller;

import aero.cubox.cmmn.controller.CommonController;

import aero.cubox.door.service.DoorService;
import aero.cubox.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonController.class);

    /**
     * 출입문관리 - view
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/management.do")
    public String managent(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {
        //todo 세션처리

        return "cubox/door/management";
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

        return "cubox/door/schedule";
    }

    // 출입문 목록 가져오기
    @RequestMapping(value="/getDoorList.do")
    public ModelAndView getDoorList(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        List<Map> doorList = doorService.getDoorList(commandMap);

        modelAndView.addObject("doorList", doorList);

        return modelAndView;
    }

    // 출입문 상세정보 가져오기
    @RequestMapping(value="/getDoorInformation.do")
    public ModelAndView getDoorInformation(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsonView");

        HashMap doorInfo = (HashMap) doorService.getDoorInformation(commandMap);

		modelAndView.addObject("doorInfo", doorInfo);

		return modelAndView;
    }


    // 출입문 정보 수정 / 추가
    @RequestMapping(value="/updateDoorInformation.do")
    public ModelAndView updatedoorInformation(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try {
            doorService.updateDoorInformation(commandMap);

        } catch(Exception e) {
            e.getStackTrace();
            modelAndView.addObject("result", "N");
        }
        modelAndView.addObject("result", "Y");


        return modelAndView;
    }



    // 출입문 삭제
    @RequestMapping(value="/deleteDoor.do")
    public ModelAndView deleteDoor(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try {
            doorService.deleteDoor(commandMap);
        } catch(Exception e) {
            e.getStackTrace();
            modelAndView.addObject("result", "N");
        }
        modelAndView.addObject("result", "Y");

        return modelAndView;
    }

    // 단말기 정보 가져오기
    @RequestMapping(value="/getTerminalInformation.do")
    public ModelAndView getTerminalInformation(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        HashMap terminalInfo= doorService.getTerminalInformation(commandMap);
        modelAndView.addObject("terminalInfo", terminalInfo);

        return modelAndView;
    }

    // 단말기 검색 (단말기명 / 관리번호 / 단말기코드로)
    @RequestMapping(value="/searchTerminalInformation.do")
    public ModelAndView searchTerminalInformation(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        List<HashMap> terminalList = doorService.searchTerminalInformation(commandMap);

        modelAndView.addObject("terminalList", terminalList);

        return modelAndView;
    }



    // 권한 그룹 가져오기
    @RequestMapping(value="/getPermissionGroups.do")
    public ModelAndView getPermissionGroups(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        List<HashMap> permissionGroupList = doorService.getPermissionGroups(commandMap);

        modelAndView.addObject("permissionGroupList", permissionGroupList);

        return modelAndView;
    }



    // 권한그룹 검색 (권한그룹명)
    @RequestMapping(value="/searchPermissionGroup.do")
    public ModelAndView searchPermissionGroup(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        List<HashMap> permissionGroupList = doorService.searchPermissionGroup(commandMap);

        modelAndView.addObject("permissionGroupList", permissionGroupList);

        return modelAndView;
    }

    // 일괄 등록 양식 다운로드(출입문)
    @RequestMapping(value="/downloadRegistrationForm.do")
    public ModelAndView downloadRegistrationForm(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        return modelAndView;
    }

    // 일괄 등록 (출입문 Excel)
    @RequestMapping(value="/insertBatch.do")
    public ModelAndView insertBatch(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        return modelAndView;
    }




    //todo 리스트
    //todo
    @RequestMapping(value = "/schedule_add.do")
    public String schedule_add(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        if (commandMap.get("editMode").equals("edit")) {
            model.addAttribute("schName", commandMap.get("schName"));
            model.addAttribute("schUseYn", commandMap.get("schUseYn"));
            model.addAttribute("gateGroup", commandMap.get("gateGroup"));
        }
        model.addAttribute("editMode", commandMap.get("editMode"));
        System.out.println(commandMap.get("editMode"));
        System.out.println(model.get("editMode"));
        System.out.println(model.get("schName"));
        return "cubox/door/schedule_add";
    }
    //todo
    @RequestMapping(value="/detail.do")
    public String schedule_detail(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        String[] days = {"월", "화", "수", "목", "금", "토", "일"};
        String[] days_eng = {"mon", "tue", "wed", "thu", "fri", "sat", "sun"};

        model.addAttribute("days", days);
        model.addAttribute("days_eng", days_eng);
        return "cubox/door/detail";
    }
    //todo
    @RequestMapping(value="/schedule_delete.do")
    public String schedule_delete(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        return "cubox/door/schedule";
    }

}
