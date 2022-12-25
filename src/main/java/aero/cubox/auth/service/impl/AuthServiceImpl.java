package aero.cubox.auth.service.impl;


import aero.cubox.auth.service.AuthService;
import aero.cubox.core.vo.AuthVO;
import aero.cubox.core.vo.DeptVO;
import aero.cubox.core.vo.EmpVO;
import aero.cubox.core.vo.FaceVO;
import aero.cubox.util.StringUtil;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("authService")
public class AuthServiceImpl extends EgovAbstractServiceImpl implements AuthService {

    @Resource(name="authDAO")
    private AuthDAO authDAO;


    @Override
    public List<EmpVO> getEmpList(EmpVO vo) throws Exception {
        return authDAO.getEmpList(vo);
    }

    @Override
    public int getEmpListCount(EmpVO vo) throws Exception {
        return authDAO.getEmpListCount(vo);
    }

    @Override
    public List<DeptVO> getDeptList(DeptVO vo) throws Exception {
        return authDAO.getDeptList(vo);
    }

    @Override
    public int getDeptListCount(DeptVO vo) throws Exception {
        return authDAO.getDeptListCount(vo);
    }

    @Override
    public List<AuthVO> getAuthList(AuthVO vo) throws Exception {
        return authDAO.getAuthList(vo);
    }

    @Override
    public int getAuthListCount(AuthVO vo) throws Exception {
        return authDAO.getAuthListCount(vo);
    }

    @Override
    public List<AuthVO> getAuthList2(AuthVO vo) throws Exception {
        return authDAO.getAuthList2(vo);
    }

    @Override
    public int getAuthListCount2(AuthVO vo) throws Exception {
        return authDAO.getAuthListCount2(vo);
    }

    @Override
    public AuthVO getAuthDetail(int id) throws Exception {
        return authDAO.getAuthDetail(id);
    }

    @Override
    public HashMap getEmpDetail(int id) throws Exception {
        return authDAO.getEmpDetail(id);
    }

    @Override
    public HashMap getDoorDetail(int id) throws Exception {
        return authDAO.getDoorDetail(id);
    }

    @Override
    @Transactional
    public void modifyEmp(Map<String, Object> map) throws Exception {
        String isModAuthEnt = (String) map.get("isModAuthEnt");
        String authEntStr = (String) map.get("authEntStr");

        if("Y".equals(isModAuthEnt)){
            String[] authEntArray =authEntStr.split(",");
            this.delAuthEmp(map);
            if(!"nan".equals(authEntStr)){
                for(int i=0;i<authEntArray.length;i++){
                    map.put("authId", authEntArray[i]);
                    this.addAuthEmp(map);
                }
            }
        }
    }

    @Override
    public int modifyDoor(Map<String, Object> map) throws Exception {
        return authDAO.modifyDoor(map);
    }

    @Override
    public int deleteEmp(Map<String, Object> map) throws Exception {
        return authDAO.deleteEmp(map);
    }

    @Override
    public int delAuthEmp(Map<String, Object> map) throws Exception {
        return authDAO.delAuthEmp(map);
    }

    @Override
    public int addAuthEmp(Map<String, Object> map) throws Exception {
        return authDAO.addAuthEmp(map);
    }

    @Override
    public int addAuth(Map<String, Object> map) throws Exception {
        return authDAO.addAuth(map);
    }

    @Override
    public int addAuthBuilding(Map<String, Object> map) throws Exception {
        return authDAO.addAuthBuilding(map);
    }

    @Override
    public int addAuthDoorGrp(Map<String, Object> map) throws Exception {
        return authDAO.addAuthDoorGrp(map);
    }

    @Override
    public List<Map> getAuthEntMyList(int id) throws Exception {
        return authDAO.getAuthEntMyList(id);
    }

    @Override
    public List<Map> getDoorList(int id) throws Exception {
        return authDAO.getDoorList(id);
    }

    @Override
    public List<Map> getAuthEntAllList() throws Exception {
        return authDAO.getAuthEntAllList();
    }

    @Override
    public List<Map> getBuildingList() throws Exception {
        return authDAO.getBuildingList();
    }

    @Override
    public List<Map> getAuthBuildingItem(int id) throws Exception {
        return authDAO.getAuthBuildingItem(id);
    }

    @Override
    public List<Map> getAuthDoorGrpItem(int id) throws Exception {
        return authDAO.getAuthDoorGrpItem(id);
    }

    @Override
    public List<Map> getAuthDoorItem(int id) throws Exception {
        return authDAO.getAuthDoorItem(id);
    }

    @Override
    public List<Map> getDoorGrpList(Map map) throws Exception {
        return authDAO.getDoorGrpList(map);
    }

    @Override
    @Transactional
    public void registAuthDoor(Map<String, Object> map) throws Exception {
        String rdoAuthTyp = (String) map.get("rdoAuthTyp");
        String authItemStr = (String) map.get("authItemArray");
        String[] authItemArray =authItemStr.split(",");

        map.put("deptAuthYn", "N");
        //map.put("deptCd", "");

        this.addAuth(map);

        if(authItemArray.length > 0 && ! StringUtil.isEmpty(authItemArray[0])){
            for(int i=0;i<authItemArray.length;i++){
                map.put("item", authItemArray[i]);

                if("EAT001".equals(rdoAuthTyp)){
                    this.addAuthBuilding(map);
                }else if("EAT002".equals(rdoAuthTyp)){
                    this.addAuthDoorGrp(map);
                }else{
                    // todo
                }
            }
        }
    }

    @Override
    public FaceVO selectFaceOne(String empCd) throws Exception {
        return authDAO.selectFaceOne(empCd);
    }

    @Override
    public List<Map> getEmpSourceList(Map map) throws Exception {
        return authDAO.getEmpSourceList(map);
    }

    @Override
    public List<Map> getEmpTargetList(Map map) throws Exception {
        return authDAO.getEmpTargetList(map);
    }

    @Override
    @Transactional
    public void assignAuthEmp(HashMap<String, Object> map) throws Exception {
        String targetEmpAStr = (String) map.get("targetEmpArray");
        String[] targetEmpArray = targetEmpAStr.split(",");

        this.delAuth(map);

        if(targetEmpArray.length > 0 && ! StringUtil.isEmpty(targetEmpArray[0])){
            for(String empId : targetEmpArray){
                HashMap<String, Object> param = new HashMap<String, Object>();
                param.put("authId", map.get("authId"));
                param.put("id", empId);

                this.addAuthEmp(param);
            }
        }
    }

    @Override
    public int delAuth(HashMap<String, Object> map) throws Exception {
        return authDAO.delAuth(map);
    }

}
