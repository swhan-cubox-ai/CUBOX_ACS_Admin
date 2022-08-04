package aero.cubox.admin.controller;

import aero.cubox.admin.service.AdminService;
import aero.cubox.cmmn.service.CommonService;
import aero.cubox.core.vo.*;
import aero.cubox.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class AdminController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
	
	private int recPerPage  = 10; //조회할 페이지 수
	private int curPageUnit = 10; //한번에 표시할 페이지 번호 개수

	/** adminService */
	@Resource(name = "adminService")
	private AdminService adminService;

	/** commonService */
	@Resource(name = "commonService")
	private CommonService commonService;


	/**
	 * 계정관리화면
	 * @return admin/account_management
	 * @throws Exception
	 */
	@RequestMapping(value="/admin/accountMngmt.do")
	public String accountMngmt(ModelMap model, @RequestParam Map<String, Object> param,	HttpServletRequest request) throws Exception {

		LoginVO loginVO = (LoginVO)request.getSession().getAttribute("loginVO");

		if (loginVO != null && loginVO.getFsiteid() != null && !loginVO.getFsiteid().equals("")) {

			SiteUserVO siteUserVO = new SiteUserVO();

			int srchPage = String.valueOf(request.getParameter("srchPage")).matches("(^[0-9]*$)") ? Integer.valueOf(request.getParameter("srchPage")) : 1;
			String srchRecPerPage = StringUtil.nvl(param.get("srchRecPerPage"), String.valueOf(siteUserVO.getSrchCnt()));

			// paging
			siteUserVO.setSrchPage(srchPage);
			siteUserVO.setSrchCnt(Integer.parseInt(srchRecPerPage));
			siteUserVO.autoOffset();
			
			// search
			siteUserVO.setSite_id(StringUtil.nvl(param.get("srchCond")));
			siteUserVO.setFsiteid(StringUtil.nvl(param.get("srchFsiteid")));
			siteUserVO.setFname(StringUtil.nvl(param.get("srchFname")));
			siteUserVO.setAuthor_id(StringUtil.nvl(param.get("srchAuthorId")));

			List<SiteUserVO> siteUserList = adminService.getSiteUserList(siteUserVO);
			int siteUserCnt = 10; //TODO temp 20220803

			PaginationVO pageVO = new PaginationVO();
			pageVO.setCurPage(srchPage);
			pageVO.setRecPerPage(siteUserVO.getSrchCnt());
			pageVO.setTotRecord(siteUserCnt);
			pageVO.setUnitPage(siteUserVO.getCurPageUnit());
			pageVO.calcPageList();

			List<CodeVO> authorList = commonService.getAuthorList();
			List<SiteVO> siteList = null;
			List<CodeVO> cntPerPage = commonService.getCodeList("combo","COUNT_PER_PAGE");  //page당 record 수

			model.addAttribute("authorList", authorList);
			model.addAttribute("centerList", siteList);
			model.addAttribute("cntPerPage", cntPerPage);
			
			model.addAttribute("siteUserList", siteUserList); //검색목록
			model.addAttribute("siteUserVO", siteUserVO);
			model.addAttribute("pagination", pageVO);			

			return "cubox/admin/account_management";
		}else{
			return "redirect:/login.do";
		}
	}
	
	/**
	 * 입력한 id 중복여부를 체크하여 사용가능여부를 확인
	 * @param commandMap 파라메터전달용 commandMap
	 * @return admin/idDplctCnfirm
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/admin/idDplctCnfirm.do")
	public ModelAndView checkIdDplct(@RequestParam Map<String, Object> commandMap) throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsonView");

		String checkId = (String) commandMap.get("checkId");
		//checkId = new String(checkId.getBytes("ISO-8859-1"), "UTF-8");

		int usedCnt = adminService.checkIdDplct(checkId);
		modelAndView.addObject("usedCnt", usedCnt);
		modelAndView.addObject("checkId", checkId);

		return modelAndView;
	}


	/**
	 * 계정추가
	 * @param commandMap 파라메터전달용 commandMap
	 * @return modelAndView
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/admin/siteUserAddSave.do")
	public ModelAndView siteUserAddSave(@RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {

		LoginVO loginVO = (LoginVO)request.getSession().getAttribute("loginVO");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsonView");

		String fsiteid = (String) commandMap.get("fsiteid");
		String fname = (String) commandMap.get("fname");
		String siteId = (String) commandMap.get("siteId");
		String authorId = (String) commandMap.get("authorId");
		String fphone01 = (String) commandMap.get("fphone01");
		String fphone02 = (String) commandMap.get("fphone02");
		String fphone03 = (String) commandMap.get("fphone03");

		String fphone = "";
		if(fphone01!=null && fphone02!=null && fphone03!=null && !fphone01.equals("") && !fphone02.equals("") && !fphone03.equals("")) {
			fphone = fphone01 + "-" + fphone02 + "-" + fphone03;
		}

		SiteUserVO siteUserVO = new SiteUserVO();

		int addCnt = 0;
		int uidChkCnt = 0;


		if(uidChkCnt > 0) {
			addCnt = -2;
		} else {
			siteUserVO.setFsiteid(fsiteid);
			siteUserVO.setFname(fname);
			siteUserVO.setFphone(fphone);
			siteUserVO.setSite_id(siteId);
			siteUserVO.setAuthor_id(authorId);

			addCnt = adminService.siteUserAddSave(siteUserVO);

		}
		modelAndView.addObject("addCnt", addCnt);

		return modelAndView;
	}

	/**
	 * 계정편집
	 * @param commandMap 파라메터전달용 commandMap
	 * @return modelAndView
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/admin/siteUserInfoChangeSave.do")
	public ModelAndView siteUserInfoChangeSave(@RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {

		LoginVO loginVO = (LoginVO)request.getSession().getAttribute("loginVO");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsonView");

		String fsiteid = (String) commandMap.get("fsiteid");
		String fname = (String) commandMap.get("fname");
		String siteId = (String) commandMap.get("siteId");
		String authorId = (String) commandMap.get("authorId");
		String fphone01 = (String) commandMap.get("fphone01");
		String fphone02 = (String) commandMap.get("fphone02");
		String fphone03 = (String) commandMap.get("fphone03");

		String fphone = "";
		if(fphone01!=null && fphone02!=null && fphone03!=null && !fphone01.equals("") && !fphone02.equals("") && !fphone03.equals("")) {
			fphone = fphone01 + "-" + fphone02 + "-" + fphone03;
		}

		SiteUserVO siteUserVO = new SiteUserVO();
		siteUserVO.setFsiteid(fsiteid);

		int addCnt = 0;
		int uidChkCnt = 0;


		if(uidChkCnt > 0) {
			addCnt = -2;
		} else {
			siteUserVO.setFname(fname);
			siteUserVO.setFphone(fphone);
			siteUserVO.setAuthor_id(authorId);
			siteUserVO.setSite_id(siteId);

			addCnt = adminService.siteUserInfoChangeSave(siteUserVO);


		}
		modelAndView.addObject("addCnt", addCnt);

		return modelAndView;
	}

	/**
	 * 계정사용유무변경
	 * @param commandMap 파라메터전달용 commandMap
	 * @return modelAndView
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/admin/siteUserFuseynChangeSave.do")
	public ModelAndView siteUserFuseynChangeSave(@RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {

		LoginVO loginVO = (LoginVO)request.getSession().getAttribute("loginVO");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsonView");

		String fsiteid = (String) commandMap.get("fsiteid");
		String fuseyn = (String) commandMap.get("fuseyn");

		SiteUserVO siteUserVO = new SiteUserVO();
		siteUserVO.setFsiteid(fsiteid);
		siteUserVO.setFuseyn(fuseyn);

		int addCnt = adminService.siteUserFuseynChangeSave(siteUserVO);

		String fuseynm = fuseyn.equals("Y") ? "사용중" : "사용안함" ;
		String fdetail = "계정 : " + fsiteid + ", " + fuseynm;


		modelAndView.addObject("addCnt", addCnt);

		return modelAndView;
	}

	/**
	 * 계정비밀번호초기화
	 * @param commandMap 파라메터전달용 commandMap
	 * @return modelAndView
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/admin/siteUserPasswdReset.do")
	public ModelAndView siteUserPasswdReset(@RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {

		LoginVO loginVO = (LoginVO)request.getSession().getAttribute("loginVO");

    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("jsonView");

		String fsiteid = (String) commandMap.get("fsiteid");

		SiteUserVO siteUserVO = new SiteUserVO();
		siteUserVO.setFsiteid(fsiteid);

		int addCnt = adminService.siteUserPasswdReset(siteUserVO);

		String fdetail = "계정 : " + fsiteid;

		modelAndView.addObject("addCnt", addCnt);

		return modelAndView;
	}

	/**
	 * 비밀번호변경화면
	 * @param commandMap 파라메터전달용 commandMap
	 * @return admin/pw_change
	 * @throws Exception
	 */
	@RequestMapping(value="/admin/pwChange.do")
	public String pwChangeView(ModelMap model, @RequestParam Map<String, Object> commandMap,
			HttpServletRequest request) throws Exception {

		LoginVO loginVO = (LoginVO)request.getSession().getAttribute("loginVO");

		if (loginVO != null && loginVO.getFsiteid() != null && !loginVO.getFsiteid().equals("")) {
			return "cubox/admin/pw_change";
		}else{
			return "redirect:/login.do";
		}
	}

	/**
	 * 비밀번호변경
	 * @param commandMap 파라메터전달용 commandMap
	 * @return modelAndView
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/admin/passwdChangeSave.do")
	public ModelAndView passwdChangeSave(@RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {

		ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("jsonView");

		String currentpasswd = (String) commandMap.get("currentpasswd");
		String fpasswd = (String) commandMap.get("fpasswd");

		LoginVO loginVO = (LoginVO)request.getSession().getAttribute("loginVO");

		if (loginVO != null && loginVO.getFsiteid() != null && !loginVO.getFsiteid().equals("")) {

			SiteUserVO siteUserVO = new SiteUserVO();
			siteUserVO.setFsiteid(loginVO.getFsiteid());
			siteUserVO.setFpasswd(currentpasswd);

			int checkPwd = adminService.checkPwd(siteUserVO);

			if(checkPwd > 0){
				siteUserVO.setFpasswd(fpasswd);
				int passwdCnt = adminService.passwdChangeSave(siteUserVO);

				modelAndView.addObject("checkPwdError", "N");

			}else{
				modelAndView.addObject("checkPwdError", "Y");
			}
		}

		return modelAndView;

	}

	/**
	 * 공통코드관리화면
	 * @param commandMap 파라메터전달용 commandMap
	 * @return admin/center_management
	 * @throws Exception
	 */
	@RequestMapping(value="/admin/commcodeMngmt.do")
	public String commcodeMngmt(ModelMap model, @RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {

		LoginVO loginVO = (LoginVO)request.getSession().getAttribute("loginVO");

		if (loginVO != null && loginVO.getFsiteid() != null && !loginVO.getFsiteid().equals("")) {

			String searchFkind1 = StringUtil.isNullToString(request.getParameter("searchFkind1"));
			String searchFkind2 = StringUtil.isNullToString(request.getParameter("searchFkind2"));
			String searchCode = StringUtil.isNullToString(request.getParameter("searchCode"));
			String searchCodeName = StringUtil.isNullToString(request.getParameter("searchCodeName"));
			String srchUseYn = StringUtil.isNullToString(request.getParameter("srchUseYn"));
			

			List<CodeVO> codeFkind1List = commonService.getCodeFkind1List();
			model.addAttribute("codeFkind1List", codeFkind1List);

			CodeVO codevo = new CodeVO();
			codevo.setFkind1(searchFkind1);
			codevo.setFkind2(searchFkind2);
			codevo.setFkind3(searchCode);
			codevo.setFvalue(searchCodeName.trim());
			codevo.setFuseyn(srchUseYn);
			
			List<CodeVO> codeFullList = commonService.getCodeFullList(codevo);
			model.addAttribute("codeFullList", codeFullList);
			
			List<CodeVO> codeFkind2List = commonService.getCodeFkind2List2(codevo);
			model.addAttribute("codeFkind2List", codeFkind2List);

			model.addAttribute("searchFkind1", searchFkind1);
			model.addAttribute("searchFkind2", searchFkind2);
			model.addAttribute("searchCode", searchCode);
			model.addAttribute("searchCodeName", searchCodeName);
			model.addAttribute("srchUseYn", srchUseYn);
			
			return "cubox/admin/code_management";
		}else{
			return "redirect:/login.do";
		}

	}
	
	@ResponseBody
	@RequestMapping(value = "/admin/getCodeFkind2List2.do")
	public ModelAndView getAuthGroup(HttpServletRequest request, @RequestParam Map<String, Object> param) throws Exception {

		LoginVO loginVO = (LoginVO)request.getSession().getAttribute("loginVO");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsonView");

		CodeVO codeVO = new CodeVO();
		codeVO.setFkind1(StringUtil.isNullToString(param.get("fkind1")));

		List<CodeVO> codeList = commonService.getCodeFkind2List2(codeVO);

		modelAndView.addObject("codeFkind2List", codeList);

		return modelAndView;
	}	

	/**
	 * 공통코드 추가
	 * @param
	 * @return modelAndView
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/admin/commcodeAddSave.do")
	public ModelAndView commcodeAddSave(@RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {
		LoginVO loginVO = (LoginVO)request.getSession().getAttribute("loginVO");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsonView");
		int rtn =  0;

		String strfk1 = StringUtil.nvl(commandMap.get("fkind1"));
		String strfk2 = StringUtil.nvl(commandMap.get("fkind2"));
		String strfk2_txt = StringUtil.nvl(commandMap.get("fkind2_txt"));
		String strfk3 = StringUtil.nvl(commandMap.get("fkind3"));
		String fvalue = StringUtil.nvl(commandMap.get("fvalue"));
		String forder = StringUtil.nvl(commandMap.get("forder"));
		
		if(strfk2.equals("txt")) strfk2 = strfk2_txt;

		CodeVO vo = new CodeVO();
		vo.setFkind1(strfk1);
		vo.setFkind2(strfk2);
		vo.setFkind3(strfk3);
		vo.setFuseyn("Y");
		vo.setFvalue(fvalue);
		vo.setForder(forder);

		//fkind3의 중복체크
		int fkind3cnt = commonService.getFkind3Cnt (vo);
		if(fkind3cnt > 0) {
			modelAndView.addObject("codeSaveCnt", -1);
		} else {
			rtn = commonService.insertCode (vo);
			modelAndView.addObject("codeSaveCnt", rtn);
		}

		return modelAndView;
	}

	/**
	 * 공통코드 수정
	 * @param
	 * @return modelAndView
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/admin/commcodeEditSave.do")
	public ModelAndView commcodeEditSave(@RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {
		//LoginVO loginVO = (LoginVO)request.getSession().getAttribute("loginVO");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsonView");
		int rtn =  0;

		String strfk1 = (String) commandMap.get("fkind1");
		String strfk2 = (String) commandMap.get("fkind2");
		String strfk3 = (String) commandMap.get("fkind3");
		String strfk3_org = (String) commandMap.get("fkind3_org");
		String fvalue = (String) commandMap.get("fvalue");
		String fuseyn = (String) commandMap.get("fuseyn");
		String forder = (String) commandMap.get("forder");

		CodeVO vo = new CodeVO();
		vo.setFkind1(strfk1);
		vo.setFkind2(strfk2);
		vo.setFkind3(strfk3);
		vo.setFkind3Org(strfk3_org);
		vo.setFuseyn(fuseyn);
		vo.setFvalue(fvalue);
		vo.setForder(forder);

		rtn = commonService.updateCode (vo);
		modelAndView.addObject("codeSaveCnt", rtn);

		return modelAndView;
	}

	/**
	 * 공통코드 사용유무 수정
	 * @param
	 * @return modelAndView
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/admin/commCodeUseynChangeSave.do")
	public ModelAndView commCodeUseynChangeSave(@RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {
		//LoginVO loginVO = (LoginVO)request.getSession().getAttribute("loginVO");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsonView");
		int rtn =  0;

		String strfk1 = (String) commandMap.get("fkind1");
		String strfk2 = (String) commandMap.get("fkind2");
		String strfk3 = (String) commandMap.get("fkind3");
		String fuseyn = (String) commandMap.get("fuseyn");

		CodeVO vo = new CodeVO();
		vo.setFkind1(strfk1);
		vo.setFkind2(strfk2);
		vo.setFkind3(strfk3);
		vo.setFuseyn(fuseyn);

		rtn = commonService.updateCodeUseYn (vo);
		modelAndView.addObject("codeSaveCnt", rtn);

		return modelAndView;
	}

	/**
	 * (사이트)권한 관리
	 * @param commandMap 파라메터전달용 commandMap
	 * @return admin/auth_management
	 * @throws Exception
	 */
	@RequestMapping(value="/admin/authMngmt.do")
	public String groupauthMngmt(ModelMap model, @RequestParam Map<String, Object> commandMap,
			HttpServletRequest request) throws Exception {

		LoginVO loginVO = (LoginVO)request.getSession().getAttribute("loginVO");
		if (loginVO != null && loginVO.getSite_id() != null && !loginVO.getSite_id().equals("")) {
			List<AuthorVO> authList = adminService.getAuthList();
			model.addAttribute("authList", authList);

			return "cubox/admin/auth_management";
		}else{
			return "redirect:/login.do";
		}

	}

	/**
	 * (사이트)권한 추가
	 * @param
	 * @return modelAndView
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/admin/authAddSave.do")
	public ModelAndView authAddSave(@RequestParam Map<String, Object> commandMap, HttpServletRequest request) throws Exception {
		LoginVO loginVO = (LoginVO)request.getSession().getAttribute("loginVO");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsonView");
		int rtn =  0;

		AuthorVO vo = new AuthorVO();
		vo.setAuthorNm(StringUtil.nvl(commandMap.get("txtAuthorNm")));
		vo.setAuthorDesc(StringUtil.nvl(commandMap.get("txtAuthorDesc")));
		vo.setSortOrdr(StringUtil.isNullToString(commandMap.get("txtSortOrdr")));
		vo.setUseYn(StringUtil.nvl(commandMap.get("selUseYn")));

		rtn = adminService.authAddSave(vo);

		modelAndView.addObject("authSaveCnt", rtn);

		return modelAndView;
	}

	
	private int getOffset(int srchPage, int srchCnt) {
		int offset = (srchPage - 1) * srchCnt;
		if(offset < 0) offset = 0;
    	return offset;
    }	
}
