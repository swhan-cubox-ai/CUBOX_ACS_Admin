package aero.cubox.user.service.impl;

import aero.cubox.admin.service.impl.AdminServiceImpl;
import aero.cubox.board.service.vo.BoardVO;
import aero.cubox.cmmn.service.CommonService;
import aero.cubox.core.vo.*;
import aero.cubox.user.service.UserService;
import aero.cubox.util.DataScrty;
import aero.cubox.util.StringUtil;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl extends EgovAbstractServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource(name="userDAO")
    private UserDAO userDAO;

    /**
     * 사용자 등록 임시
     */
    @Override
    public int addUser(UserVO vo) throws Exception {
        String loginPwd = DataScrty.encryptPassword(vo.getLogin_pwd(), vo.getLogin_id());
        LOGGER.debug("vo.getLoginId() : " + vo.getLogin_id());
        vo.setLogin_pwd(loginPwd);
        vo.setUser_nm(vo.getLogin_id() + "사용자");

        return userDAO.addUser(vo);
    }

    @Override
    public int checkPwd(UserVO vo) throws Exception {
        String loginPwd = DataScrty.encryptPassword(vo.getLogin_pwd(), vo.getLogin_id());
		LOGGER.debug("vo.getLogin_id() : " + vo.getLogin_id());
		vo.setLogin_pwd(loginPwd);

		return userDAO.checkPwd(vo);
    }

    /**
 	 * 비밀번호변경저장
 	 * @return int
 	 * @throws Exception
 	 */
	@Override
	public int passwdChangeSave(UserVO vo) throws Exception {

		String loginPwd = DataScrty.encryptPassword(vo.getLogin_pwd(), vo.getLogin_id());
		LOGGER.debug("vo.getLogin_id() : " + vo.getLogin_id());
		LOGGER.debug("loginPwd : " + loginPwd);
		vo.setLogin_pwd(loginPwd);

		return userDAO.passwdChangeSave(vo);
	}

    @Override
    public int getUserListCount(UserVO vo) throws Exception {
        return userDAO.getUserListCount(vo);
    }

    @Override
    public List<UserVO> getUserList(UserVO vo) throws Exception {
        return userDAO.getUserList(vo);
    }

}
