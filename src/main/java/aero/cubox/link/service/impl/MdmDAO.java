package aero.cubox.link.service.impl;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

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
}
