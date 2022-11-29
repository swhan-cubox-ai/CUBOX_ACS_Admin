package aero.cubox.errManagement.controller;

import aero.cubox.cmmn.service.CommonService;
import aero.cubox.core.vo.CommonVO;
import aero.cubox.core.vo.FaceFeatureErrVO;
import aero.cubox.core.vo.PaginationVO;
import aero.cubox.errManagement.service.ErrManagementService;
import aero.cubox.util.AES256Util;
import aero.cubox.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.List;
import java.util.Map;


@Controller
public class FaceFeatureController {

    @Resource(name = "commonService")
    private CommonService commonService;

    @Resource(name = "faceFeatureErrService")
    private ErrManagementService errService;

    private static final Logger LOGGER = LoggerFactory.getLogger(FaceFeatureController.class);

    @RequestMapping(value="/err/faceFeatureList.do")
    public String list(ModelMap model, @RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");

        try {
            FaceFeatureErrVO vo = new FaceFeatureErrVO();
            String srchPage       = StringUtil.nvl(commandMap.get("srchPage"), "1");
            String srchRecPerPage = StringUtil.nvl(commandMap.get("srchRecPerPage"), "10");

            String empCd = StringUtil.nvl(commandMap.get("emp_cd"), null);
            String featureTyp = StringUtil.nvl(commandMap.get("feature_typ"), null);
            String empNm = StringUtil.nvl(commandMap.get("emp_nm"), null);


            vo.setFace_feature_typ(featureTyp);
            vo.setEmp_cd(empCd);
            vo.setEmp_nm(empNm);

            vo.setSrchPage(Integer.parseInt(srchPage));
            vo.setSrchCnt(Integer.parseInt(srchRecPerPage));
            vo.autoOffset();


            List<FaceFeatureErrVO> list = errService.selectFaceFeatureErrList(vo);
            int totalCnt = errService.getFaceFeatureErrCount(vo);

            PaginationVO pageVO = new PaginationVO();
            pageVO.setCurPage(vo.getSrchPage());
            pageVO.setRecPerPage(vo.getSrchCnt());
            pageVO.setTotRecord(totalCnt);
            pageVO.setUnitPage(vo.getCurPageUnit());
            pageVO.calcPageList();


            List<CommonVO> featureTypList = commonService.getCommonCodeList("FaceFeatureTyp");

            model.addAttribute("featureTypList", featureTypList);
            model.addAttribute("errList", list);
            model.addAttribute("cntPerPage", "10");
            model.addAttribute("data", vo);
            model.addAttribute("pagination", pageVO);

        } catch(Exception e) {
            e.printStackTrace();
            modelAndView.addObject("message", e.getMessage());
        }

        return "cubox/errManagement/feature/list";
    }

    @RequestMapping(value="/err/faceFeature/detail")
    public ModelAndView detail(ModelMap model, @RequestParam Map<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsonView");
        Integer id =  Integer.parseInt(param.get("id").toString());
        FaceFeatureErrVO vo = new FaceFeatureErrVO();
        vo.setId(id);
        FaceFeatureErrVO data = errService.selectFaceFeatureErrOne(vo);

        //byte[] img = byteArrDecode(data.getFace_img());
        String img = byteArrEncode((byte[]) data.getFace_img());

        //String img = byteArrEncode((byte[]) data.getFace_img());
        String errorFace = new String(Base64.getEncoder().encode(data.getFace_img()));
        //String errorFace = img;

        modelAndView.addObject("errorFace", errorFace);

        return modelAndView;
    }

    public static byte[] byteArrDecode(String encoded) throws Exception {
        AES256Util aes256Util = new AES256Util();
        byte[] result =  aes256Util.byteArrDecode(encoded, "s8LiEwT3if89Yq3i90hIo3HepqPfOhVd");
        return result;
    }
    public static String byteArrEncode(byte[] bytes) throws Exception {
        AES256Util aes256Util = new AES256Util();
        String result =  aes256Util.byteArrEncode(bytes, "s8LiEwT3if89Yq3i90hIo3HepqPfOhVd");
        return result;
    }




}
