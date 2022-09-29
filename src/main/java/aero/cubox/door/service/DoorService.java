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

    List<HashMap> searchPermissionGroup(Map<String, Object> commandMap);

    List<HashMap> getPermissionGroups(Map<String, Object> commandMap);

    List<HashMap> searchTerminalInformation(Map<String, Object> commandMap);



    void addDoorInformation(Map<String, Object> commandMap);

    void updateDoorInformation(Map<String, Object> commandMap);

    void deleteDoor(Map<String, Object> commandMap);



    HashMap getTerminalInformation(Map<String, Object> commandMap);

    List<HashMap> getScheduleList(Map<String, Object> commandMap);
}
