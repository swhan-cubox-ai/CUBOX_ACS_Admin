package aero.cubox.door.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DoorService {

    List<Map> getDoorList(Map<String, Object> commandMap);

    Map getDoorDetail(Map<String, Object> commandMap);

    void addDoor(Map<String, Object> commandMap);

    void updateDoor(Map<String, Object> commandMap);

    void deleteDoor(Map<String, Object> commandMap);



    List<Map> getAreaList(Map<String, Object> commandMap);

    List<Map> getBuildingList(Map<String, Object> commandMap);

    List<Map> getWorkplaceList(Map<String, Object> commandMap);

    List<HashMap> getFloorList(Map<String, Object> commandMap);


    //출입문
    List<HashMap> getDoorGroupList(Map<String, Object> commandMap);

    HashMap getDoorGroupDetail(Map<String, Object> commandMap);

    void addDoorGroup(Map<String, Object> commandMap);
    void updateDoorGroup(Map<String, Object> commandMap);
    void deleteDoorGroup(Map<String, Object> commandMap);

    //출입문 스케쥴
    List<HashMap> getScheduleList(Map<String, Object> commandMap);

    HashMap getScheduleDetail(Map<String, Object> commandMap);

    void addSchedule(Map<String, Object> commandMap);
    void updateSchedule(Map<String, Object> commandMap);
    void deleteSchedule(Map<String, Object> commandMap);

    //요일별 스케쥴
    HashMap getScheduleByDayDetail(Map<String, Object> commandMap);
    void addScheduleByDay(Map<String, Object> commandMap);
    void updateScheduleByDay(Map<String, Object> commandMap);
    void deleteScheduleByDay(Map<String, Object> commandMap);


    //단말기
    List<HashMap> getTerminalList(Map<String, Object> commandMap);

}
