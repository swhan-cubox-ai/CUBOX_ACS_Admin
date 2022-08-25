package aero.cubox.menu.service;

//import aero.cubox.core.vo.AuthorVO;
import aero.cubox.core.vo.MenuDetailVO;
import aero.cubox.core.vo.MenuVO;
//import aero.cubox.menu.vo.MenuClVO;
//import aero.cubox.menu.vo.MenuDetailVO;

import java.util.HashMap;
import java.util.List;

public interface MenuService {
//	public List<MenuClVO> getAuthorMenuCl(HashMap<String, Object> map) throws Exception;
//	public List<MenuDetailVO> getAuthMenuList(HashMap<String, Object> map) throws Exception;
//	public List<MenuDetailVO> getMenuList(HashMap<String, Object> map) throws Exception;
//	public List<MenuDetailVO> getTotalMenuList(HashMap map) throws Exception;
//	public List<MenuDetailVO> getUserMenuList(HashMap map) throws Exception;
//	public void saveAuthMenuGroup(HashMap pMap) throws Exception;
//
//	public List<AuthorVO> getAuthorList(HashMap<String, Object> map) throws Exception;

	public List<MenuVO> getMenuList(HashMap<String, Object> map) throws Exception;

	public List<MenuDetailVO> getMenuDetail(HashMap<String, Object> map) throws Exception;
}
