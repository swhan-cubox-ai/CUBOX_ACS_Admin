package aero.cubox.cmmn.service;

import aero.cubox.core.vo.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface MainService {

	List<EntHistVO> getMainEntHistList() throws Exception;

	int getDayEntCount() throws Exception;

	int getDayEntEmpCount() throws Exception;

    List<HashMap> getMainAlarmHistList() throws Exception;

    List<HashMap> getMainStatus01() throws Exception;

    List<HashMap> getMainStatus02() throws Exception;
}
