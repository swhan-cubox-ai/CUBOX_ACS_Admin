package aero.cubox.door.service.impl;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("doorDAO")
public class DoorDAO extends EgovAbstractMapper {

	private static final String sqlNameSpace = "door.";


	public List<Map> getDoorList(Map<String, Object> commandMap) {
		return selectList(sqlNameSpace+"selectDoorList", commandMap);
	}

	public Map getDoorDetail(Map<String, Object> commandMap) {
		return selectOne(sqlNameSpace+"selectDoorDetail", commandMap);
	}


	public void insertDoor(Map<String, Object> commandMap) {
		insert(sqlNameSpace+"insertDoor", commandMap);
	}

	public void updateDoor(Map<String, Object> commandMap) {
		update(sqlNameSpace+"updateDoor", commandMap);
	}
	public void deleteDoor(Map<String, Object> commandMap) {
		delete(sqlNameSpace+"deleteDoor", commandMap);
	}


	public List<Map> getAreaList(Map<String, Object> commandMap) {
		return selectList(sqlNameSpace+"selectAreaList", commandMap);
	}

	public List<Map> getBuildingList(Map<String, Object> commandMap) {
		return selectList(sqlNameSpace+"selectBuildingList", commandMap);
	}

	public List<Map> getWorkplaceList(Map<String, Object> commandMap) {
		return selectList(sqlNameSpace+"selectWorkplaceList", commandMap);
	}

	public List<HashMap> getFloorList(Map<String, Object> commandMap) {
		return selectList(sqlNameSpace+"selectFloorList", commandMap);
	}

	public List<HashMap> getDoorGroupList(Map<String, Object> commandMap) {
		return selectList(sqlNameSpace+"selectDoorGroupList", commandMap);
	}


	public HashMap getDoorGroupDetail(Map<String, Object> commandMap) {
		return selectOne(sqlNameSpace+"selectDoorGroupDetail", commandMap);
	}

	public void addDoorGroup(Map<String, Object> commandMap) {
		insert(sqlNameSpace+"insertDoorGroup", commandMap);
	}

	public void updateDoorGroup(Map<String, Object> commandMap) {
		update(sqlNameSpace+"updateDoorGroup", commandMap);
	}

	public void deleteDoorGroup(Map<String, Object> commandMap) {
		delete(sqlNameSpace+"deleteDoorGroup", commandMap);
	}



	public List<HashMap> getScheduleList(Map<String, Object> commandMap) {
		return selectList(sqlNameSpace+"selectDoorSchList", commandMap);
	}

	public HashMap getScheduleDetail(Map<String, Object> commandMap) {
		return selectOne(sqlNameSpace+"selectScheduleDetail", commandMap);
	}

	public void addSchedule(Map<String, Object> commandMap) {
		insert(sqlNameSpace+"insertSchedule", commandMap);
	}

	public void updateSchedule(Map<String, Object> commandMap) {
		update(sqlNameSpace+"updateSchedule", commandMap);
	}

	public void deleteSchedule(Map<String, Object> commandMap) {
		delete(sqlNameSpace+"deleteSchedule", commandMap);
	}

	public HashMap getScheduleByDayDetail(Map<String, Object> commandMap) {
		return selectOne(sqlNameSpace+"selectScheduleByDayDetail", commandMap);
	}

	public void addScheduleByDay(Map<String, Object> commandMap) {

		insert(sqlNameSpace+"insertScheduleByDay", commandMap);
	}

	public void updateScheduleByDay(Map<String, Object> commandMap) {

		update(sqlNameSpace+"updateScheduleByDay", commandMap);
	}

	public void deleteScheduleByDay(Map<String, Object> commandMap) {

		delete(sqlNameSpace+"deleteScheduleByDay", commandMap);
	}


	public HashMap getTerminalDetail(Map<String, Object> commandMap) {
		return selectOne(sqlNameSpace+"selectTerminalDetail", commandMap);
	}


	public List<HashMap> getTerminalList(Map<String, Object> commandMap) {
		return selectList(sqlNameSpace+"selectTerminalList", commandMap);
	}

	public List<HashMap> getDoorAlarmGrpList(Map<String, Object> commandMap) {
		return selectList(sqlNameSpace+"selectDoorAlarmGrpList", commandMap);
	}

	public HashMap getDoorAlarmGrpDetail(Map<String, Object> commandMap) {
		return selectOne(sqlNameSpace+"selectDoorAlarmGrpDetail", commandMap);
	}

	public void addDoorAlarmGrp(Map<String, Object> commandMap) {
		insert(sqlNameSpace+"insertDoorAlarmGrp", commandMap);
	}

	public void updateDoorAlarmGrp(Map<String, Object> commandMap) {
		update(sqlNameSpace+"updateDoorAlarmGrp", commandMap);
	}

	public void deleteDoorAlarmGrp(Map<String, Object> commandMap) {
		delete(sqlNameSpace+"deleteDoorAlarmGrp", commandMap);
	}
}
