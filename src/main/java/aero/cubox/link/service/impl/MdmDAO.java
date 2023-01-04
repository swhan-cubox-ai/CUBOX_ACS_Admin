package aero.cubox.link.service.impl;

import aero.cubox.core.vo.MdmVO;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class MdmDAO extends EgovAbstractMapper {

    @Override
    @Resource(name = "sqlSession_mdm")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }

    public String selectTmMdmCpgnList() throws Exception {
        return selectOne("mdm.selectTmMdmCpgnList");
    }

    public List<MdmVO> getMdmList(MdmVO vo) throws Exception {
        return selectList ("mdm.getMdmList", vo);
    }

    public int getMdmListCount(MdmVO vo) throws Exception {
        return selectOne ("mdm.getMdmListCount", vo);
    }
}
