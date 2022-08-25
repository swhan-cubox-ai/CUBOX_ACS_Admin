package aero.cubox.menu.service.impl;

import java.util.HashMap;
import java.util.List;

//import aero.cubox.core.vo.AuthorVO;
import aero.cubox.core.vo.MenuDetailVO;
import aero.cubox.core.vo.MenuVO;
import org.springframework.stereotype.Repository;

//import aero.cubox.menu.vo.MenuClVO;
//import aero.cubox.menu.vo.MenuDetailVO;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository
public class MenuDAO extends EgovAbstractMapper {

//	public List<MenuClVO> selectAuthorMenuCl(HashMap<String, Object> map) throws Exception {
//		return selectList ("admin.selectAuthorMenuCl", map);
//	}
//
//	public List<AuthorVO> selectAuthorList(HashMap<String, Object> map) throws Exception {
//		return selectList ("admin.selectAuthorList", map);
//	}
//
//	public List<MenuDetailVO> selectAuthMenuList(HashMap<String, Object> map) throws Exception {
//		return selectList ("menu.selectAuthMenuList", map);
//	}
//	public List<MenuDetailVO> selectMenuList(HashMap<String, Object> map) throws Exception {
//		return selectList ("admin.selectMenuList", map);
//	}
//	public List<MenuDetailVO> selectUserMenuList(HashMap<String, Object> map) throws Exception {
//		return selectList ("admin.selectUserMenuList", map);
//	}
//
//	public void insertAuthorMenu(HashMap pMap) throws Exception{
//		insert( "admin.insertAuthorMenu", pMap );
//
//	}
//	public void deleteAuthorMenu(HashMap pMap)throws Exception {
//		delete( "admin.deleteAuthorMenu", pMap );
//	}

	public List<MenuVO> getMenuList(HashMap<String, Object> map) throws Exception{
		return selectList("menu.getMenuList", map);
	}

	public List<MenuDetailVO> getMenuDetailList(HashMap<String, Object> map) throws Exception{
		return selectList("menu.getMenuDetailList", map);
	}
}
