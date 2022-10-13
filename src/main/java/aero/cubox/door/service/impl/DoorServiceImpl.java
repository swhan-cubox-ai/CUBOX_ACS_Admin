package aero.cubox.door.service.impl;

import aero.cubox.door.service.DoorService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("doorService")
public class DoorServiceImpl extends EgovAbstractServiceImpl implements DoorService {

    @Resource(name="doorDAO")
    private DoorDAO doorDAO;

    /**
     * 출입문 목록 조회
     * @param commandMap
     * @return
     */
    @Override
    public List<Map> getDoorList(Map<String, Object> commandMap) {

        return doorDAO.getDoorList(commandMap);
    }

    /**
     * 출입문 정보 조회
     * @param commandMap
     * @return
     */
    @Override
    public Map getDoorDetail(Map<String, Object> commandMap) {

        return doorDAO.getDoorDetail(commandMap);
    }

    /**
     * 출입문 등록
     * @param commandMap
     */
    @Override
    public void addDoor(Map<String, Object> commandMap) {
        doorDAO.insertDoor(commandMap);
    }

    /**
     * 출입문 수정
     * @param commandMap
     */
    @Override
    public void updateDoor(Map<String, Object> commandMap) {
        doorDAO.updateDoor(commandMap);

    }

    /**
     * 출입문 삭제
     * @param commandMap
     */
    @Override
    public void deleteDoor(Map<String, Object> commandMap) {
        doorDAO.deleteDoor(commandMap);
    }

    /**
     * 사업장 목록 조회
     * @param commandMap
     * @return
     */
    @Override
    public List<HashMap> getWorkplaceList(Map<String, Object> commandMap) {

        return doorDAO.getWorkplaceList(commandMap);
    }

    /**
     * 구역 목록 조회
     * @param commandMap
     * @return
     */
    @Override
    public List<HashMap> getAreaList(Map<String, Object> commandMap) {

        return doorDAO.getAreaList(commandMap);
    }

    /**
     * 층 목록
     * @param commandMap
     * @return
     */
    @Override
    public List<HashMap> getFloorList(Map<String, Object> commandMap) {
        return doorDAO.getFloorList(commandMap);
    }

    /**
     * 빌딩 목록 조회
     * @param commandMap
     * @return
     */
    @Override
    public List<HashMap> getBuildingList(Map<String, Object> commandMap) {

        return doorDAO.getBuildingList(commandMap);
    }



    /**
     * 출입문 그룹 검색
     * @param commandMap
     * @return
     */
    @Override
    public List<HashMap> getDoorGroupList(Map<String, Object> commandMap) {

        return doorDAO.getDoorGroupList(commandMap);
    }

    /**
     * 출압문 그룹 상세
     * @param commandMap
     * @return
     */
    @Override
    public HashMap getDoorGroupDetail(Map<String, Object> commandMap) {
        return  doorDAO.getDoorGroupDetail(commandMap);
    }

    /**
     * 출입문 그룹 추가
     * @param commandMap
     */
    @Override
    public void addDoorGroup(Map<String, Object> commandMap) {
        doorDAO.addDoorGroup(commandMap);
    }

    /**
     * 출입문 그룹 수정
     * @param commandMap
     */
    @Override
    public void updateDoorGroup(Map<String, Object> commandMap) {
        doorDAO.updateDoorGroup(commandMap);
    }

    /**
     * 출입문 그룹 삭제
     * @param commandMap
     */
    @Override
    public void deleteDoorGroup(Map<String, Object> commandMap) {
        doorDAO.deleteDoorGroup(commandMap);
    }

    /**
     * 출입문 스케줄 목록 조회
     * @param commandMap
     * @return
     */
    @Override
    public List<HashMap> getScheduleList(Map<String, Object> commandMap) {

        return doorDAO.getScheduleList(commandMap);
    }

    /**
     * 출입문 스케줄 상세
     * @param commandMap
     * @return
     */
    @Override
    public HashMap getScheduleDetail(Map<String, Object> commandMap) {
        return  doorDAO.getScheduleDetail(commandMap);
    }

    /**
     * 출입문 스케줄 등록
     * @param commandMap
     */
    @Override
    public void addSchedule(Map<String, Object> commandMap) {
        doorDAO.addSchedule(commandMap);
    }

    /**
     * 출입문 스케쥴 수정
     * @param commandMap
     */
    @Override
    public void updateSchedule(Map<String, Object> commandMap) {
        doorDAO.updateSchedule(commandMap);
    }

    /**
     * 출입문 스케쥴 삭제
     * @param commandMap
     */
    @Override
    public void deleteSchedule(Map<String, Object> commandMap) {
        doorDAO.deleteSchedule(commandMap);
    }

    /**
     * 요일별 스게쥴 상세
     * @param commandMap
     * @return
     */
    @Override
    public HashMap getScheduleByDayDetail(Map<String, Object> commandMap) {
        return  doorDAO.getScheduleByDayDetail(commandMap);
    }

    /**
     * 요일별 스케쥴 등록
     * @param commandMap
     */
    @Override
    public void addScheduleByDay(Map<String, Object> commandMap) {
        doorDAO.addScheduleByDay(commandMap);
    }

    /**
     * 요일별 스케쥴 수정
     * @param commandMap
     */
    @Override
    public void updateScheduleByDay(Map<String, Object> commandMap) {
        doorDAO.updateScheduleByDay(commandMap);
    }

    /**
     * 요일별 스케쥴 삭제
     * @param commandMap
     */
    @Override
    public void deleteScheduleByDay(Map<String, Object> commandMap) {
        doorDAO.deleteScheduleByDay(commandMap);
    }

    /**
     * 출입문 알람그룹 목록 조회
     * @param commandMap
     * @return
     */
    @Override
    public List<HashMap> getDoorAlarmGrpList(Map<String, Object> commandMap) {
        return  doorDAO.getDoorAlarmGrpList(commandMap);
    }

    /**
     * 출입문 알람그룹 상세
     * @param commandMap
     * @return
     */
    @Override
    public HashMap getDoorAlarmGrpDetail(Map<String, Object> commandMap) {
        return doorDAO.getDoorAlarmGrpDetail(commandMap);
    }

    /**
     * 출입문 알람그룹 등록
     * @param commandMap
     */
    @Override
    public void addDoorAlarmGrp(Map<String, Object> commandMap) {
        doorDAO.addDoorAlarmGrp(commandMap);
    }

    /**
     * 출입문 알람그룹 수정
     * @param commandMap
     */
    @Override
    public void updateDoorAlarmGrp(Map<String, Object> commandMap) {
        doorDAO.updateDoorAlarmGrp(commandMap);
    }

    /**
     * 출입문 알람그룹 삭제
     * @param commandMap
     */
    @Override
    public void deleteDoorAlarmGrp(Map<String, Object> commandMap) {
        doorDAO.deleteDoorAlarmGrp(commandMap);
    }


    /**
     * 단말기 목록 조회
     *
     * @param commandMap
     * @return
     */
    @Override
    public List<HashMap> getTerminalList(Map<String, Object> commandMap) {
        return doorDAO.getTerminalList(commandMap);
    }


}
