package aero.cubox.door.service.impl;

import aero.cubox.door.service.DoorService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("doorService")
public class DoorServiceImpl extends EgovAbstractServiceImpl implements DoorService {

    @Resource(name="doorDAO")
    private DoorDAO doorDAO;

    /**
     * 출입문 목록 조회
     * @param commandMap
     * @return
     */
    @Override
    public List<Map> getDoorList(Map<String, Object> commandMap) {

        return doorDAO.getDoorList(commandMap);
    }

    /**
     * 출입문 정보 조회
     * @param commandMap
     * @return
     */
    @Override
    public Map getDoorInformation(Map<String, Object> commandMap) {

        return doorDAO.getDoorInformation(commandMap);
    }

    /**
     * 사업장 목록 조회
     * @param commandMap
     * @return
     */
    @Override
    public List<Map> getWorkplaceList(Map<String, Object> commandMap) {

        return doorDAO.getWorkplaceList(commandMap);
    }

    /**
     * 구역 목록 조회
     * @param commandMap
     * @return
     */
    @Override
    public List<Map> getAreaList(Map<String, Object> commandMap) {

        return doorDAO.getAreaList(commandMap);
    }

    /**
     * 빌딩 목록 조회
     * @param commandMap
     * @return
     */
    @Override
    public List<Map> getBuildingList(Map<String, Object> commandMap) {

        return doorDAO.getBuildingList(commandMap);
    }



    /**
     * 권한 그룹 검색
     * @param commandMap
     * @return
     */
    @Override
    public List<HashMap> searchPermissionGroup(Map<String, Object> commandMap) {

        return doorDAO.getPermissionGroups(commandMap);
    }

    /**
     * 권한 그룹 조회
     * @param commandMap
     * @return
     */
    @Override
    public List<HashMap> getPermissionGroups(Map<String, Object> commandMap) {

        return doorDAO.getPermissionGroups(commandMap);
    }

    /**
     * 단말기 검색
     * @param commandMap
     * @return
     */
    @Override
    public List<HashMap> searchTerminalInformation(Map<String, Object> commandMap) {

        return doorDAO.searchTerminalInformation(commandMap);
    }


    @Override
    public void addDoorInformation(Map<String, Object> commandMap) {
        doorDAO.insertDoorInformation(commandMap);
    }

    @Override
    public List<HashMap> getScheduleList(Map<String, Object> commandMap) {
        return null;
    }


    @Override
    public void updateDoorInformation(Map<String, Object> commandMap) {
        doorDAO.updateDoorInformation(commandMap);

    }

    /**
     * 출입문 삭제
     * @param commandMap
     */
    @Override
    public void deleteDoor(Map<String, Object> commandMap) {
        doorDAO.deleteDoor(commandMap);
    }


    /**
     * 단말기 정보 조회
     * @param commandMap
     * @return
     */
    @Override
    public HashMap getTerminalInformation(Map<String, Object> commandMap) {
        return doorDAO.getTerminalInformation(commandMap);
    }



}
