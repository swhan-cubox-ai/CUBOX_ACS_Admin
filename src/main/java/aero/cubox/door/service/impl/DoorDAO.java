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

	public Map getDoorInformation(Map<String, Object> commandMap) {
		return selectOne(sqlNameSpace+"selectDoorDetail", commandMap);
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

	public List<HashMap> getDoorGroupList(Map<String, Object> commandMap) {
		return selectList(sqlNameSpace+"selectDoorGroupList", commandMap);
	}


	public void insertDoor(Map<String, Object> commandMap) {
		insert(sqlNameSpace+"insertDoor", commandMap);
	}

	public void updateDoor(Map<String, Object> commandMap) {
		update(sqlNameSpace+"updateDoor", commandMap);
	}
	public void deleteDoorInformation(Map<String, Object> commandMap) {
		delete(sqlNameSpace+"deleteDoorInformation", commandMap);
	}

	public HashMap getTerminal(Map<String, Object> commandMap) {
		return selectOne(sqlNameSpace+"selectTerminal", commandMap);
	}


	public List<HashMap> getTerminalList(Map<String, Object> commandMap) {
		return selectList(sqlNameSpace+"selectTerminal", commandMap);
	}

	public List<HashMap> getScheduleList(Map<String, Object> commandMap) {
		return selectList(sqlNameSpace+"selectDoorSchList", commandMap);
	}

	public List<HashMap> getFloorList(Map<String, Object> commandMap) {
		return selectList(sqlNameSpace+"selectFloorList", commandMap);
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
}
