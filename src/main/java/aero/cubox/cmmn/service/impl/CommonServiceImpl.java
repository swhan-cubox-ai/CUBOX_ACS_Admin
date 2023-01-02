package aero.cubox.cmmn.service.impl;

import aero.cubox.cmmn.service.CommonService;
import aero.cubox.core.vo.CodeVO;
import aero.cubox.core.vo.CommonVO;
import aero.cubox.core.vo.DateTimeVO;
import aero.cubox.core.vo.LoginVO;
//import aero.cubox.core.vo.LoginVO;
import aero.cubox.util.DataScrty;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("commonService")
public class CommonServiceImpl extends EgovAbstractServiceImpl implements CommonService {

    @Resource(name="commonDAO")
    private CommonDAO commonDAO;

    /**
	 * 일반 로그인을 처리한다
	 * @param vo LoginVO
	 * @return LoginVO
	 * @exception Exception
	 */
    @Override
	public LoginVO actionLogin(LoginVO vo) throws Exception {

    	// 1. 입력한 비밀번호를 암호화한다.
		String loginPwd = DataScrty.encryptPassword(vo.getLogin_pwd(), vo.getLogin_id());
    	vo.setLogin_pwd(loginPwd);

    	// 2. 아이디와 암호화된 비밀번호가 DB와 일치하는지 확인한다.
    	LoginVO loginVO = commonDAO.actionLogin(vo);

    	// 3. 결과를 리턴한다.
    	if (loginVO != null && !loginVO.getLogin_id().equals("")) {
    		return loginVO;
    	} else {
    		loginVO = new LoginVO();
    	}

    	return loginVO;
    }

    /**
   	 * 로그인시 마지막 접속일 변경
   	 * @return
   	 * @throws Exception
   	 */
    //@Override
//	public int lastConnect(LoginVO vo) throws Exception {
//    	return commonDAO.lastConnect(vo);
//    }

    /**
	 * 코드가져오기
	 * @return List<CodeVO>
	 * @throws Exception
	 */
	@Override
	public List<CodeVO> getCodeList(String fkind1, String fkind2) throws Exception {
		CodeVO codeVO = new CodeVO();
		codeVO.setFkind1(fkind1);
		codeVO.setFkind2(fkind2);

		return commonDAO.getCodeList(codeVO);
	}

	@Override
	public List<CodeVO> getAuthorList() throws Exception {
		return commonDAO.selectAuthorList();
	}

	/**
	 * 코드가져오기
	 * @return List<CodeVO>
	 * @throws Exception
	 */
	@Override
	public List<CodeVO> getCodeList2(String fkind1, String fkind2) throws Exception {
		CodeVO codeVO = new CodeVO();
		codeVO.setFkind1(fkind1);
		codeVO.setFkind2(fkind2);

		return commonDAO.getCodeList2(codeVO);
	}

	/**
	 * 코드값가져오기 (fvalue)
	 * @return List<CodeVO>
	 * @throws Exception
	 */
	@Override
	public String getCodeValue(String fkind1, String fkind2, String fkind3) throws Exception{

		CodeVO vo = new CodeVO();
		vo.setFkind1(fkind1);
		vo.setFkind2(fkind2);
		vo.setFkind3(fkind3);

		return commonDAO.getCodeValue(vo);

	}

	/**
	 * 코드명가져오기 (fkind3)
	 * @return List<CodeVO>
	 * @throws Exception
	 */
	@Override
	public String getCodeKind3(String fkind1, String fkind2, String fvalue) throws Exception{

		CodeVO vo = new CodeVO();
		vo.setFkind1(fkind1);
		vo.setFkind2(fkind2);
		vo.setFvalue(fvalue);

		return commonDAO.getCodeKind3(vo);
	}


	/**
	 * 공통코드 full list
	 * @return List<CodeVO>
	 * @throws Exception
	 */
	@Override
	public List<CodeVO> getCodeFullList(CodeVO vo) throws Exception {
		return commonDAO.getCodeFullList(vo);
	}

	/**
	 * 공통코드 fkind1 list
	 * @return List<CodeVO>
	 * @throws Exception
	 */
	@Override
	public List<CodeVO> getCodeFkind1List() throws Exception {
		return commonDAO.getCodeFkind1List();
	}

	/**
	 * 공통코드 fkind2 list
	 * @return List<CodeVO>
	 * @throws Exception
	 */
	@Override
	public List<CodeVO> getCodeFkind2List() throws Exception {
		return commonDAO.getCodeFkind2List();
	}
	
	@Override
	public List<CodeVO> getCodeFkind2List2(CodeVO vo) throws Exception {
		return commonDAO.getCodeFkind2List2(vo);
	}

	/**
	 * 공통코드 fkind3 중복 체크
	 * @return int
	 * @throws Exception
	 */
	@Override
	public int getFkind3Cnt(CodeVO vo) throws Exception {
		return commonDAO.getFkind3Cnt(vo);
	}

	/**
	 * 공통코드 코드 등록
	 * @return int
	 * @throws Exception
	 */
	@Override
	public int insertCode(CodeVO vo) throws Exception {
		return commonDAO.insertCode(vo);
	}

	/**
	 * 공통코드 코드 수정
	 * @return int
	 * @throws Exception
	 */
	@Override
	public int updateCode(CodeVO vo) throws Exception {
		return commonDAO.updateCode(vo);
	}

	/**
	 * 공통코드 코드 사용유무 수정
	 * @return int
	 * @throws Exception
	 */
	@Override
	public int updateCodeUseYn(CodeVO vo) throws Exception {
		return commonDAO.updateCodeUseYn(vo);
	}

	/**
	 * 공통코드 코드 fkind3 최대값 조회
	 * @return int
	 * @throws Exception
	 */
	@Override
	public String getMaxCodeFkind3(CodeVO vo) throws Exception {
		return commonDAO.getMaxCodeFkind3(vo);
	}

	/**
	 * 어제 오늘날짜, 현재시간가져오기
	 * @param
	 * @return DateTimeVO
	 * @throws Exception
	 */
	@Override
	public DateTimeVO getDateTime() throws Exception{
		return commonDAO.getDateTime();
	}

	@Override
	public List<CommonVO> getCommonCodeList(String cdType) throws Exception {
		return commonDAO.getCommonCodeList(cdType);
	}

	@Override
	public List<CommonVO> getDeptList() throws Exception {
		return commonDAO.getDeptList();
	}

	@Override
	public Map<String, Object> getDeptInfo(String empCd) throws Exception {
		return commonDAO.getDeptInfo(empCd);
	}

	public List<CodeVO> selectAuthorList() throws Exception {
		return commonDAO.selectAuthorList();

	}

}
