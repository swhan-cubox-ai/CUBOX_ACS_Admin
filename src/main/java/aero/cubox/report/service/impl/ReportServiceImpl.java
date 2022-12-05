package aero.cubox.report.service.impl;

import aero.cubox.core.vo.*;
import aero.cubox.report.service.ReportService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;


@Service("reportService")
public class ReportServiceImpl extends EgovAbstractServiceImpl implements ReportService {

    @Resource(name="reportDAO")
    private ReportDAO reportDAO;

    @Override
    public List<EntHistVO> getEntHistList(EntHistVO vo) throws Exception {
        return reportDAO.getEntHistList(vo);
    }

    @Override
    public int getEntHistListCount(EntHistVO vo) throws Exception {
        return reportDAO.getEntHistListCount(vo);
    }

    @Override
    public EntHistBioVO selectEntFaceOne(EntHistBioVO vo) throws Exception {
        return reportDAO.selectEntFaceOne(vo);
    }

    @Override
    public List<AlarmHistVO> getAlarmHistList(AlarmHistVO vo) throws Exception {
        return reportDAO.getAlarmHistList(vo);
    }

    @Override
    public int getAlarmHistListCount(AlarmHistVO vo) throws Exception {
        return reportDAO.getAlarmHistListCount(vo);
    }

    @Override
    public HashMap getDoorDetail(HashMap map) throws Exception {
        return reportDAO.getDoorDetail(map);
    }

    @Override
    public HashMap getEntHistBioImg(int id) throws Exception {
        return reportDAO.getEntHistBioImg(id);
    }

    @Override
    public FaceVO selectFaceOne(int faceId) throws Exception {
        return reportDAO.selectFaceOne(faceId);
    }

    @Override
    public List<FaceFeatureErrVO> selectFaceFeatureErrList(FaceFeatureErrVO vo) throws Exception {
        return reportDAO.selectFaceFeatureErrList(vo);
    }

    public int getFaceFeatureErrCount(FaceFeatureErrVO vo) throws Exception {
        return reportDAO.getFaceFeatureListCount(vo);
    }

    @Override
    public FaceFeatureErrVO selectFaceFeatureErrOne(FaceFeatureErrVO vo) throws Exception {
        return reportDAO.selectFaceFeatureErrOne(vo);
    }

}
