package aero.cubox.admin.service.impl;

import aero.cubox.admin.service.AdminService;
import aero.cubox.core.vo.AuthorVO;
import aero.cubox.core.vo.SiteUserVO;
import aero.cubox.util.DataScrty;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("adminService")
public class AdminServiceImpl extends EgovAbstractServiceImpl implements AdminService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);

	@Resource(name = "adminDAO")
	private AdminDAO adminDAO;


	/**
	 * 사이트유저가져오기
	 * @param
	 * @return List<SiteUserVO>
	 * @throws Exception
	 */
	@Override
	public List<SiteUserVO> getSiteUserList(SiteUserVO siteUserVO) throws Exception {
		return adminDAO.getSiteUserList(siteUserVO);
	}

	/**
	 * 입력한 사용자아이디의 중복여부를 체크하여 사용가능여부를 확인
	 * @return 사용가능여부(아이디 사용회수 int)
	 * @throws Exception
	 */
	@Override
	public int checkIdDplct(String checkId) {
		return adminDAO.checkIdDplct(checkId);
	}

	/**
	 * 계정추가
	 * @return int
	 * @throws Exception
	 */
	@Override
	public int siteUserAddSave(SiteUserVO vo) throws Exception {

		String fpasswd = DataScrty.encryptPassword(vo.getFsiteid() + "1234", vo.getFsiteid());
		LOGGER.debug("vo.getFsiteid() : " + vo.getFsiteid());
		LOGGER.debug("fpasswd : " + fpasswd);
		vo.setFpasswd(fpasswd);

		return adminDAO.siteUserAddSave(vo);
	}

	/**
	 * 계정편집
	 * @return int
	 * @throws Exception
	 */
	@Override
	public int siteUserInfoChangeSave(SiteUserVO vo) throws Exception {
		return adminDAO.siteUserInfoChangeSave(vo);
	}

	/**
	 * 계정사용유무변경
	 * @return int
	 * @throws Exception
	 */
	@Override
	public int siteUserFuseynChangeSave(SiteUserVO vo) throws Exception {
		return adminDAO.siteUserFuseynChangeSave(vo);
	}

	/**
	 * 계정비밀번호초기화
	 * @return int
	 * @throws Exception
	 */
	@Override
	public int siteUserPasswdReset(SiteUserVO vo) throws Exception {

		String fpasswd = DataScrty.encryptPassword(vo.getFsiteid() + "1234", vo.getFsiteid());
		LOGGER.debug("vo.getFsiteid() : " + vo.getFsiteid());
		LOGGER.debug("fpasswd : " + fpasswd);
		vo.setFpasswd(fpasswd);

		return adminDAO.siteUserPasswdReset(vo);
	}

	/**
	 * 비밀번호체크
	 * @return int
	 * @throws Exception
	 */
	@Override
	public int checkPwd(SiteUserVO vo) throws Exception {

		String fpasswd = DataScrty.encryptPassword(vo.getFpasswd(), vo.getFsiteid());
		LOGGER.debug("vo.getFsiteid() : " + vo.getFsiteid());
		LOGGER.debug("fpasswd : " + fpasswd);
		vo.setFpasswd(fpasswd);

		return adminDAO.checkPwd(vo);
	}

	/**
	 * 비밀번호변경저장
	 * @return int
	 * @throws Exception
	 */
	@Override
	public int passwdChangeSave(SiteUserVO vo) throws Exception {

		String fpasswd = DataScrty.encryptPassword(vo.getFpasswd(), vo.getFsiteid());
		LOGGER.debug("vo.getFsiteid() : " + vo.getFsiteid());
		LOGGER.debug("fpasswd : " + fpasswd);
		vo.setFpasswd(fpasswd);

		return adminDAO.passwdChangeSave(vo);
	}

	@Override
	public List<AuthorVO> getAuthList() throws Exception {
		return adminDAO.getAuthList();
	}

	@Override
	public int authAddSave(AuthorVO vo) throws Exception {
		return adminDAO.authAddSave(vo);
	}


}
