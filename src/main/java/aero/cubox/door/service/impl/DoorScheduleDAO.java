package aero.cubox.door.service.impl;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("doorScheduleDAO")
public class DoorScheduleDAO extends EgovAbstractMapper {

    private static final String sqlNameSpace = "door.schedule.";


    public List<HashMap> getScheduleList(Map<String, Object> paramMap) {
        return selectList(sqlNameSpace+"selectDoorSchList", paramMap);
    }

    public HashMap getScheduleDetail(Map<String, Object> paramMap) {
        return selectOne(sqlNameSpace+"selectScheduleDetail", paramMap);
    }

    public void addSchedule(Map<String, Object> paramMap) {

        insert(sqlNameSpace+"insertSchedule", paramMap);
    }

    public void updateSchedule(Map<String, Object> paramMap) {

        update(sqlNameSpace+"updateSchedule", paramMap);
    }

    public void deleteSchedule(Map<String, Object> paramMap) {

        delete(sqlNameSpace+"deleteSchedule", paramMap);
    }

    public HashMap getScheduleByDayDetail(Map<String, Object> paramMap) {
        return selectOne(sqlNameSpace+"selectScheduleByDayDetail", paramMap);
    }

    public void addScheduleByDay(Map<String, Object> paramMap) {

        insert(sqlNameSpace+"insertScheduleByDay", paramMap);
    }

    public void updateScheduleByDay(Map<String, Object> paramMap) {

        update(sqlNameSpace+"updateScheduleByDay", paramMap);
    }

    public void deleteScheduleByDay(Map<String, Object> paramMap) {

        delete(sqlNameSpace+"deleteScheduleByDay", paramMap);
    }



}
