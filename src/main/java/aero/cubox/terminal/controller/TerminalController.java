package aero.cubox.terminal.controller;


import aero.cubox.cmmn.service.CommonService;
import aero.cubox.core.vo.CommonVO;
import aero.cubox.core.vo.PaginationVO;
import aero.cubox.core.vo.TerminalVO;
import aero.cubox.terminal.service.TerminalService;
import aero.cubox.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TerminalController {

    @Resource(name = "terminalService")
    private TerminalService terminalService;

    @Resource(name = "commonService")
    private CommonService commonService;


    @RequestMapping(value = "/terminal/list.do")
    public String list(ModelMap model, @RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try{
            TerminalVO vo = new TerminalVO();

            String srchPage       = StringUtil.nvl(commandMap.get("srchPage"), "1");
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

            List<CommonVO> terminalTypCombList = commonService.getCommonCodeList("TerminalTyp");
            List<CommonVO> buildingCombList = terminalService.getBuildingList();

            int totalCnt = terminalService.getTerminalListCount(vo);
            List<TerminalVO> terminalList = terminalService.getTerminalList(vo);

            PaginationVO pageVO = new PaginationVO();
            pageVO.setCurPage(vo.getSrchPage());
            pageVO.setRecPerPage(vo.getSrchCnt());
            pageVO.setTotRecord(totalCnt);
            pageVO.setUnitPage(vo.getCurPageUnit());
            pageVO.calcPageList();

            model.addAttribute("terminalTypCombList", terminalTypCombList);
            model.addAttribute("buildingCombList", buildingCombList);
            model.addAttribute("terminalList", terminalList);
            model.addAttribute("data", vo);
            model.addAttribute("pagination", pageVO);

        }catch (Exception e){
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return "cubox/terminal/list";
    }

    @RequestMapping(value="/terminal/add.do", method= RequestMethod.GET)
    public String add(ModelMap model, HttpServletRequest request) throws Exception {
        List<CommonVO> doorCombList = terminalService.getDoorList();
        List<CommonVO> terminalTypCombList = commonService.getCommonCodeList("TerminalTyp");
        List<CommonVO> complexAuthTypCombList = commonService.getCommonCodeList("ComplexAuthTyp");
        List<CommonVO> faceAuthTypCombList = commonService.getCommonCodeList("FaceAuthTyp");

        model.addAttribute("doorCombList", doorCombList);
        model.addAttribute("terminalTypCombList", terminalTypCombList);
        model.addAttribute("complexAuthTypCombList", complexAuthTypCombList);
        model.addAttribute("faceAuthTypCombList", faceAuthTypCombList);

        return "cubox/terminal/add";
    }

    @RequestMapping(value="/terminal/detail/{id}", method= RequestMethod.GET)
    public String detail(ModelMap model, @PathVariable int id, HttpServletRequest request) throws Exception {
        HashMap data = terminalService.getTerminalDetail(id);

        model.addAttribute("isModify", false);
        model.addAttribute("data", data);

        return "cubox/terminal/detail";
    }

    @RequestMapping(value="/terminal/modify/{id}", method= RequestMethod.GET)
    public String modify(ModelMap model, @PathVariable int id, HttpServletRequest request) throws Exception {
        List<CommonVO> doorCombList = terminalService.getDoorList();
        List<CommonVO> terminalTypCombList = commonService.getCommonCodeList("TerminalTyp");
        List<CommonVO> complexAuthTypCombList = commonService.getCommonCodeList("ComplexAuthTyp");
        List<CommonVO> faceAuthTypCombList = commonService.getCommonCodeList("FaceAuthTyp");

        HashMap data = terminalService.getTerminalDetail(id);

        model.addAttribute("isModify", true);
        model.addAttribute("doorCombList", doorCombList);
        model.addAttribute("terminalTypCombList", terminalTypCombList);
        model.addAttribute("complexAuthTypCombList", complexAuthTypCombList);
        model.addAttribute("faceAuthTypCombList", faceAuthTypCombList);
        model.addAttribute("data", data);

        return "cubox/terminal/detail";
    }

    @ResponseBody
    @RequestMapping(value="/terminal/addTerminal.do")
    public ModelAndView addTerminal(HttpServletRequest request, @RequestParam HashMap<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try{
            int result = terminalService.addTerminal(param);

            modelAndView.addObject("result", "success");
        }catch (Exception e){
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value="/terminal/deleteTerminal.do")
    public ModelAndView deleteTerminal(HttpServletRequest request, @RequestParam HashMap<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try{
            terminalService.deleteTerminal(param);
            modelAndView.addObject("result", "success");
        }catch (Exception e){
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value="/terminal/modifyTerminal.do")
    public ModelAndView modifyTerminal(HttpServletRequest request, @RequestParam HashMap<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try{
            terminalService.modifyTerminal(param);

            modelAndView.addObject("result", "success");
        }catch (Exception e){
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
            modelAndView.addObject("result", "fail");
        }

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/terminal/getDoorInfo.do")
    public ModelAndView getDoorInfo(HttpServletRequest request, @RequestParam Map<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try {
            Map data = terminalService.getDoorInfo(param);

            modelAndView.addObject("data", data);
            modelAndView.addObject("result", "success");
        } catch(Exception e) {
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/terminal/getBlackList.do")
    public ModelAndView getBlackList(HttpServletRequest request, @RequestParam Map<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try {
            List<Map> list = terminalService.getBlackList(param);

            modelAndView.addObject("list", list);

        } catch(Exception e) {
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/terminal/getWhiteList.do")
    public ModelAndView getWhiteList(HttpServletRequest request, @RequestParam Map<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try {
            List<Map> list = terminalService.getWhiteList(param);

            modelAndView.addObject("list", list);

        } catch(Exception e) {
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return modelAndView;
    }


    @RequestMapping(value="/terminal/black/modify/{id}", method= RequestMethod.GET)
    public String blackModify(ModelMap model, @PathVariable int id, HttpServletRequest request) throws Exception {
        model.addAttribute("wbTyp", "BLACK");
        model.addAttribute("id", id);

        return "cubox/terminal/wb";
    }

    @RequestMapping(value="/terminal/white/modify/{id}", method= RequestMethod.GET)
    public String whiteModify(ModelMap model, @PathVariable int id, HttpServletRequest request) throws Exception {
        model.addAttribute("wbTyp", "WHITE");
        model.addAttribute("id", id);

        return "cubox/terminal/wb";
    }

    @ResponseBody
    @RequestMapping(value = "/terminal/getEmpSourceList.do")
    public ModelAndView getEmpSourceList(HttpServletRequest request, @RequestParam Map<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try {
            List<Map> list = terminalService.getEmpSourceList(param);

            modelAndView.addObject("list", list);

        } catch(Exception e) {
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/terminal/getEmpTargetList.do")
    public ModelAndView getEmpTargetList(HttpServletRequest request, @RequestParam Map<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try {
            List<Map> list = terminalService.getEmpTargetList(param);

            modelAndView.addObject("list", list);

        } catch(Exception e) {
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value="/terminal/addWbList.do")
    public ModelAndView addWbList(HttpServletRequest request, @RequestParam HashMap<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try{
            terminalService.registWbList(param);
            modelAndView.addObject("result", "success");
        }catch (Exception e){
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return modelAndView;
    }

}
