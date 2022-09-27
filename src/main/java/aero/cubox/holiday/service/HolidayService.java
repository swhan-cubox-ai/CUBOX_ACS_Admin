package aero.cubox.holiday.service;

import aero.cubox.core.vo.HolidayVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface HolidayService {


    public List<HolidayVO> getHolidayList(HolidayVO vo) throws Exception;

    public List<Map> getHolidayNmList(Map<String, Object> map) throws Exception;

    public int getHolidayListCount(HolidayVO vo) throws Exception;

    public HashMap getHolidayDetail(int id) throws Exception;

    public int addHoliday(Map<String, Object> map) throws Exception;

    public int modifyHoliday(Map<String, Object> map) throws Exception;

    public int deleteHoliday(Map<String, Object> map) throws Exception;
}
