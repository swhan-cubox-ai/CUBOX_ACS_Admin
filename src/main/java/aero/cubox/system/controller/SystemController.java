package aero.cubox.system.controller;

import aero.cubox.cmmn.service.CommonService;
import aero.cubox.core.vo.EmpVO;
import aero.cubox.core.vo.PaginationVO;
import aero.cubox.core.vo.PrivacyVO;
import aero.cubox.system.service.SystemService;
import aero.cubox.util.CommonUtils;
import aero.cubox.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/system")
public class SystemController {

    @Resource(name = "systemService")
    private SystemService systemService;

    @Resource(name = "commonService")
    private CommonService commonService;

    @Resource(name = "commonUtils")
    private CommonUtils commonUtils;


    @RequestMapping(value="/privacy/list.do")
    public String privacyList(ModelMap model, @RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try{
            PrivacyVO vo = new PrivacyVO();

            String srchPage       = StringUtil.nvl(commandMap.get("srchPage"), "1");
            String srchRecPerPage = StringUtil.nvl(commandMap.get("srchRecPerPage"), "10");
            String fromDt = StringUtil.nvl(commandMap.get("fromDt"), "");
            String toDt = StringUtil.nvl(commandMap.get("toDt"), "");
            String keyword1 = StringUtil.nvl(commandMap.get("keyword1"), "");
            String keyword2 = StringUtil.nvl(commandMap.get("keyword2"), "");

            vo.setSrchPage(Integer.parseInt(srchPage));
            vo.setSrchCnt(Integer.parseInt(srchRecPerPage));
            vo.autoOffset();

            vo.setFromDt(fromDt);
            vo.setToDt(toDt);
            vo.setKeyword1(keyword1);
            vo.setKeyword2(keyword2);

            int totalCnt = systemService.getPrivacyListCount(vo);
            List<PrivacyVO> privacyList = systemService.getPrivacyList(vo);

            PaginationVO pageVO = new PaginationVO();
            pageVO.setCurPage(vo.getSrchPage());
            pageVO.setRecPerPage(vo.getSrchCnt());
            pageVO.setTotRecord(totalCnt);
            pageVO.setUnitPage(vo.getCurPageUnit());
            pageVO.calcPageList();

            model.addAttribute("privacyList", privacyList);
            model.addAttribute("data", vo);
            model.addAttribute("pagination", pageVO);

        } catch(Exception e) {
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return "cubox/system/privacy/list";
    }

    @ResponseBody
    @RequestMapping(value = "/privacy/getEmpDetail.do")
    public ModelAndView getEmpDetail(HttpServletRequest request, @RequestParam Map<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try {
            Map data = systemService.getEmpDetail(param);

            modelAndView.addObject("data", data);
            modelAndView.addObject("result", "success");
        } catch(Exception e) {
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/privacy/delAllPrivacy.do")
    public ModelAndView delAllPrivacy(HttpServletRequest request, @RequestParam Map<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try {
            systemService.delAllPrivacy(param);
            modelAndView.addObject("result", "success");
        } catch(Exception e) {
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/privacy/delSelectedPrivacy.do")
    public ModelAndView delSelectedPrivacy(HttpServletRequest request, @RequestParam Map<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try {
            systemService.delSelectedPrivacy(param);
            modelAndView.addObject("result", "success");
        } catch(Exception e) {
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return modelAndView;
    }

}
