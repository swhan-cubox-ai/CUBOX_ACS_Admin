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
		return selectOne(sqlNameSpace+"selectDoorInformation", commandMap);
	}

	public List<HashMap> getPermissionGroups(Map<String, Object> commandMap) {
		return selectList(sqlNameSpace+"selectPermissionGroup", commandMap);
	}


	public List<HashMap> searchTerminalInformation(Map<String, Object> commandMap) {
		return selectList(sqlNameSpace+"selectTerminalInformation", commandMap);
	}


	public void insertDoorInformation(Map<String, Object> commandMap) {
		update(sqlNameSpace+"insertDoorInformation", commandMap);
	}

	public void updateDoorInformation(Map<String, Object> commandMap) {
		update(sqlNameSpace+"updateDoorInformation", commandMap);
	}
	public void deleteDoor(Map<String, Object> commandMap) {

		delete(sqlNameSpace+"deleteDoor", commandMap);
	}

	public HashMap getTerminalInformation(Map<String, Object> commandMap) {
		return selectOne(sqlNameSpace+"selectTerminalInformation", commandMap);
	}

	public List<Map> getAreaList(Map<String, Object> commandMap) {
		return selectList(sqlNameSpace+"selectAreaList", commandMap);
	}

	public List<Map> getBuildingList(Map<String, Object> commandMap) {
		return selectList(sqlNameSpace+"selectBuildingList", commandMap);
	}

}
