package aero.cubox.report.service.impl;

import aero.cubox.core.vo.*;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;


@Repository("reportDAO")
public class ReportDAO extends EgovAbstractMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportDAO.class);
    private static final String sqlNameSpace = "report.";

    public List<EntHistVO> getEntHistList(EntHistVO vo) throws Exception {
        return selectList ("report.getEntHistList", vo);
    }

    public int getEntHistListCount(EntHistVO vo) throws Exception {
        return selectOne ("report.getEntHistListCount", vo);
    }

    public EntHistBioVO selectEntFaceOne(EntHistBioVO vo) throws Exception {
        return selectOne ("report.selectEntFaceOne", vo);
    }

    public List<AlarmHistVO> getAlarmHistList(AlarmHistVO vo) throws Exception {
        return selectList ("report.getAlarmHistList", vo);
    }

    public int getAlarmHistListCount(AlarmHistVO vo) throws Exception {
        return selectOne ("report.getAlarmHistListCount", vo);
    }

    public HashMap getDoorDetail(HashMap map) throws Exception {
        return  selectOne ( "report.getDoorDetail", map);
    }

    public HashMap getEntHistBioImg(int id) throws Exception {
        return  selectOne ( "report.getEntHistBioImg", id);
    }

    public FaceVO selectFaceOne(int faceId) throws Exception {
        return  selectOne ( "report.selectFaceOne", faceId);
    }

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
