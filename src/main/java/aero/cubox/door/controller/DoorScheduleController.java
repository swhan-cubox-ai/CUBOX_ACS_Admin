package aero.cubox.door.controller;

import aero.cubox.cmmn.service.CommonService;
import aero.cubox.core.vo.PaginationVO;
import aero.cubox.door.service.DoorGroupService;
import aero.cubox.door.service.DoorScheduleService;
import aero.cubox.door.service.DoorService;
import aero.cubox.util.CommonUtils;
import aero.cubox.util.StringUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static aero.cubox.util.JsonUtil.getListMapFromJsonArray;

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

    @Resource(name = "doorGroupService")
    private DoorGroupService doorGroupService;


    private static final Logger LOGGER = LoggerFactory.getLogger(DoorScheduleController.class);

    private int curPageUnit= 10; //한번에 표시할 페이지 번호 개수
    private String initSrchRecPerPage = "10"; //한번에 표시할 페이지 번호 개수

    public int autoOffset(int srchPage, int srchCnt ){
        int off = (srchPage - 1) * srchCnt;
        if(off<0) off = 0;
        return off;
    }

    /**
     * 스케줄 관리 - view
     *
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list.do")
    public String showScheduleList(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        //todo 세션
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

            List<HashMap> doorScheduleList = doorScheduleService.getDoorScheduleList(paramMap);
            int totalCnt = doorScheduleService.getDoorScheduleListCount(paramMap);

            PaginationVO pageVO = new PaginationVO();
            pageVO.setCurPage(srchPage);
            pageVO.setRecPerPage(srchRecPerPage);
            pageVO.setTotRecord(totalCnt);
            pageVO.setUnitPage(curPageUnit);
            pageVO.calcPageList();

            model.addAttribute("doorScheduleList", doorScheduleList);
            model.addAttribute("keyword", keyword);
            model.addAttribute("pagination", pageVO);

        } catch (Exception e){
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return "cubox/door/schedule/list";
    }


    /**
     * 스케줄 목록 조회 Ajax
     *
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listAjax.do", method = RequestMethod.GET)
    public ModelAndView getScheduleList(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        //todo 세션
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

            List<HashMap> doorScheduleList = doorScheduleService.getDoorScheduleList(paramMap);
            int totalCnt = doorScheduleService.getDoorScheduleListCount(paramMap);

            PaginationVO pageVO = new PaginationVO();
            pageVO.setCurPage(srchPage);
            pageVO.setRecPerPage(srchRecPerPage);
            pageVO.setTotRecord(totalCnt);
            pageVO.setUnitPage(curPageUnit);
            pageVO.calcPageList();

            model.addAttribute("doorScheduleList", doorScheduleList);
            model.addAttribute("keyword", keyword);
            model.addAttribute("pagination", pageVO);

        } catch (Exception e){
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

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
    @RequestMapping(value = "/add.do", method = RequestMethod.GET)
    public String showScheduleAddView(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        return "cubox/door/schedule/add";
    }

    // 출입문 그룹 관리 상세
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(ModelMap model, @PathVariable int id, HttpServletRequest request) throws Exception {

        HashMap<String, Object> paramMap = new HashMap();
        paramMap.put("doorSchId", id);

        HashMap doorScheduleDetail = doorScheduleService.getDoorScheduleDetail(id);
        List<HashMap> doorGroupList = doorGroupService.getDoorGroupList(paramMap);

        model.addAttribute("doorScheduleDetail", doorScheduleDetail);
        model.addAttribute("doorGroupList", doorGroupList);

        return "cubox/door/schedule/detail";
    }

    @ResponseBody
    @RequestMapping(value = "/save.do", method = RequestMethod.POST)
    public ModelAndView saveSchedule(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        String resultCode = "Y";
        String newDoorId = "";
        try {
            newDoorId = doorScheduleService.addSchedule(commandMap);
        } catch (Exception e) {
            e.getStackTrace();
            resultCode = "N";
        }

        modelAndView.addObject("resultCode", resultCode);
        modelAndView.addObject("newDoorId", newDoorId);

        return modelAndView;
    }


    /**
     * 요일별 스케쥴 등록
     * @param commandMap
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/day/add.do", method = RequestMethod.POST)
    public ModelAndView addScheduleByDay(@RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        String resultCode = "Y";

        JSONParser parser = new JSONParser();
        Object obj = parser.parse((String) commandMap.get("day_schedule"));

        List<Map<String, Object>> dayScheduleList = getListMapFromJsonArray((JSONArray) obj);

        try {

            if( dayScheduleList.size() > 0 ){
                for (int i = 0; i < dayScheduleList.size(); i++) {
                    Map<String, Object> dayScheduleParam = dayScheduleList.get(i);
                    dayScheduleParam.put("doorSchId", commandMap.get("doorSchId"));
                    dayScheduleParam.put("id", commandMap.get("id"));
                    dayScheduleParam.put("id", commandMap.get("id"));
                    dayScheduleParam.put("weekday", dayScheduleParam.get("weekday"));
                    dayScheduleParam.put("begTm", dayScheduleParam.get("beg_tm"));
                    dayScheduleParam.put("endTm", dayScheduleParam.get("end_tm"));
                    doorScheduleService.addScheduleByDay(dayScheduleParam);
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
            resultCode = "N";
        }

        modelAndView.addObject("resultCode", resultCode);

        return modelAndView;
    }

    public static String readBody(HttpServletRequest request) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuilder builder = new StringBuilder();

        String buffer;
        while ((buffer = input.readLine()) != null) {
            if (builder.length() > 0) {
                builder.append("\n");
            }
            builder.append(buffer);
        }
        return builder.toString();
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

    @RequestMapping(value = "/name/verification.do", method = RequestMethod.GET)
    public ModelAndView getDoorScheduleNameValidation(ModelMap model, @RequestParam Map<String, Object> commandMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");
        HashMap<String, Object> param = new HashMap<String, Object>();

        param.put("doorGroupNm", commandMap.get("doorGroupNm"));

        int doorScheduleNameVerificationCnt = doorScheduleService.getDoorScheduleNameVerification(param);

        modelAndView.addObject("doorScheduleNameVerificationCnt", doorScheduleNameVerificationCnt);

        return modelAndView;
    }
}
