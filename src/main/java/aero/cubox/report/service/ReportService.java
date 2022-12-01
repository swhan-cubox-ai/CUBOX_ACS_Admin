package aero.cubox.report.service;

import aero.cubox.core.vo.AlarmHistVO;
import aero.cubox.core.vo.EntHistBioVO;
import aero.cubox.core.vo.EntHistVO;
import aero.cubox.core.vo.FaceVO;

import java.util.HashMap;
import java.util.List;

public interface ReportService {

    public List<EntHistVO> getEntHistList(EntHistVO vo) throws Exception;

    public int getEntHistListCount(EntHistVO vo) throws Exception;

    public EntHistBioVO selectEntFaceOne(EntHistBioVO vo) throws Exception;

    public List<AlarmHistVO> getAlarmHistList(AlarmHistVO vo) throws Exception;

    public int getAlarmHistListCount(AlarmHistVO vo) throws Exception;

    public HashMap getDoorDetail(HashMap map) throws Exception;

    public HashMap getEntHistBioImg(int id) throws Exception;

    public FaceVO selectFaceOne(String empCd) throws Exception;


}
