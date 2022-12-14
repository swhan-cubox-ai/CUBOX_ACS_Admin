package aero.cubox.util;

import aero.cubox.core.vo.*;
import aero.cubox.menu.service.MenuService;
import aero.cubox.role.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SessionCheck extends HandlerInterceptorAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(SessionCheck.class);

	AuthorManager authorManager = AuthorManager.getInstance();

	@Resource
    private MenuService menuService;

	@Resource
	private RoleService roleService;

	/**
	 * 세션정보 체크
	 *
	 * @param request
	 * @return
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		//로그인없이 볼수 있는 페이지
		ArrayList<String> freeAccessUrls = new ArrayList<String>();

		//로그인
		freeAccessUrls.add("/login.do");
		freeAccessUrls.add("/common/loginProc.do");

		//강제로그인
		freeAccessUrls.add("/common/loginSession.do");


		// 사용자등록 임시
		freeAccessUrls.add("/user/addUser.do");
		freeAccessUrls.add("/admin/pwChange.do");


		//권한관계 없이 볼수 있는 페이지
		ArrayList<String> defaultAccessUrls = new ArrayList<String>();
		defaultAccessUrls.add("/main.do");

		String uri = request.getServletPath();

		LOGGER.debug("uri >>>> "+uri);

		LoginVO loginVO = (LoginVO) request.getSession().getAttribute("loginVO");

		//로그인
		if(loginVO != null && loginVO.getDirect_yn() == "Y") {
			setAuthorInfo(loginVO.getRole_id() + "");
			return true;
		}

		if (loginVO != null && loginVO.getLogin_id() != null && !loginVO.getLogin_id().equals("")) {
			String role_id = loginVO.getRole_id();
			//권한 확인
			if(!authorManager.is()) setAuthorInfo(role_id);

			//공통화면 체크
			if(defaultAccessUrls.contains(uri)) { 	//index.do
				request.getSession().setAttribute("uriPath", uri);
				return true;
			} else {
				if(uri != null && (uri.endsWith("login.do") || uri.endsWith("index.do"))) {
					//String strUrlPath = authorManager.getMainRedirect(loginVO.getAuthor_id());
					//request.getSession().setAttribute("uriPath", strUrlPath);
					//response.sendRedirect(strUrlPath);
					request.getSession().setAttribute("uriPath", "/main.do");
					response.sendRedirect("/main.do");
					return false;
				} else {
					request.getSession().setAttribute("uriPath", uri);
					return true;
				}
			}
		}else{
			if(freeAccessUrls.contains(uri)) {
				return true;
			} else if (uri.contains("Popup")) {
				PrintWriter writer = response.getWriter();
				writer.println("<script>parent.location.href='/login.do';</script>");	//Popup 부모창 로그인 이동
				return false;
			} else {
				response.sendRedirect("/login.do");
				return false;
			}
		}
	}

	public static String getUserIp() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String ip = request.getHeader("X-FORWARDED-FOR");
		if (ip == null) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public void setAuthorInfo(String role_id) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("role_id", role_id);
		// 임시 -- 모든 대메뉴접근
		//map.put("role_id", "");

		// 권한별 대메뉴 정보
		List<MenuVO> urlList = menuService.getMenuList(map);
    	for(MenuVO mvo : urlList) {
    		LOGGER.debug("result >>>> "+mvo.getMenu_cd());
    		String menuCd = mvo.getMenu_cd();

        	HashMap<String, Object> sMap = new HashMap<String, Object>();
        	sMap.put("menu_cd", menuCd);
			//권한별 sub menu 정보

			//String strClCode = vo.getMenu_cl_code();
			String strClCode = "left_icon5.png";
			mvo.setIcon_img(strClCode); // todo 임시 - 메뉴로고설정 추후 설계에 따라서 변경

			sMap.put("parent_menu_cd", mvo.getMenu_cd());
			List<MenuDetailVO> menuList = menuService.getMenuDetail(sMap);

			// todo 임시 - 메뉴URL 설정 추후 설계에 따라서 변경
			for(MenuDetailVO mdvo : menuList) {
				String menuUrl = MenuUrlSetting.getMenuUrl((String) mdvo.getMenu_cd());
				mdvo.setMenu_url(menuUrl);
			}

			mvo.setList(menuList);
    	}
		authorManager.setMenuList(role_id, urlList);
		authorManager.complete();
	}
}
