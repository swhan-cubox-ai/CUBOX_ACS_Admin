package aero.cubox.cmmn.service.impl;

import aero.cubox.core.vo.*;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository("mainDAO")
public class MainDAO extends EgovAbstractMapper {

	private static final String sqlNameSpace = "main.";


	public List<EntHistVO> getMainEntHistList() throws Exception {
		return selectList (sqlNameSpace+"getMainEntHistList");
	}

	public int getDayEntCount() throws Exception {
		return selectOne (sqlNameSpace+"getDayEntCount");
	}

	public int getDayEntEmpCount() throws Exception {
		return selectOne (sqlNameSpace+"getDayEntEmpCount");
	}

    public List<HashMap> getMainAlarmHistList() {

		return selectList (sqlNameSpace+"selectMainAlarmHistList");
    }

	public List<HashMap> getMainStatus01() {
		return selectList (sqlNameSpace+"selectMainStatus01");
	}

    public List<HashMap> getMainStatus02() {
		return selectList (sqlNameSpace+"selectMainStatus02");
    }
}
