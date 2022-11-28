package aero.cubox.report.service.impl;

import aero.cubox.core.vo.AlarmHistVO;
import aero.cubox.core.vo.EntHistBioVO;
import aero.cubox.core.vo.EntHistVO;
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

}
