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


    @Override
    public List<Map> getDoorList(Map<String, Object> commandMap) {
        return doorDAO.getDoorList(commandMap);
    }

    @Override
    public Map getDoorInformation(Map<String, Object> commandMap) {

        return doorDAO.getDoorInformation(commandMap);
    }

    @Override
    public List<HashMap> searchPermissionGroup(Map<String, Object> commandMap) {

        return doorDAO.getPermissionGroups(commandMap);
    }

    @Override
    public List<HashMap> getPermissionGroups(Map<String, Object> commandMap) {

        return doorDAO.getPermissionGroups(commandMap);
    }

    @Override
    public List<HashMap> searchTerminalInformation(Map<String, Object> commandMap) {

        return doorDAO.searchTerminalInformation(commandMap);
    }

    @Override
    public void deleteDoor(Map<String, Object> commandMap) {
        doorDAO.deleteDoor(commandMap);
    }

    @Override
    public void updateDoorInformation(Map<String, Object> commandMap) {
        doorDAO.updateDoorInformation(commandMap);

    }

    @Override
    public HashMap getTerminalInformation(Map<String, Object> commandMap) {
        return doorDAO.getTerminalInformation(commandMap);
    }
}
