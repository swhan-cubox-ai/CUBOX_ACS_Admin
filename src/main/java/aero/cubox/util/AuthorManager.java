package aero.cubox.util;

import java.awt.*;
import java.util.*;
import java.util.List;

import aero.cubox.core.vo.MenuVO;
//import aero.cubox.menu.vo.MenuClVO;
//import aero.cubox.menu.vo.MenuDetailVO;

public class AuthorManager{

	private static AuthorManager authorManager = null;
//	private static LinkedHashMap<String, List<MenuDetailVO>> MENU_INFO = new LinkedHashMap<String, List<MenuDetailVO>>();
//	private static LinkedHashMap<String, List<MenuClVO>> MENU_CL_INFO = new LinkedHashMap<String, List<MenuClVO>>();
//	private static LinkedHashMap<String, List<MenuDetailVO>> MENU_DETAIL_INFO = new LinkedHashMap<String, List<MenuDetailVO>>();

	private static LinkedHashMap<String, List<MenuVO>> MENU_LIST = new LinkedHashMap<String, List<MenuVO>>();
	private static boolean AUTHOR_AT = false;

	public static synchronized AuthorManager getInstance(){
		if(authorManager == null){
			authorManager = new AuthorManager();
		}
		return authorManager;
	}

//	public synchronized List<MenuClVO> getMenuCl(String role_id){
//		return MENU_CL_INFO.get(role_id);
//	}
//
//	public synchronized void setMenuCl(String author, List<MenuClVO> vo){
//		MENU_CL_INFO.put(author, vo);
//	}
//
//
//	public synchronized List<MenuDetailVO> getMenu(String author, String menuClCode){
//		return MENU_INFO.get(author+"_"+menuClCode);
//	}
//
//	public synchronized void setMenu(String author, String menuClCode, List<MenuDetailVO> menuList){
//		MENU_INFO.put(author+"_"+menuClCode, menuList);
//	}

	public synchronized void complete() {
		AUTHOR_AT = true;
	}

	public synchronized boolean is() {
		return AUTHOR_AT;
	}

	public synchronized void clear() {
//		MENU_INFO = new LinkedHashMap<String, List<MenuDetailVO>>();
//		MENU_CL_INFO = new LinkedHashMap<String, List<MenuClVO>>();
		AUTHOR_AT = false;
		MENU_LIST = new LinkedHashMap<String, List<MenuVO>>();
	}

//	public void setDetailMenu(String authorId, List<MenuDetailVO> menuList) {
//		MENU_DETAIL_INFO.put(authorId, menuList);
//	}

//	public List<MenuDetailVO> getDetailMenu(String authorId) {
//		return MENU_DETAIL_INFO.get(authorId);
//	}

//	public String getMainRedirect(String author) {
//		String strMain = "/userInfo/userMngmt.do";
//		String returnUrl = "";
//		int i = 0;
//		List<MenuDetailVO> dlist = getDetailMenu (author);
//		if(dlist != null && dlist.size() > 0) {
//			for(MenuDetailVO mv : dlist) {
//				if(mv.getMenu_url()!=null) {
//					if(mv.getMenu_url().endsWith(strMain)) {
//						returnUrl = strMain;
//						break;
//					}
//					if(i==0) {
//						returnUrl = mv.getMenu_url();
//						i++;
//					}
//				}
//			}
//		}
//		if(returnUrl == null || returnUrl.trim().equals("")) {
//			returnUrl = "/login.do";
//		}
//		return returnUrl;
//	}

	public synchronized void setMenuList(String role_id, List<MenuVO> vo){
		MENU_LIST.put(role_id, vo);
	}

	public synchronized List<MenuVO> getMenuList(String role_id){
		return MENU_LIST.get(role_id);
	}
}
