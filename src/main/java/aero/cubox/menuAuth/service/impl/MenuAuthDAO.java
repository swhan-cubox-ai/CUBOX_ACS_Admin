package aero.cubox.menuAuth.service.impl;

import aero.cubox.core.vo.AuthMenuTreeVO;
import aero.cubox.core.vo.HolidayVO;
import aero.cubox.core.vo.MenuAuthVO;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("menuAuthDAO")
public class MenuAuthDAO extends EgovAbstractMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuAuthDAO.class);
    private static final String sqlNameSpace = "menuAuth.";

    public List<MenuAuthVO> getMenuAuthList(MenuAuthVO vo) throws Exception {
        return selectList ("menuAuth.getMenuAuthList", vo);
    }

    public Map getMenuAuthDetail(int id) throws Exception {
        return selectOne ("menuAuth.getMenuAuthDetail", id);
    }

    public List<MenuAuthVO> getUserRoleList(Map<String, Object> map) throws Exception {
        return selectList ("menuAuth.getUserRoleList", map);
    }

    public List<MenuAuthVO> getUserAuthList(Map<String, Object> map) throws Exception {
        return selectList ("menuAuth.getUserAuthList", map);
    }

    public List<MenuAuthVO> getRoleList() throws Exception {
        return selectList ("menuAuth.getRoleList");
    }

    public List<MenuAuthVO> getRoleListByUser(Map<String, Object> map) throws Exception {
        return selectList ("menuAuth.getRoleListByUser", map);
    }

    public List<Map> getAuthMenuTree(Map<String, Object> map) throws Exception {
        return selectList ("menuAuth.getAuthMenuTree", map);
    }

    public List<Map> getAuthMyMenuTree(Map<String, Object> map) throws Exception {
        return selectList ("menuAuth.getAuthMyMenuTree", map);
    }

    public int getMenuAuthListCount(MenuAuthVO vo) throws Exception {
        return selectOne ("menuAuth.getMenuAuthListCount", vo);
    }

    public int addRole(HashMap<String, Object> map) throws Exception {
        return insert ("menuAuth.addRole", map);
    }

    public int delRole(HashMap<String, Object> map) throws Exception {
        return delete ("menuAuth.delRole", map);
    }

    public int addMenuAuth(HashMap<String, Object> map) throws Exception {
        return insert ("menuAuth.addMenuAuth", map);
    }

    public int delMenuAuth(HashMap<String, Object> map) throws Exception {
        return delete ("menuAuth.delMenuAuth", map);
    }

    public int addUserRole(HashMap<String, Object> map) throws Exception {
        return insert ("menuAuth.addUserRole", map);
    }

    public int delUserRole(HashMap<String, Object> map) throws Exception {
        return delete ("menuAuth.delUserRole", map);
    }
}
