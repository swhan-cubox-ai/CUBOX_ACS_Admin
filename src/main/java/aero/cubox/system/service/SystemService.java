package aero.cubox.system.service;

import aero.cubox.core.vo.PrivacyVO;

import java.util.List;
import java.util.Map;

public interface SystemService {

    public List<PrivacyVO> getPrivacyList(PrivacyVO vo) throws Exception;

    public int getPrivacyListCount(PrivacyVO vo) throws Exception;

    public Map getEmpDetail(Map map) throws Exception;

    public void delSelectedPrivacy(Map map) throws Exception;

    public int delAllPrivacy(Map map) throws Exception;

    public int delPrivacy(Map map) throws Exception;
}
