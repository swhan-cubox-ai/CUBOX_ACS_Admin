package aero.cubox.errManagement.service;

import aero.cubox.core.vo.FaceFeatureErrVO;

import java.util.HashMap;
import java.util.List;

public interface ErrManagementService {

    List<FaceFeatureErrVO> selectFaceFeatureErrList(FaceFeatureErrVO vo) throws Exception;

    int getFaceFeatureErrCount(FaceFeatureErrVO vo) throws Exception;

    FaceFeatureErrVO selectFaceFeatureErrOne(FaceFeatureErrVO vo) throws Exception;


}
