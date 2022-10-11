package aero.cubox.auth.service;

import aero.cubox.core.vo.AuthVO;
import aero.cubox.core.vo.DeptVO;
import aero.cubox.core.vo.EmpVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AuthService {


    public List<EmpVO> getEmpList(EmpVO vo) throws Exception;
    public int getEmpListCount(EmpVO vo) throws Exception;

    public List<DeptVO> getDeptList(DeptVO vo) throws Exception;
    public int getDeptListCount(DeptVO vo) throws Exception;

    public List<AuthVO> getAuthList(AuthVO vo) throws Exception;
    public int getAuthListCount(AuthVO vo) throws Exception;


    public HashMap getEmpDetail(int id) throws Exception;

    public HashMap getDoorDetail(int id) throws Exception;

    public void modifyEmp(Map<String, Object> map) throws Exception;

    public int modifyDoor(Map<String, Object> map) throws Exception;

    public int deleteEmp(Map<String, Object> map) throws Exception;

    public int delAuthEmp(Map<String, Object> map) throws Exception;

    public int addAuthEmp(Map<String, Object> map) throws Exception;

    public int addAuth(Map<String, Object> map) throws Exception;

    public int addAuthBuilding(Map<String, Object> map) throws Exception;

    public int addAuthDoorGrp(Map<String, Object> map) throws Exception;

    public List<Map> getAuthEntMyList(int id) throws Exception;

    public List<Map> getDoorList(int id) throws Exception;

    public List<Map> getAuthEntAllList() throws Exception;

    public List<Map> getBuildingList() throws Exception;

    public List<Map> getAuthBuildingItem(int id) throws Exception;

    public List<Map> getAuthDoorGrpItem(int id) throws Exception;

    public List<Map> getAuthDoorItem(int id) throws Exception;

    public List<Map> getDoorGrpList(Map map) throws Exception;

    public void registAuthDoor(Map<String, Object> map) throws Exception;
}
