package aero.cubox.user.service;

import aero.cubox.board.service.vo.BoardVO;
import aero.cubox.core.vo.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserService {

	/**
	 * 사용자등록 임시
	 */
	int addUser(UserVO vo) throws Exception;

	int modifyUser(UserVO vo) throws Exception;

	/**
	 * 비밀번호체크
	 * @return int
	 * @throws Exception
	 */
	public int checkPwd(UserVO vo) throws Exception;

	/**
	 * 비밀번호변경저장
	 * @return int
	 * @throws Exception
	 */
	public int passwdChangeSave(UserVO vo) throws Exception;

	int getUserListCount(UserVO vo) throws Exception;

	/**
	 * 사용자목록 조회
	 * @param UserVO
	 * @return UserVO
	 * @throws Exception
	 */
	public List<UserVO> getUserList(UserVO vo) throws Exception;

	/**
	 * 사용자 상세 조회
	 * @param int
	 * @return UserVO
	 * @throws Exception
	 */
	public UserVO getUserDetail(int id) throws Exception;

	public int getUserId(UserVO vo) throws Exception;

	public int checkLoginId(Map<String, Object> map) throws Exception;
}
