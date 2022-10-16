package aero.cubox.system.service.impl;


import aero.cubox.core.vo.PrivacyVO;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("systemDAO")
public class SystemDAO extends EgovAbstractMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemDAO.class);
    private static final String sqlNameSpace = "system.";

    public List<PrivacyVO> getPrivacyList(PrivacyVO vo) throws Exception {
        return selectList ("system.getPrivacyList", vo);
    }

    public int getPrivacyListCount(PrivacyVO vo) throws Exception {
        return selectOne ("system.getPrivacyListCount", vo);
    }

    public Map getEmpDetail(Map map) throws Exception {
        return selectOne ("system.getEmpDetail", map);
    }

    public int delAllPrivacy(Map map) throws Exception {
        return update ("system.delAllPrivacy", map);
    }

    public int delPrivacy(Map map) throws Exception {
        return update ("system.delPrivacy", map);
    }
}
