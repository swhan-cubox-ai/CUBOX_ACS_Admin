package aero.cubox.door.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DoorService {

    //출입문
    List<Map> getDoorList(Map<String, Object> commandMap);

    Map getDoorDetail(Map<String, Object> commandMap);

    String addDoor(Map<String, Object> commandMap);

    void updateDoor(Map<String, Object> commandMap);

    void deleteDoor(Map<String, Object> commandMap);

    List<HashMap> getAreaList(Map<String, Object> commandMap);

    List<HashMap> getBuildingList(Map<String, Object> commandMap);

    List<HashMap> getWorkplaceList(Map<String, Object> commandMap);

    List<HashMap> getFloorList(Map<String, Object> commandMap);

    //단말기
    List<HashMap> getTerminalList(Map<String, Object> commandMap);


    String addBuilding(HashMap param);

    void updateBuilding(Map<String, Object> commandMap);

    String addArea(HashMap param);

    void updateArea(Map<String, Object> commandMap);

    String addFloor(HashMap param);

    void updateFloor(Map<String, Object> commandMap);


}
