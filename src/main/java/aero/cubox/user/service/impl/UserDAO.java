package aero.cubox.user.service.impl;

import java.util.List;

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

}
