package aero.cubox.cmmn.service.impl;

import java.util.List;
import java.util.Map;

import aero.cubox.core.vo.CodeVO;
import aero.cubox.core.vo.CommonVO;
import aero.cubox.core.vo.DateTimeVO;
import aero.cubox.core.vo.LoginVO;
//import aero.cubox.core.vo.LoginVO;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository("commonDAO")
public class CommonDAO extends EgovAbstractMapper {

	private static final String sqlNameSpace = "common.";

	/**
	 * 일반 로그인을 처리한다
	 * @param vo LoginVO
	 * @return LoginVO
	 * @exception Exception
	 */
    public LoginVO actionLogin(LoginVO vo) throws Exception {
    	return (LoginVO)selectOne(sqlNameSpace + "actionLogin", vo);
    }

    /**
	 * actionLogin
	 * @param
	 * @return
	 * @throws Exception
	 */
//	public int lastConnect(LoginVO vo) throws Exception {
//		return update(sqlNameSpace+"lastConnect", vo);
//	}

	/**
	 * 코드가져오기
	 * @return List<CodeVO>
	 * @throws Exception
	 */
	public List<CodeVO> getCodeList(CodeVO vo) throws Exception {
        return selectList(sqlNameSpace+"getCodeList", vo);
    }

	/**
	 * 코드가져오기
	 * @return List<CodeVO>
	 * @throws Exception
	 */
	public List<CodeVO> getCodeList2(CodeVO vo) throws Exception {
        return selectList(sqlNameSpace+"getCodeList2", vo);
    }

	/**
	 * 코드값가져오기 (fvalue)
	 * @return List<CodeVO>
	 * @throws Exception
	 */
	public String getCodeValue(CodeVO vo) throws Exception{
		return selectOne(sqlNameSpace+"getCodeValue", vo);
	}

	/**
	 * 코드명가져오기 (fkind3)
	 * @return List<CodeVO>
	 * @throws Exception
	 */
	public String getCodeKind3(CodeVO vo) throws Exception{
		return selectOne(sqlNameSpace+"getCodeKind3", vo);
	}



	/**
	 * 공통코드 full list
	 * @return List<CodeVO>
	 * @throws Exception
	 */
	public List<CodeVO> getCodeFullList(CodeVO vo) throws Exception {
		return selectList(sqlNameSpace+"getCodeFullList", vo);
	}

	/**
	 * 공통코드 fkind1 list
	 * @return List<CodeVO>
	 * @throws Exception
	 */
	public List<CodeVO> getCodeFkind1List() throws Exception {
		return selectList(sqlNameSpace+"getCodeFkind1List");
	}

	/**
	 * 공통코드 fkind1 list
	 * @return List<CodeVO>
	 * @throws Exception
	 */
	public List<CodeVO> getCodeFkind2List() throws Exception {
		return selectList(sqlNameSpace+"getCodeFkind2List");
	}
	
	public List<CodeVO> getCodeFkind2List2(CodeVO vo) throws Exception {
		return selectList(sqlNameSpace+"getCodeFkind2List2", vo);
	}	

	/**
	 * 공통코드 fkind3 중복 체크
	 * @return int
	 * @throws Exception
	 */
	public int getFkind3Cnt(CodeVO vo) throws Exception {
		return selectOne(sqlNameSpace+"getFkind3Cnt", vo);
	}

	/**
	 * 공통코드 코드 등록
	 * @return int
	 * @throws Exception
	 */
	public int insertCode(CodeVO vo) throws Exception {
		return insert(sqlNameSpace+"insertCode", vo);
	}

	/**
	 * 공통코드 코드 수정
	 * @return int
	 * @throws Exception
	 */
	public int updateCode(CodeVO vo) throws Exception {
		return insert(sqlNameSpace+"updateCode", vo);
	}

	/**
	 * 공통코드 코드 사용유무 수정
	 * @return int
	 * @throws Exception
	 */
	public int updateCodeUseYn(CodeVO vo) throws Exception {
		return insert(sqlNameSpace+"updateCodeUseYn", vo);
	}

	/**
	 * 공통코드 코드 fkind3 최대값 조회
	 * @return int
	 * @throws Exception
	 */
	public String getMaxCodeFkind3(CodeVO vo) throws Exception {
		return selectOne(sqlNameSpace+"getMaxCodeFkind3", vo);
	}

	/**
	 * 어제 오늘날짜, 현재시간가져오기
	 * @param
	 * @return DateTimeVO
	 * @throws Exception
	 */
	public DateTimeVO getDateTime() throws Exception {
		return selectOne(sqlNameSpace+"getDateTime");
	}

	public List<CodeVO> selectAuthorList() throws Exception {
		return selectList(sqlNameSpace+"selectAuthorList");
	}

	public List<CommonVO> getCommonCodeList(String cdType) throws Exception {
		return selectList(sqlNameSpace+"getCommonCodeList", cdType);
	}

	public List<CommonVO> getDeptList() throws Exception {
		return selectList(sqlNameSpace+"getDeptList");
	}


	public Map<String, Object> getDeptInfo(String empCd) throws Exception {
		return selectOne(sqlNameSpace+"getDeptInfo", empCd);
	}
}
