package aero.cubox.door.service.impl;

import aero.cubox.door.service.DoorScheduleService;
import aero.cubox.door.service.DoorService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("doorScheduleService")
public class DoorScheduleServiceImpl extends EgovAbstractServiceImpl implements DoorScheduleService {

    @Resource(name="doorScheduleDAO")
    private DoorScheduleDAO doorScheduleDAO;

    /**
     * 출입문 스케줄 목록 조회
     * @param commandMap
     * @return
     */
    @Override
    public List<HashMap> getScheduleList(Map<String, Object> commandMap) {

        return doorScheduleDAO.getScheduleList(commandMap);
    }

    @Override
    public int getScheduleListCount(Map<String, Object> commandMap) {
        return doorScheduleDAO.getScheduleListCount(commandMap);
    }

    /**
     * 출입문 스케줄 상세
     * @param commandMap
     * @return
     */
    @Override
    public HashMap getScheduleDetail(Map<String, Object> commandMap) {
        return  doorScheduleDAO.getScheduleDetail(commandMap);
    }

    /**
     * 출입문 스케줄 등록
     * @param commandMap
     */
    @Override
    public void addSchedule(Map<String, Object> commandMap) {
        doorScheduleDAO.addSchedule(commandMap);
    }

    /**
     * 출입문 스케쥴 수정
     * @param commandMap
     */
    @Override
    public void updateSchedule(Map<String, Object> commandMap) {
        doorScheduleDAO.updateSchedule(commandMap);
    }

    /**
     * 출입문 스케쥴 삭제
     * @param commandMap
     */
    @Override
    public void deleteSchedule(Map<String, Object> commandMap) {
        doorScheduleDAO.deleteSchedule(commandMap);
    }

    /**
     * 요일별 스게쥴 상세
     * @param commandMap
     * @return
     */
    @Override
    public HashMap getScheduleByDayDetail(Map<String, Object> commandMap) {
        return  doorScheduleDAO.getScheduleByDayDetail(commandMap);
    }

    /**
     * 요일별 스케쥴 등록
     * @param commandMap
     */
    @Override
    public void addScheduleByDay(Map<String, Object> commandMap) {
        doorScheduleDAO.addScheduleByDay(commandMap);
    }

    /**
     * 요일별 스케쥴 수정
     * @param commandMap
     */
    @Override
    public void updateScheduleByDay(Map<String, Object> commandMap) {
        doorScheduleDAO.updateScheduleByDay(commandMap);
    }

    /**
     * 요일별 스케쥴 삭제
     * @param commandMap
     */
    @Override
    public void deleteScheduleByDay(Map<String, Object> commandMap) {
        doorScheduleDAO.deleteScheduleByDay(commandMap);
    }
}
