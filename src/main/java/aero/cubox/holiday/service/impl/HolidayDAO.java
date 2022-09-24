package aero.cubox.holiday.service.impl;

import aero.cubox.core.vo.HolidayVO;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("holidayDAO")
public class HolidayDAO extends EgovAbstractMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(HolidayDAO.class);
    private static final String sqlNameSpace = "holiday.";


    public List<HolidayVO> getHolidayList(HolidayVO vo) throws Exception {
        return selectList ("holiday.getHolidayList", vo);
    }

    public List<Map> getHolidayNmList(Map<String, Object> map) throws Exception {
        return selectList ("holiday.getHolidayNmList", map);
    }

    public int getHolidayListCount(HolidayVO vo) throws Exception {
        return selectOne ("holiday.getHolidayListCount", vo);
    }

    public HashMap getHolidayDetail(int id) throws Exception {
        return selectOne ("holiday.getHolidayDetail", id);
    }

    public int addHoliday(Map<String, Object> map) throws Exception {
        return insert ("holiday.addHoliday", map);
    }

    public int modifyHoliday(Map<String, Object> map) throws Exception {
        return insert ("holiday.modifyHoliday", map);
    }

    public int deleteHoliday(Map<String, Object> map) throws Exception {
        return delete ("holiday.deleteHoliday", map);
    }
}
