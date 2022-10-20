package aero.cubox.door.service.impl;

import aero.cubox.door.service.DoorAlarmService;
import aero.cubox.door.service.DoorService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("doorAlarmService")
public class DoorAlarmServiceImpl extends EgovAbstractServiceImpl implements DoorAlarmService {

    @Resource(name="doorAlarmDAO")
    private DoorAlarmDAO doorAlarmDAO;



    /**
     * 출입문 알람그룹 목록 조회
     * @param commandMap
     * @return
     */
    @Override
    public List<HashMap> getDoorAlarmGrpList(Map<String, Object> commandMap) {
        return  doorAlarmDAO.getDoorAlarmGrpList(commandMap);
    }

    /**
     * 출입문 알람그룹 상세
     * @param commandMap
     * @return
     */
    @Override
    public HashMap getDoorAlarmGrpDetail(Map<String, Object> commandMap) {
        return doorAlarmDAO.getDoorAlarmGrpDetail(commandMap);
    }

    /**
     * 출입문 알람그룹 등록
     * @param commandMap
     */
    @Override
    public void addDoorAlarmGrp(Map<String, Object> commandMap) {
        doorAlarmDAO.addDoorAlarmGrp(commandMap);
    }

    /**
     * 출입문 알람그룹 수정
     * @param commandMap
     */
    @Override
    public void updateDoorAlarmGrp(Map<String, Object> commandMap) {
        doorAlarmDAO.updateDoorAlarmGrp(commandMap);
    }

    /**
     * 출입문 알람그룹 삭제
     * @param commandMap
     */
    @Override
    public void deleteDoorAlarmGrp(Map<String, Object> commandMap) {
        doorAlarmDAO.deleteDoorAlarmGrp(commandMap);
    }


}
