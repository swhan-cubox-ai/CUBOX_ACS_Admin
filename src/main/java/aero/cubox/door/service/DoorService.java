package aero.cubox.door.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DoorService {

    Map getDoorInformation(Map<String, Object> commandMap);

    List<Map> getDoorList(Map<String, Object> commandMap);

    List<Map> getAreaList(Map<String, Object> commandMap);

    List<Map> getBuildingList(Map<String, Object> commandMap);

    List<Map> getWorkplaceList(Map<String, Object> commandMap);

    List<HashMap> getDoorGroupList(Map<String, Object> commandMap);



    void addDoor(Map<String, Object> commandMap);

    void updateDoor(Map<String, Object> commandMap);

    void deleteDoorInformation(Map<String, Object> commandMap);

    HashMap getTerminal(Map<String, Object> commandMap);
    List<HashMap> getTerminalList(Map<String, Object> commandMap);

    List<HashMap> getScheduleList(Map<String, Object> commandMap);

    List<HashMap> getFloorList(Map<String, Object> commandMap);
}
