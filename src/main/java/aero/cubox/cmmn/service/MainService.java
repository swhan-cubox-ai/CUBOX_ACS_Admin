package aero.cubox.cmmn.service;

import aero.cubox.core.vo.*;

import java.util.List;
import java.util.Map;


public interface MainService {

	List<EntHistVO> getMainEntHistList() throws Exception;

	int getDayEntCount() throws Exception;

	int getDayEntEmpCount() throws Exception;

    List<Map> getMainAlarmHistList() throws Exception;
}
