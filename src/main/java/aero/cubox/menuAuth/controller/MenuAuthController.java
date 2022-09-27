package aero.cubox.menuAuth.controller;

import aero.cubox.cmmn.service.CommonService;

import aero.cubox.core.vo.*;

import aero.cubox.menuAuth.service.MenuAuthService;
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

/**
 * 메뉴권한 관리
 * @author bhs
 *
 *
 */
@Controller
public class MenuAuthController {

    @Resource(name = "menuAuthService")
    private MenuAuthService menuAuthService;

    @Resource(name = "commonService")
    private CommonService commonService;


    @RequestMapping(value = "/menuAuth/list.do")
    public String list(ModelMap model, @RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try{
            MenuAuthVO vo = new MenuAuthVO();

            String srchPage       = StringUtil.nvl(commandMap.get("srchPage"), "1");
            String srchRecPerPage = StringUtil.nvl(commandMap.get("srchRecPerPage"), "10");
            String keyword = StringUtil.nvl(commandMap.get("keyword"), "");

            vo.setSrchPage(Integer.parseInt(srchPage));
            vo.setSrchCnt(Integer.parseInt(srchRecPerPage));
            vo.autoOffset();

            vo.setRoleNm(keyword);

            int totalCnt = menuAuthService.getMenuAuthListCount(vo);
            List<MenuAuthVO> menuAuthList = menuAuthService.getMenuAuthList(vo);

            PaginationVO pageVO = new PaginationVO();
            pageVO.setCurPage(vo.getSrchPage());
            pageVO.setRecPerPage(vo.getSrchCnt());
            pageVO.setTotRecord(totalCnt);
            pageVO.setUnitPage(vo.getCurPageUnit());
            pageVO.calcPageList();

            model.addAttribute("menuAuthList", menuAuthList);
            //model.addAttribute("cntPerPage", cntPerPage);
            model.addAttribute("data", vo);
            model.addAttribute("pagination", pageVO);

        }catch (Exception e){
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return "cubox/menuAuth/list";
    }

    @ResponseBody
    @RequestMapping(value="/menuAuth/modifyMenuAuth.do")
    public ModelAndView modifyMenuAuth(HttpServletRequest request, @RequestParam HashMap<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try{
            menuAuthService.modifyMenuAuth(param);
            modelAndView.addObject("result", "success");
        }catch (Exception e){
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value="/menuAuth/addMenuAuth.do")
    public ModelAndView addMenuAuth(HttpServletRequest request, @RequestParam HashMap<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try{
            menuAuthService.registMenuAuth(param);
            modelAndView.addObject("result", "success");
        }catch (Exception e){
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value="/menuAuth/deleteMenuAuth.do")
    public ModelAndView deleteMenuAuth(HttpServletRequest request, @RequestParam HashMap<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try{
            menuAuthService.deleteMenuAuth(param);
            modelAndView.addObject("result", "success");
        }catch (Exception e){
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return modelAndView;
    }


    @ResponseBody
    @RequestMapping(value = "/menuAuth/getUserRoleList.do")
    public ModelAndView getUserRoleList(HttpServletRequest request, @RequestParam Map<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try {
            List<MenuAuthVO> list = menuAuthService.getUserRoleList(param);

            modelAndView.addObject("list", list);

        } catch(Exception e) {
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return modelAndView;
    }

    @RequestMapping(value="/menuAuth/add.do", method= RequestMethod.GET)
    public String add(ModelMap model, HttpServletRequest request) throws Exception {
        return "cubox/menuAuth/add";
    }

    @RequestMapping(value="/menuAuth/detail/{id}", method= RequestMethod.GET)
    public String detail(ModelMap model, @PathVariable int id, HttpServletRequest request) throws Exception {
        HashMap data = (HashMap) menuAuthService.getMenuAuthDetail(id);

        model.addAttribute("isModify", false);
        model.addAttribute("data", data);

        return "cubox/menuAuth/detail";
    }

    @RequestMapping(value="/menuAuth/modify/{id}", method= RequestMethod.GET)
    public String modify(ModelMap model, @PathVariable int id, HttpServletRequest request) throws Exception {
        HashMap data = (HashMap) menuAuthService.getMenuAuthDetail(id);

        model.addAttribute("isModify", true);
        model.addAttribute("data", data);

        return "cubox/menuAuth/detail";
    }

    @ResponseBody
    @RequestMapping(value="/menuAuth/getAuthMenuTree.do",method= {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getAuthMenuTree(HttpServletRequest request, @RequestParam Map<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try{
            List<Map> allList = menuAuthService.getAuthMenuTree(param);
            List<Map> myList = menuAuthService.getAuthMyMenuTree(param);

            modelAndView.addObject("allList", allList);
            modelAndView.addObject("myList", myList);

        }catch (Exception e){
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return modelAndView;
    }
}