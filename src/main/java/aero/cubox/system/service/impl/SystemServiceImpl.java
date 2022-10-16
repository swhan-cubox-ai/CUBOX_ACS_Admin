package aero.cubox.system.service.impl;

import aero.cubox.core.vo.PrivacyVO;
import aero.cubox.system.service.SystemService;
import aero.cubox.terminal.service.impl.TerminalDAO;
import aero.cubox.util.StringUtil;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("systemService")
public class SystemServiceImpl extends EgovAbstractServiceImpl implements SystemService {

    @Resource(name="systemDAO")
    private SystemDAO systemDAO;

    @Override
    public List<PrivacyVO> getPrivacyList(PrivacyVO vo) throws Exception {
        return systemDAO.getPrivacyList(vo);
    }

    @Override
    public int getPrivacyListCount(PrivacyVO vo) throws Exception {
        return systemDAO.getPrivacyListCount(vo);
    }

    @Override
    public Map getEmpDetail(Map map) throws Exception {
        return systemDAO.getEmpDetail(map);
    }

    @Override
    @Transactional
    public void delSelectedPrivacy(Map map) throws Exception {
        String checkedEmpStr = (String) map.get("checkedEmpArray");
        String[] checkedEmpArray = checkedEmpStr.split(",");

        if(checkedEmpArray.length > 0 && ! StringUtil.isEmpty(checkedEmpArray[0])){
            for(String empCd : checkedEmpArray){
                HashMap<String, Object> param = new HashMap<String, Object>();
                param.put("empCd", empCd);
                this.delPrivacy(param);
            }
        }
    }

    @Override
    public int delAllPrivacy(Map map) throws Exception {
        return systemDAO.delAllPrivacy(map);
    }

    @Override
    public int delPrivacy(Map map) throws Exception {
        return systemDAO.delPrivacy(map);
    }
}
