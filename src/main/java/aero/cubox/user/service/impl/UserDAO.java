package aero.cubox.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aero.cubox.board.service.vo.BoardVO;
import aero.cubox.core.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository("userDAO")
public class UserDAO extends EgovAbstractMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAO.class);
    private static final String sqlNameSpace = "user.";

    /**
     * 일반 사용자등록 임시
     */
    public int addUser(UserVO vo) throws Exception {
        return insert(sqlNameSpace+"addUser", vo);
    }

    /**
	 * 비밀번호체크
	 * @return int
	 * @throws Exception
	 */
    public int checkPwd(UserVO vo){
        return (Integer)selectOne(sqlNameSpace+"checkPwd", vo);
    }


    /**
     * 계정비밀번호변경저장
     * @return int
     * @throws Exception
     */
	public int passwdChangeSave(UserVO vo) throws Exception {
        return insert(sqlNameSpace+"passwdChangeSave", vo);
    }


    public List<UserVO> getUserList(Map<String, Object> map) throws Exception {
        return selectList ("user.getUserList", map);
    }
}
