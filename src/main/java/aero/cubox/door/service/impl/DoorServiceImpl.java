package aero.cubox.door.service.impl;

import aero.cubox.door.service.DoorService;
import aero.cubox.util.StringUtil;
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

    @Resource(name="doorGroupDAO")
    private DoorGroupDAO doorGroupDAO;

    /**
     * 출입문 목록 조회
     * @param paramMap
     * @return
     */
    @Override
    public List<Map> getDoorList(Map<String, Object> paramMap) {

        return doorDAO.getDoorList(paramMap);
    }

    /**
     * 출입문 정보 조회
     * @param paramMap
     * @return
     */
    @Override
    public Map getDoorDetail(Map<String, Object> paramMap) {

        return doorDAO.getDoorDetail(paramMap);
    }

    /**
     * 출입문 등록
     *
     * @param commandMap
     * @return
     */
    @Override
    public String addDoor(Map<String, Object> commandMap) {

        doorDAO.insertDoor(commandMap);

        String newDoorId = "";
        newDoorId = commandMap.get("doorId").toString();

        HashMap paramMap = new HashMap();

        if(!StringUtil.isEmpty(commandMap.get("doorId").toString() ) ){

            //도어그룹의 스케줄에 출입문 id Update
            if( !StringUtil.isEmpty((String) commandMap.get("doorGroupId"))){

                paramMap.put("doorId", newDoorId );
                paramMap.put("doorgrpId", commandMap.get("doorGroupId"));

                doorGroupDAO.addDoorInDoorGroup(paramMap);
            }

            //단말기정보에 출입문 id Update
            if( !StringUtil.isEmpty((String) commandMap.get("terminalIds"))){

                paramMap.put("doorId", newDoorId );
                paramMap.put("id", commandMap.get("terminalIds"));
                doorDAO.updateDoorIdForTerminal(paramMap);
            }

            //출입권한-출입문 table에 door_id Insert
            if( !StringUtil.isEmpty((String) commandMap.get("authGrIds"))){

                String authGrIds = "";
                authGrIds = commandMap.get("authGrIds").toString();

                if( authGrIds.length() > 0 ){
                    String[] authGrIdArr = authGrIds.split("/");
                    for (int i = 0; i < authGrIdArr.length; i++) {
                        paramMap.put("authId", authGrIdArr[i]);
                        paramMap.put("doorId", newDoorId );

                        doorDAO.insertDoorIdForAuthDoor(paramMap);
                    }
                }

            }
        }
        return newDoorId;
    }

    /**
     * 출입문 수정
     * @param commandMap
     */
    @Override
    public void updateDoor(Map<String, Object> commandMap) {
        doorDAO.updateDoor(commandMap);

        HashMap paramMap = new HashMap();

        
        //출입문그룹의 스케줄에 출입문 id - Insert or Update
        if( !StringUtil.isEmpty((String) commandMap.get("doorGroupId"))){

            paramMap.put("doorId", commandMap.get("id") );
            paramMap.put("doorgrpId", commandMap.get("doorGroupId"));
            doorGroupDAO.deleteDoorInDoorGroup(paramMap);
            doorGroupDAO.addDoorInDoorGroup(paramMap);
        }

        //단말기정보에 출입문 id Update
        if( !StringUtil.isEmpty((String) commandMap.get("terminalIds"))){
            paramMap.put("doorId", commandMap.get("id") );
            paramMap.put("id", commandMap.get("terminalIds"));
            doorDAO.updateDoorIdForTerminal(paramMap);
        }

        //출입권한-출입문 table에 door_id Delete-Insert
        if( !StringUtil.isEmpty((String) commandMap.get("authGrIds"))){

            paramMap.put("doorId", commandMap.get("id") );

            String authGrIds = "";
            authGrIds = commandMap.get("authGrIds").toString();

            if( authGrIds.length() > 0 ){
                String[] authGrIdArr = authGrIds.split("/");
                for (int i = 0; i < authGrIdArr.length; i++) {
                    paramMap.put("authId", authGrIdArr[i]);

                    doorDAO.deleteDoorIdForAuthDoor(paramMap);
                    doorDAO.insertDoorIdForAuthDoor(paramMap);
                }
            }
        }
    }

    /**
     * 출입문 삭제
     * @param commandMap
     */
    @Override
    public void deleteDoor(Map<String, Object> commandMap) {

        String doorId = commandMap.get("id").toString();

        HashMap paramMap = new HashMap();

        //단말기정보에 출입문 id - Update
        if( !StringUtil.isEmpty((String) commandMap.get("terminalIds"))){
            paramMap.put("doorId", doorId );
            paramMap.put("id", commandMap.get("terminalIds"));
            doorDAO.updateDoorIdForTerminal(paramMap);
        }

        //출입권한-출입문 table에 door_id Delete-Insert
        if( !StringUtil.isEmpty((String) commandMap.get("authGrIds"))){

            paramMap.put("doorId", doorId );
            paramMap.put("authId", commandMap.get("authGrIds"));

            doorDAO.deleteDoorIdForAuthDoor(paramMap);
        }

        doorDAO.deleteDoor(commandMap);
    }

   //Workplace
    /**
     * 사업장 목록 조회
     * @param paramMap
     * @return
     */
    @Override
    public List<HashMap> getWorkplaceList(Map<String, Object> paramMap) {

        return doorDAO.getWorkplaceList(paramMap);
    }


    //Building
    /**
     * 빌딩 목록 조회
     * @param paramMap
     * @return
     */
    @Override
    public List<HashMap> getBuildingList(Map<String, Object> paramMap) {

        return doorDAO.getBuildingList(paramMap);
    }
    @Override
    public Map getBuildingDetail(Map<String, Object> paramMap) {
        return doorDAO.getBuildingDetail(paramMap);
    }

    @Override
    public String addBuilding(HashMap paramMap) {
        doorDAO.insertBuilding(paramMap);

        String newBuildingId = "";
        newBuildingId = paramMap.get("buildingId").toString();

        paramMap.put("buildingId", newBuildingId);

        //출입권한-빌딩 table에 buildingId Insert
        if( !StringUtil.isEmpty((String) paramMap.get("authGrIds")) ){

            String authGrIds = "";
            authGrIds = paramMap.get("authGrIds").toString();

            if( authGrIds.length() > 0 ){
                String[] authGrIdArr = authGrIds.split("/");
                for (int i = 0; i < authGrIdArr.length; i++) {
                    paramMap.put("authId", authGrIdArr[i]);
                    paramMap.put("buildingId", newBuildingId );

                    doorDAO.insertDoorIdForAuthDoor(paramMap);
                }
            }
        }
        return newBuildingId;
    }

    /**
     * 출입문 수정
     * @param paramMap
     */
    @Override
    public void updateBuilding(Map<String, Object> paramMap) {
        doorDAO.updateBuilding(paramMap);
        //출입권한-출입문 table에 door_id Delete-Insert
        if( !StringUtil.isEmpty((String) paramMap.get("authGrIds"))){

            String authGrIds = "";
            authGrIds = paramMap.get("authGrIds").toString();

            if( authGrIds.length() > 0 ){
                String[] authGrIdArr = authGrIds.split("/");
                for (int i = 0; i < authGrIdArr.length; i++) {
                    paramMap.put("authId", authGrIdArr[i]);

                    doorDAO.deleteBuildingIdForAuthBuilding(paramMap);
                    doorDAO.insertBuildingIdForAuthBuilding(paramMap);
                }
            }
        }
    }

    @Override
    public void deleteBuilding(Map<String, Object> commandMap) {
        doorDAO.deleteBuilding(commandMap);
    }


    //Area

    /**
     * 구역 목록 조회
     * @param paramMap
     * @return
     */
    @Override
    public List<HashMap> getAreaList(Map<String, Object> paramMap) {

        return doorDAO.getAreaList(paramMap);
    }
    @Override
    public Map getAreaDetail(HashMap<String, Object> paramMap) {
        return doorDAO.getAreaDetail(paramMap);
    }


    @Override
    public String addArea(HashMap paramMap) {

        doorDAO.insertArea(paramMap);

        String newAreaId = "";
        newAreaId = paramMap.get("areaId").toString();
        return newAreaId;
    }

    @Override
    public void updateArea(Map<String, Object> paramMap) {

        doorDAO.updateArea(paramMap);
    }

    @Override
    public void deleteArea(Map<String, Object> commandMap) {
        doorDAO.deleteArea(commandMap);
    }



    //Floor
    /**
     * 층 목록
     * @param paramMap
     * @return
     */
    @Override
    public List<HashMap> getFloorList(Map<String, Object> paramMap) {

        return doorDAO.getFloorList(paramMap);
    }

    @Override
    public Map getFloorDetail(Map<String, Object> paramMap) {
        return doorDAO.getFloorDetail(paramMap);
    }
    @Override
    public String addFloor(HashMap paramMap) {
        doorDAO.insertFloor(paramMap);

        String newFloorId = "";
        newFloorId = paramMap.get("floorId").toString();

        return newFloorId;
    }

    @Override
    public void updateFloor(Map<String, Object> paramMap) {
        doorDAO.updateFloor(paramMap);
    }

    @Override
    public void deleteFloor(Map<String, Object> commandMap) {
        doorDAO.deleteFloor(commandMap);
    }

    @Override
    public int getTerminalUseCnt(HashMap<String, Object> param) {
        return doorDAO.getTerminalUseCnt(param);
    }

    @Override
    public int getBuildingNameVerification(HashMap<String, Object> param) {
        return doorDAO.getBuildingNameVerification(param);
    }
    @Override
    public int getAreaNameVerification(HashMap<String, Object> param) {
        return doorDAO.getAreaNameVerification(param);
    }
    @Override
    public int getFloorNameVerification(HashMap<String, Object> param) {
        return doorDAO.getFloorNameVerification(param);
    }
    @Override
    public int getDoorNameVerification(HashMap<String, Object> param) {
        return doorDAO.getDoorNameVerification(param);
    }

    /**
     * 단말기 목록 조회
     *
     * @param commandMap
     * @return
     */
    @Override
    public List<HashMap> getTerminalList(Map<String, Object> commandMap) {
        return doorDAO.getTerminalList(commandMap);
    }



}
