package aero.cubox.terminal.service.impl;

import aero.cubox.core.vo.CommonVO;
import aero.cubox.core.vo.TerminalVO;
import aero.cubox.terminal.service.TerminalService;
import aero.cubox.util.StringUtil;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("terminalService")
public class TerminalServiceImpl extends EgovAbstractServiceImpl implements TerminalService {

    @Resource(name="terminalDAO")
    private TerminalDAO terminalDAO;

    @Override
    public List<TerminalVO> getTerminalList(TerminalVO vo) throws Exception {
        return terminalDAO.getTerminalList(vo);
    }

    @Override
    public int getTerminalListCount(TerminalVO vo) throws Exception {
        return terminalDAO.getTerminalListCount(vo);
    }

    @Override
    public List<CommonVO> getBuildingList() throws Exception {
        return terminalDAO.getBuildingList();
    }

    @Override
    public List<CommonVO> getDoorList() throws Exception {
        return terminalDAO.getDoorList();
    }

    @Override
    public HashMap getTerminalDetail(int id) throws Exception {
        return terminalDAO.getTerminalDetail(id);
    }

    @Override
    public HashMap getDoorInfo(Map map) throws Exception {
        return terminalDAO.getDoorInfo(map);
    }

    @Override
    public List<Map> getBlackList(Map map) throws Exception {
        return terminalDAO.getBlackList(map);
    }

    @Override
    public List<Map> getWhiteList(Map map) throws Exception {
        return terminalDAO.getWhiteList(map);
    }

    @Override
    public List<Map> getEmpSourceList(Map map) throws Exception {
        return terminalDAO.getEmpSourceList(map);
    }

    @Override
    public List<Map> getEmpTargetList(Map map) throws Exception {
        return terminalDAO.getEmpTargetList(map);
    }

    @Override
    @Transactional
    public void registWbList(HashMap<String, Object> map) throws Exception {
        String targetEmpAStr = (String) map.get("targetEmpArray");
        String[] targetEmpArray = targetEmpAStr.split(",");

        this.delWbList(map);

        if(targetEmpArray.length > 0 && ! StringUtil.isEmpty(targetEmpArray[0])){
            for(String empId : targetEmpArray){
                HashMap<String, Object> param = new HashMap<String, Object>();
                param.put("id", map.get("id"));
                param.put("wbTyp", map.get("wbTyp"));
                param.put("empId", empId);

                this.addWbList(param);
            }
        }
    }

    @Override
    public int delWbList(HashMap<String, Object> map) throws Exception {
        return terminalDAO.delWbList(map);
    }

    @Override
    public int addWbList(HashMap<String, Object> map) throws Exception {
        return terminalDAO.addWbList(map);
    }

    @Override
    public int modifyTerminal(Map<String, Object> map) throws Exception {
        return terminalDAO.modifyTerminal(map);
    }

    @Override
    public int deleteTerminal(Map<String, Object> map) throws Exception {
        return terminalDAO.deleteTerminal(map);
    }

    @Override
    public int addTerminal(Map<String, Object> map) throws Exception {
        return terminalDAO.addTerminal(map);
    }
}
