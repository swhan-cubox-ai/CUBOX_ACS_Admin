package aero.cubox.menuAuth.service.impl;

import aero.cubox.core.vo.AuthMenuTreeVO;
import aero.cubox.core.vo.MenuAuthVO;
import aero.cubox.holiday.service.impl.HolidayDAO;
import aero.cubox.menuAuth.service.MenuAuthService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("menuAuthService")
public class MenuAuthServiceImpl extends EgovAbstractServiceImpl implements MenuAuthService {

    @Resource(name="menuAuthDAO")
    private MenuAuthDAO menuAuthDAO;

    @Override
    public List<MenuAuthVO> getMenuAuthList(MenuAuthVO vo) throws Exception {
        return menuAuthDAO.getMenuAuthList(vo);
    }

    @Override
    public Map getMenuAuthDetail(int id) throws Exception {
        return menuAuthDAO.getMenuAuthDetail(id);
    }

    @Override
    public List<MenuAuthVO> getUserRoleList(Map<String, Object> map) throws Exception {
        return menuAuthDAO.getUserRoleList(map);
    }

    @Override
    public List<MenuAuthVO> getUserAuthList(Map<String, Object> map) throws Exception {
        return menuAuthDAO.getUserAuthList(map);
    }

    @Override
    public List<MenuAuthVO> getRoleList() throws Exception {
        return menuAuthDAO.getRoleList();
    }

    @Override
    public List<MenuAuthVO> getRoleListByUser(Map<String, Object> map) throws Exception {
        return menuAuthDAO.getRoleListByUser(map);
    }

    @Override
    public List<Map> getAuthMenuTree(Map<String, Object> map) throws Exception {
        return menuAuthDAO.getAuthMenuTree(map);
    }

    @Override
    public List<Map> getAuthMyMenuTree(Map<String, Object> map) throws Exception {
        return menuAuthDAO.getAuthMyMenuTree(map);
    }

    @Override
    public int getMenuAuthListCount(MenuAuthVO vo) throws Exception {
        return menuAuthDAO.getMenuAuthListCount(vo);
    }

    @Override
    @Transactional
    public void modifyMenuAuth(HashMap<String, Object> map) throws Exception {
        String idStr = (String) map.get("id");
        String menuCdStr = (String) map.get("menuArray");
        String[] menuCdArray = menuCdStr.split(",");

        this.delMenuAuth(map);

        for(String menuCd : menuCdArray){
            HashMap paramap = new HashMap();
            paramap.put("id", idStr);
            paramap.put("menuCd", menuCd);

            this.addMenuAuth(paramap);
        }
    }

    @Override
    @Transactional
    public void registMenuAuth(HashMap<String, Object> map) throws Exception {
        String menuCdStr = (String) map.get("menuArray");
        String[] menuCdArray = menuCdStr.split(",");

        this.addRole(map);

        for(String menuCd : menuCdArray){
            HashMap paramap = new HashMap();
            paramap.put("id", map.get("id"));
            paramap.put("menuCd", menuCd);

            this.addMenuAuth(paramap);
        }
    }

    @Override
    @Transactional
    public void deleteMenuAuth(HashMap<String, Object> map) throws Exception {
        this.delMenuAuth(map);
        this.delRole(map);
    }

    @Override
    public int addRole(HashMap<String, Object> map) throws Exception {
        return menuAuthDAO.addRole(map);
    }

    @Override
    public int delRole(HashMap<String, Object> map) throws Exception {
        return menuAuthDAO.delRole(map);
    }

    @Override
    public int addMenuAuth(HashMap<String, Object> map) throws Exception {
        return menuAuthDAO.addMenuAuth(map);
    }

    @Override
    public int delMenuAuth(HashMap<String, Object> map) throws Exception {
        return menuAuthDAO.delMenuAuth(map);
    }

    @Override
    public int addUserRole(HashMap<String, Object> map) throws Exception {
        return menuAuthDAO.addUserRole(map);
    }

    @Override
    public int delUserRole(HashMap<String, Object> map) throws Exception {
        return menuAuthDAO.delUserRole(map);
    }
}
