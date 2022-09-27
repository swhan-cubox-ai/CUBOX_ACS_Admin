package aero.cubox.menuAuth.service;

import aero.cubox.core.vo.MenuAuthVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MenuAuthService {


    public List<MenuAuthVO> getMenuAuthList(MenuAuthVO vo) throws Exception;

    public Map getMenuAuthDetail(int id) throws Exception;

    public List<MenuAuthVO> getUserRoleList(Map<String, Object> map) throws Exception;

    public List<MenuAuthVO> getUserAuthList(Map<String, Object> map) throws Exception;

    public List<MenuAuthVO> getRoleList() throws Exception;

    public List<MenuAuthVO> getRoleListByUser(Map<String, Object> map) throws Exception;

    public List<Map> getAuthMenuTree(Map<String, Object> map) throws Exception;

    public List<Map> getAuthMyMenuTree(Map<String, Object> map) throws Exception;

    public int getMenuAuthListCount(MenuAuthVO vo) throws Exception;

    public void modifyMenuAuth(HashMap<String, Object> map) throws Exception;

    public void registMenuAuth(HashMap<String, Object> map) throws Exception;

    public void deleteMenuAuth(HashMap<String, Object> map) throws Exception;

    public int addRole(HashMap<String, Object> map) throws Exception;

    public int delRole(HashMap<String, Object> map) throws Exception;

    public int addMenuAuth(HashMap<String, Object> map) throws Exception;

    public int delMenuAuth(HashMap<String, Object> map) throws Exception;

    public int addUserRole(HashMap<String, Object> map) throws Exception;

    public int delUserRole(HashMap<String, Object> map) throws Exception;
}
