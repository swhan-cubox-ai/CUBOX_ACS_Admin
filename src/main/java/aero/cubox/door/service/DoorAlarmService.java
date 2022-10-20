package aero.cubox.door.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DoorAlarmService {

    //출입문 알람 그룹
    List<HashMap> getDoorAlarmGrpList(Map<String, Object> commandMap);
    HashMap getDoorAlarmGrpDetail(Map<String, Object> commandMap);
    void addDoorAlarmGrp(Map<String, Object> commandMap);
    void updateDoorAlarmGrp(Map<String, Object> commandMap);
    void deleteDoorAlarmGrp(Map<String, Object> commandMap);
}
