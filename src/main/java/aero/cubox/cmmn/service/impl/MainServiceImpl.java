package aero.cubox.cmmn.service.impl;

import aero.cubox.cmmn.service.MainService;
import aero.cubox.core.vo.*;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("mainService")
public class MainServiceImpl extends EgovAbstractServiceImpl implements MainService {

    @Resource(name="mainDAO")
    private MainDAO mainDAO;


	@Override
	public List<EntHistVO> getMainEntHistList() throws Exception {
		return mainDAO.getMainEntHistList();
	}

	@Override
	public int getDayEntCount() throws Exception {
		return mainDAO.getDayEntCount();
	}

	@Override
	public int getDayEntEmpCount() throws Exception {
		return mainDAO.getDayEntEmpCount();
	}

    @Override
    public List<HashMap> getMainAlarmHistList() throws Exception {
		return mainDAO.getMainAlarmHistList();
    }

    @Override
    public List<HashMap> getMainStatus01() throws Exception {
		return mainDAO.getMainStatus01();
    }

}
