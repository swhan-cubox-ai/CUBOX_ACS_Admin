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

	public Map getDoor(Map<String, Object> commandMap) {
		return selectOne(sqlNameSpace+"selectDoor", commandMap);
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
		update(sqlNameSpace+"insertDoor", commandMap);
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
}
