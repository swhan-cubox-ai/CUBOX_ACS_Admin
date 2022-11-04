package aero.cubox.door.service.impl;

import aero.cubox.door.service.DoorScheduleService;
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
    public List<HashMap> getDoorScheduleList(Map<String, Object> commandMap) {

        return doorScheduleDAO.getDoorScheduleList(commandMap);
    }

    @Override
    public int getDoorScheduleListCount(Map<String, Object> commandMap) {
        return doorScheduleDAO.getDoorScheduleListCount(commandMap);
    }

    /**
     * 출입문 스케줄 상세
     * @param id
     * @return
     */
    @Override
    public HashMap getDoorScheduleDetail(int id) {
        return  doorScheduleDAO.getDoorScheduleDetail(id);
    }

    /**
     * 출입문 스케줄 등록
     *
     * @param commandMap
     * @return
     */
    @Override
    public String addSchedule(Map<String, Object> commandMap) {
        String newScheduleId = "";
        doorScheduleDAO.addSchedule(commandMap);
        newScheduleId = commandMap.get("scheduleId").toString();
        return newScheduleId;
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
        HashMap paramMap = new HashMap();

        paramMap.put("doorSchId",  commandMap.get("id"));

        doorScheduleDAO.deleteScheduleByDay(paramMap);
        doorScheduleDAO.deleteSchedule(commandMap);

    }

    /**
     * 요일별 스게쥴 상세
     * @param commandMap
     * @return
     */
    @Override
    public List<HashMap> getScheduleByDayDetailList(Map<String, Object> commandMap) {
        return  doorScheduleDAO.getScheduleByDayDetailList(commandMap);
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
        doorScheduleDAO.deleteScheduleByDay(commandMap);
        doorScheduleDAO.addScheduleByDay(commandMap);
    }

    /**
     * 요일별 스케쥴 삭제
     * @param commandMap
     */
    @Override
    public void deleteScheduleByDay(Map<String, Object> commandMap) {
        doorScheduleDAO.deleteScheduleByDay(commandMap);
    }

    @Override
    public int getDoorScheduleNameVerification(HashMap<String, Object> param) {
        return doorScheduleDAO.getDoorScheduleNameVerification(param);
    }

    @Override
    public int getDayScheduleExistsCount(Map<String, Object> commandMap) {
        return doorScheduleDAO.getDayScheduleExistsCount(commandMap);
    }
}
