package aero.cubox.admin.service.impl;

import aero.cubox.core.vo.AuthorVO;
import aero.cubox.core.vo.SiteUserVO;
import aero.cubox.core.vo.SiteVO;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("adminDAO")
public class AdminDAO extends EgovAbstractMapper {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminDAO.class);

	private static final String sqlNameSpace = "admin.";


	/**
	 * 사이트유저가져오기
	 * @param siteUserVO 
	 * @param
	 * @return List<SiteUserVO>
	 * @throws Exception
	 */
//	public List<SiteUserVO> getSiteUserList(SiteUserVO siteUserVO) throws Exception {
//        return selectList(sqlNameSpace+"getSiteUserList", siteUserVO);
//    }
//
//	/**
//     * 입력한 아이디의 중복여부를 체크하여 사용가능여부를 확인
//     * @return int 사용가능여부(아이디 사용회수 )
//     */
//    public int checkIdDplct(String checkId){
//        return (Integer)selectOne(sqlNameSpace+"checkIdDplct", checkId);
//    }
//
//    /**
//	 * 계정추가
//	 * @return int
//	 * @throws Exception
//	 */
//	public int siteUserAddSave(SiteUserVO vo) throws Exception {
//        return insert(sqlNameSpace+"siteUserAddSave", vo);
//    }
//
//	/**
//	 * 계정편집
//	 * @return int
//	 * @throws Exception
//	 */
//	public int siteUserInfoChangeSave(SiteUserVO vo) throws Exception {
//        return insert(sqlNameSpace+"siteUserInfoChangeSave", vo);
//    }
//
//	/**
//	 * 계정사용유무변경
//	 * @return int
//	 * @throws Exception
//	 */
//	public int siteUserFuseynChangeSave(SiteUserVO vo) throws Exception {
//        return insert(sqlNameSpace+"siteUserFuseynChangeSave", vo);
//    }
//
//	/**
//	 * 계정비밀번호초기화
//	 * @return int
//	 * @throws Exception
//	 */
//	public int siteUserPasswdReset(SiteUserVO vo) throws Exception {
//        return insert(sqlNameSpace+"siteUserPasswdReset", vo);
//    }
//
//	/**
//	 * 비밀번호체크
//	 * @return int
//	 * @throws Exception
//	 */
//    public int checkPwd(SiteUserVO vo){
//        return (Integer)selectOne(sqlNameSpace+"checkPwd", vo);
//    }
//
//    /**
//	 * 계정비밀번호변경저장
//	 * @return int
//	 * @throws Exception
//	 */
//	public int passwdChangeSave(SiteUserVO vo) throws Exception {
//        return insert(sqlNameSpace+"passwdChangeSave", vo);
//    }
//
//	/**
//	 * 권한 가져오기
//	 * @param
//	 * @return AuthorVO
//	 * @throws Exception
//	 */
//	public List<AuthorVO> getAuthList() throws Exception {
//		return selectList(sqlNameSpace+"getAuthList");
//	}
//
//	/**
//	 * 권한 추가
//	 * @return int
//	 * @throws Exception
//	 */
//	public int authAddSave(AuthorVO vo) {
//		// TODO Auto-generated method stub
//		return insert(sqlNameSpace+"authAddSave",vo);
//	}
//
//	/**
//	 *  사이트 가져오기
//	 * @param
//	 * @return AuthorVO
//	 * @throws Exception
//	 */
//	public List<AuthorVO> getSiteList() {
//		// TODO Auto-generated method stub
//		return selectList(sqlNameSpace+"getSiteList");
//	}
//
//	/**
//	 * 사이트 유저 추가
//	 * @return int
//	 * @throws Exception
//	 */
//	public int siteAddSave(SiteVO vo) {
//		// TODO Auto-generated method stub
//		return insert(sqlNameSpace+"siteAddSave",vo);
//	}
//
//	/**
//	 * 사이트 유저 중복체크
//	 * @return int
//	 * @throws Exception
//	 */
//	public int getSiteUserCnt(SiteUserVO vo) {
//		// TODO Auto-generated method stub
//		return selectOne(sqlNameSpace+"getSiteUserCnt",vo);
//	}

}
