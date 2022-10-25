package aero.cubox.door.service.impl;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("doorAlarmDAO")
public class DoorAlarmDAO extends EgovAbstractMapper {

    private static final String sqlNameSpace = "door.alarm.";

    public List<HashMap> getDoorAlarmGrpList(Map<String, Object> paramMap) {
        return selectList(sqlNameSpace+"selectDoorAlarmGrpList", paramMap);
    }

    public int getDoorAlarmGrpListCount(Map<String, Object> paramMap) {
        return selectOne(sqlNameSpace+"selectDoorAlarmGrpListCount", paramMap);
    }

    public HashMap getDoorAlarmGrpDetail(int id) {
        return selectOne(sqlNameSpace+"selectDoorAlarmGrpDetail", id);
    }

    public void addDoorAlarmGrp(Map<String, Object> paramMap) {
        insert(sqlNameSpace+"insertDoorAlarmGrp", paramMap);
    }

    public void updateDoorAlarmGrp(Map<String, Object> paramMap) {
        update(sqlNameSpace+"updateDoorAlarmGrp", paramMap);
    }

    public void deleteDoorAlarmGrp(int id) {
        delete(sqlNameSpace+"deleteDoorAlarmGrp", id);
    }


}
