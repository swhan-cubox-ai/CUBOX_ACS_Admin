package aero.cubox.terminal.service;

import aero.cubox.core.vo.CommonVO;
import aero.cubox.core.vo.TerminalVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TerminalService {

    public List<TerminalVO> getTerminalList(TerminalVO vo) throws Exception;

    public int getTerminalListCount(TerminalVO vo) throws Exception;

    public List<CommonVO> getBuildingList() throws Exception;

    public List<CommonVO> getDoorList() throws Exception;

    public HashMap getTerminalDetail(int id) throws Exception;

    public HashMap getDoorInfo(Map map) throws Exception;

    public List<Map> getBlackList(Map map) throws Exception;

    public List<Map> getWhiteList(Map map) throws Exception;

    public List<Map> getEmpSourceList(Map map) throws Exception;

    public List<Map> getEmpTargetList(Map map) throws Exception;

    public void registWbList(HashMap<String, Object> map) throws Exception;

    public int delWbList(HashMap<String, Object> map) throws Exception;

    public int addWbList(HashMap<String, Object> map) throws Exception;

    public int modifyTerminal(Map<String, Object> map) throws Exception;

    public int deleteTerminal(Map<String, Object> map) throws Exception;

    public int addTerminal(Map<String, Object> map) throws Exception;
}
