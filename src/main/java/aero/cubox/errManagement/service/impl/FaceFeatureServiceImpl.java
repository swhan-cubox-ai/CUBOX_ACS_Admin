package aero.cubox.errManagement.service.impl;

import aero.cubox.core.vo.FaceFeatureErrVO;
import aero.cubox.core.vo.UserVO;
import aero.cubox.errManagement.service.ErrManagementService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("faceFeatureErrService")
public class FaceFeatureServiceImpl extends EgovAbstractServiceImpl implements ErrManagementService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FaceFeatureServiceImpl.class);

    @Resource(name = "faceFeatureErrDAO")
    private FaceFeatureErrDAO faceFeatureErrDAO;


    @Override
    public List<FaceFeatureErrVO> selectFaceFeatureErrList(FaceFeatureErrVO vo) throws Exception {
        return faceFeatureErrDAO.selectFaceFeatureErrList(vo);
    }

    public int getFaceFeatureErrCount(FaceFeatureErrVO vo) throws Exception {
        return faceFeatureErrDAO.getFaceFeatureListCount(vo);
    }

    @Override
    public FaceFeatureErrVO selectFaceFeatureErrOne(FaceFeatureErrVO vo) throws Exception {
        return faceFeatureErrDAO.selectFaceFeatureErrOne(vo);
    }
}
