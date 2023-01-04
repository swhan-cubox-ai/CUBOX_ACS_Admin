package aero.cubox.cmmn.service;

import aero.cubox.core.vo.*;

import java.util.List;
import java.util.Map;


public interface CommonService {

	/**
	 * 로그인을 처리한다
	 * @param vo LoginVO
	 * @return LoginVO
	 * @exception Exception
	 */
    LoginVO actionLogin(LoginVO vo) throws Exception;

    /**
	 * 로그인시 마지막 접속일 변경
	 * @return
	 * @throws Exception
	 */
	//int lastConnect(LoginVO vo) throws Exception;

	/**
	 * 코드가져오기
	 * @return List<CodeVO>
	 * @throws Exception
	 */
	public List<CodeVO> getCodeList(String fkind1, String fkind2) throws Exception;

	/**
	 *
	 * @param
	 * @return List<CodeVO>
	 * @throws Exception
	 */
	List<CodeVO> getAuthorList() throws Exception;

	/**
	 * 코드가져오기
	 * @return List<CodeVO>
	 * @throws Exception
	 */
	public List<CodeVO> getCodeList2(String fkind1, String fkind2) throws Exception;

	/**
	 * 코드값가져오기 (fvalue)
	 * @return List<CodeVO>
	 * @throws Exception
	 */
	public String getCodeValue(String fkind1, String fkind2, String fkind3) throws Exception;

	/**
	 * 코드명가져오기 (fkind3)
	 * @return List<CodeVO>
	 * @throws Exception
	 */
	public String getCodeKind3(String fkind1, String fkind2, String fvalue) throws Exception;


	/**
	 * 공통코드 full 가져오기
	 * @return List<CodeVO>
	 * @throws Exception
	 */
	public List<CodeVO> getCodeFullList(CodeVO vo) throws Exception;

	/**
	 * 공통코드 fkind1 가져오기
	 * @return List<CodeVO>
	 * @throws Exception
	 */
	public List<CodeVO> getCodeFkind1List() throws Exception;

	/**
	 * 공통코드 fkind2 가져오기
	 * @return List<CodeVO>
	 * @throws Exception
	 */
	public List<CodeVO> getCodeFkind2List() throws Exception;
	
	public List<CodeVO> getCodeFkind2List2(CodeVO vo) throws Exception;

	/**
	 * 공통코드 fkind3 중복 체크
	 * @return int
	 * @throws Exception
	 */
	public int getFkind3Cnt(CodeVO vo) throws Exception;

	/**
	 * 공통코드 코드 등록
	 * @return int
	 * @throws Exception
	 */
	public int insertCode(CodeVO vo) throws Exception;

	/**
	 * 공통코드 코드 수정
	 * @return int
	 * @throws Exception
	 */
	public int updateCode(CodeVO vo) throws Exception;

	/**
	 * 공통코드 코드 사용 유무 수정
	 * @return int
	 * @throws Exception
	 */
	public int updateCodeUseYn(CodeVO vo) throws Exception;

	/**
	 * 공통코드 코드 fkind3 최대값 조회
	 * @return int
	 * @throws Exception
	 */
	public String getMaxCodeFkind3(CodeVO vo) throws Exception;


	/**
	 * 어제 오늘날짜, 현재시간가져오기
	 * @param
	 * @return DateTimeVO
	 * @throws Exception
	 */
	public DateTimeVO getDateTime() throws Exception;

	/**
	 * 공통코드 목록 가져오기
	 * @return List<CommonVO>
	 * @throws Exception
	 */
	List<CommonVO> getCommonCodeList(String cdType) throws  Exception;

	List<CommonVO> getDeptList() throws  Exception;


	/**
	 * 부서정보 가져오기
	 * @return Map
	 * @throws Exception
	 */
	Map<String, Object> getDeptInfo(String empCd) throws  Exception;

}
