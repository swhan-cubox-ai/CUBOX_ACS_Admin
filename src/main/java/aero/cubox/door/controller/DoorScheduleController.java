package aero.cubox.door.controller;

import aero.cubox.cmmn.service.CommonService;
import aero.cubox.door.service.DoorScheduleService;
import aero.cubox.door.service.DoorService;
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
@RequestMapping(value = "/door/schedule/")
public class DoorScheduleController {

    @Resource(name = "commonUtils")
    private CommonUtils commonUtils;

    @Resource(name = "commonService")
    private CommonService commonService;

    @Resource(name = "doorService")
    private DoorService doorService;

    @Resource(name = "doorScheduleService")
    private DoorScheduleService doorScheduleService;

    private static final Logger LOGGER = LoggerFactory.getLogger(DoorScheduleController.class);


    /**
     * 스케줄 관리 - view
     *
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listView.do", method = RequestMethod.GET)
    public String showScheduleList(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {
        //todo 세션처리

        return "cubox/door/schedule/list";
    }


    /**
     * 스케줄 목록 조회
     *
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list.do", method = RequestMethod.GET)
    public ModelAndView getScheduleList(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        String keyword = StringUtil.nvl(commandMap.get("keyword"), "");
        HashMap parmaMap = new HashMap();

        if( keyword.length() > 0){
            parmaMap.put("keyword",keyword);
        }

        List<HashMap> scheduleList = doorScheduleService.getScheduleList(parmaMap);
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
    @RequestMapping(value = "/addView.do", method = RequestMethod.GET)
    public String showScheduleAddView(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        return "cubox/door/schedule/add";
    }

    @ResponseBody
    @RequestMapping(value = "/add.do", method = RequestMethod.POST)
    public ModelAndView addSchedule(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        String resultCode = "Y";
        try {
            doorScheduleService.addSchedule(commandMap);
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
    @RequestMapping(value = "/detail.do", method = RequestMethod.GET)
    public String showScheduleDetail(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        return "cubox/door/schedule/detail";
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
    @RequestMapping(value = "/day/add.do", method = RequestMethod.POST)
    public ModelAndView addScheduleByDay(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        String resultCode = "Y";
        try {
            doorScheduleService.addScheduleByDay(commandMap);
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
    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    public ModelAndView deleteSchedule(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        String resultCode = "N";

        resultCode = "Y";
        model.addAttribute("resultCode", resultCode);
        return modelAndView;
    }
}
