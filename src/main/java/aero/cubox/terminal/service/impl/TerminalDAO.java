package aero.cubox.terminal.service.impl;

import aero.cubox.core.vo.CommonVO;
import aero.cubox.core.vo.TerminalVO;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository("terminalDAO")
public class TerminalDAO extends EgovAbstractMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(TerminalDAO.class);
    private static final String sqlNameSpace = "terminal.";

    public List<TerminalVO> getTerminalList(TerminalVO vo) throws Exception {
        return selectList ("terminal.getTerminalList", vo);
    }

    public int getTerminalListCount(TerminalVO vo) throws Exception {
        return selectOne ("terminal.getTerminalListCount", vo);
    }

    public List<CommonVO> getBuildingList() throws Exception {
        return selectList ("terminal.getBuildingList");
    }

    public List<CommonVO> getDoorList() throws Exception {
        return selectList ("terminal.getDoorList");
    }

    public HashMap getTerminalDetail(int id) throws Exception {
        return selectOne ("terminal.getTerminalDetail", id);
    }

    public HashMap getDoorInfo(Map map) throws Exception {
        return selectOne ("terminal.getDoorInfo", map);
    }

    public List<Map> getBlackList(Map map) throws Exception {
        return selectList ("terminal.getBlackList", map);
    }

    public List<Map> getWhiteList(Map map) throws Exception {
        return selectList ("terminal.getWhiteList", map);
    }

    public List<Map> getEmpSourceList(Map map) throws Exception {
        return selectList ("terminal.getEmpSourceList", map);
    }

    public List<Map> getEmpTargetList(Map map) throws Exception {
        return selectList ("terminal.getEmpTargetList", map);
    }

    public int delWbList(Map map) throws Exception {
        return delete ("terminal.delWbList", map);
    }

    public int addWbList(Map map) throws Exception {
        return insert ("terminal.addWbList", map);
    }

    public int modifyTerminal(Map map) throws Exception {
        return update ("terminal.modifyTerminal", map);
    }

    public int deleteTerminal(Map map) throws Exception {
        return delete ("terminal.deleteTerminal", map);
    }

    public int addTerminal(Map map) throws Exception {
        return insert ("terminal.addTerminal", map);
    }

    public HashMap getTerminalInfo(String doorId) throws Exception {
        return selectOne ("terminal.getTerminalInfo", doorId);
    }
}
