package aero.cubox.door.service.impl;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("doorGroupDAO")
public class DoorGroupDAO extends EgovAbstractMapper {

    private static final String sqlNameSpace = "door.group.";

    public List<HashMap> getDoorGroupList(Map<String, Object> paramMap) {
        return selectList(sqlNameSpace+"selectDoorGroupList", paramMap);
    }

    public int getDoorGroupListCount(Map<String, Object> paramMap) {
        return selectOne (sqlNameSpace+"selectDoorGroupListCount", paramMap);
    }


    public HashMap getDoorGroupDetail(int id) {
        return selectOne(sqlNameSpace+"selectDoorGroupDetail", id);
    }

    public void addDoorGroup(Map<String, Object> paramMap) {

        insert(sqlNameSpace+"insertDoorGroup", paramMap);
    }

    public void updateDoorGroup(Map<String, Object> paramMap) {

        update(sqlNameSpace+"updateDoorGroup", paramMap);
    }

    public void deleteDoorGroup(int id) {

        delete(sqlNameSpace+"deleteDoorGroup", id);
    }

}
