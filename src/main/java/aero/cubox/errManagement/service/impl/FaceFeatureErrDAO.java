package aero.cubox.errManagement.service.impl;

import aero.cubox.core.vo.FaceFeatureErrVO;
import aero.cubox.core.vo.RoleVO;
import aero.cubox.core.vo.UserVO;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository("faceFeatureErrDAO")
public class FaceFeatureErrDAO extends EgovAbstractMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(FaceFeatureErrDAO.class);

    private static final String sqlNameSpace = "faceFeatureErr.";

    public List<FaceFeatureErrVO> selectFaceFeatureErrList(FaceFeatureErrVO vo) throws Exception {
        return selectList (sqlNameSpace + "selectFaceFeatureErrList", vo);
    }

    public int getFaceFeatureListCount(FaceFeatureErrVO vo) {
        return selectOne(sqlNameSpace+"getFaceFeatureListCount", vo);
    }

    public FaceFeatureErrVO selectFaceFeatureErrOne(FaceFeatureErrVO vo) {
        return selectOne(sqlNameSpace+"selectFaceFeatureErrOne", vo);
    }

}
