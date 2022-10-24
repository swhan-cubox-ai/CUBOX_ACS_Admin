package aero.cubox.door.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DoorScheduleService {



    //출입문 스케쥴
    List<HashMap> getScheduleList(Map<String, Object> commandMap);


    int getScheduleListCount(Map<String, Object> commandMap);

    HashMap getScheduleDetail(Map<String, Object> commandMap);

    void addSchedule(Map<String, Object> commandMap);
    void updateSchedule(Map<String, Object> commandMap);
    void deleteSchedule(Map<String, Object> commandMap);

    //요일별 스케쥴
    HashMap getScheduleByDayDetail(Map<String, Object> commandMap);
    void addScheduleByDay(Map<String, Object> commandMap);
    void updateScheduleByDay(Map<String, Object> commandMap);
    void deleteScheduleByDay(Map<String, Object> commandMap);


}
