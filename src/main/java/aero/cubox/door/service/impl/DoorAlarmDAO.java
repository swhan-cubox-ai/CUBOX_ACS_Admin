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

    public HashMap getDoorAlarmGrpDetail(Map<String, Object> paramMap) {
        return selectOne(sqlNameSpace+"selectDoorAlarmGrpDetail", paramMap);
    }

    public void addDoorAlarmGrp(Map<String, Object> paramMap) {
        insert(sqlNameSpace+"insertDoorAlarmGrp", paramMap);
    }

    public void updateDoorAlarmGrp(Map<String, Object> paramMap) {
        update(sqlNameSpace+"updateDoorAlarmGrp", paramMap);
    }

    public void deleteDoorAlarmGrp(Map<String, Object> paramMap) {
        delete(sqlNameSpace+"deleteDoorAlarmGrp", paramMap);
    }

}
