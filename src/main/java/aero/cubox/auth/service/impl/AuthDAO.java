package aero.cubox.auth.service.impl;

import aero.cubox.core.vo.AuthVO;
import aero.cubox.core.vo.DeptVO;
import aero.cubox.core.vo.EmpVO;
import aero.cubox.core.vo.FaceVO;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("authDAO")
public class AuthDAO extends EgovAbstractMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthDAO.class);
    private static final String sqlNameSpace = "auth.";


    public List<EmpVO> getEmpList(EmpVO vo) throws Exception {
        return selectList ("auth.getEmpList", vo);
    }

    public int getEmpListCount(EmpVO vo) throws Exception {
        return selectOne ("auth.getEmpListCount", vo);
    }

    public List<DeptVO> getDeptList(DeptVO vo) throws Exception {
        return selectList ("auth.getDeptList", vo);
    }

    public int getDeptListCount(DeptVO vo) throws Exception {
        return selectOne ("auth.getDeptListCount", vo);
    }

    public List<AuthVO> getAuthList(AuthVO vo) throws Exception {
        return selectList ("auth.getAuthList", vo);
    }

    public int getAuthListCount(AuthVO vo) throws Exception {
        return selectOne ("auth.getAuthListCount", vo);
    }

    public List<AuthVO> getAuthList2(AuthVO vo) throws Exception {
        return selectList ("auth.getAuthList2", vo);
    }

    public int getAuthListCount2(AuthVO vo) throws Exception {
        return selectOne ("auth.getAuthListCount2", vo);
    }

    public AuthVO getAuthDetail(int id) throws Exception {
        return selectOne ("auth.getAuthDetail", id);
    }

    public HashMap getEmpDetail(int id) throws Exception {
        return selectOne ("auth.getEmpDetail", id);
    }

    public HashMap getDoorDetail(int id) throws Exception {
        return selectOne ("auth.getDoorDetail", id);
    }

    public int modifyEmp(Map<String, Object> map) throws Exception {
        return insert ("auth.modifyEmp", map);
    }

    public int modifyDoor(Map<String, Object> map) throws Exception {
        return update ("auth.modifyDoor", map);
    }

    public int deleteEmp(Map<String, Object> map) throws Exception {
        return delete ("auth.deleteEmp", map);
    }

    public int delAuthEmp(Map<String, Object> map) throws Exception {
        return delete ("auth.delAuthEmp", map);
    }

    public int addAuthEmp(Map<String, Object> map) throws Exception {
        return insert ("auth.addAuthEmp", map);
    }

    public int addAuth(Map<String, Object> map) throws Exception {
        return insert ("auth.addAuth", map);
    }

    public int addAuthBuilding(Map<String, Object> map) throws Exception {
        return insert ("auth.addAuthBuilding", map);
    }

    public int addAuthDoorGrp(Map<String, Object> map) throws Exception {
        return insert ("auth.addAuthDoorGrp", map);
    }

    public List<Map> getAuthEntMyList(int id) throws Exception {
        return selectList ("auth.getAuthEntMyList", id);
    }

    public List<Map> getAuthEntAllList() throws Exception {
        return selectList ("auth.getAuthEntAllList");
    }

    public List<Map> getBuildingList() throws Exception {
        return selectList ("auth.getBuildingList");
    }

    public List<Map> getAuthBuildingItem(int id) throws Exception {
        return selectList ("auth.getAuthBuildingItem", id);
    }

    public List<Map> getAuthDoorGrpItem(int id) throws Exception {
        return selectList ("auth.getAuthDoorGrpItem", id);
    }

    public List<Map> getAuthDoorItem(int id) throws Exception {
        return selectList ("auth.getAuthDoorItem", id);
    }

    public List<Map> getDoorGrpList(Map map) throws Exception {
        return selectList ("auth.getDoorGrpList", map);
    }

    public List<Map> getDoorList(int id) throws Exception {
        return selectList ("auth.getDoorList", id);
    }

    public FaceVO selectFaceOne(String empCd) throws Exception {
        return  selectOne ( "auth.selectFaceOne", empCd);
    }

    public List<Map> getEmpSourceList(Map map) throws Exception {
        return selectList ("auth.getEmpSourceList", map);
    }

    public List<Map> getEmpTargetList(Map map) throws Exception {
        return selectList ("auth.getEmpTargetList", map);
    }

    public int delAuth(Map map) throws Exception {
        return delete ("auth.delAuth", map);
    }
}
