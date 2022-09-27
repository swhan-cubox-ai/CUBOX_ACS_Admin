package aero.cubox.holiday.service.impl;


import aero.cubox.core.vo.HolidayVO;
import aero.cubox.holiday.service.HolidayService;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("holidayService")
public class HolidayServiceImpl extends EgovAbstractServiceImpl implements HolidayService {

    @Resource(name="holidayDAO")
    private HolidayDAO holidayDAO;

    @Override
    public List<HolidayVO> getHolidayList(HolidayVO vo) throws Exception {
        return holidayDAO.getHolidayList(vo);
    }

    @Override
    public List<Map> getHolidayNmList(Map<String, Object> map) throws Exception {
        return holidayDAO.getHolidayNmList(map);
    }

    @Override
    public int getHolidayListCount(HolidayVO vo) throws Exception {
        return holidayDAO.getHolidayListCount(vo);
    }

    @Override
    public HashMap getHolidayDetail(int id) throws Exception {
        return holidayDAO.getHolidayDetail(id);
    }

    @Override
    public int addHoliday(Map<String, Object> map) throws Exception {
        return holidayDAO.addHoliday(map);
    }

    @Override
    public int modifyHoliday(Map<String, Object> map) throws Exception {
        return holidayDAO.modifyHoliday(map);
    }

    @Override
    public int deleteHoliday(Map<String, Object> map) throws Exception {
        return holidayDAO.deleteHoliday(map);
    }
}
