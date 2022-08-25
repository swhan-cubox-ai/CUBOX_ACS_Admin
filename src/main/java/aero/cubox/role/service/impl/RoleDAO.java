package aero.cubox.role.service.impl;

import aero.cubox.core.vo.AuthorVO;
import aero.cubox.core.vo.RoleVO;
import aero.cubox.core.vo.UserVO;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository("roleDAO")
public class RoleDAO extends EgovAbstractMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleDAO.class);

    private static final String sqlNameSpace = "role.";

    public List<RoleVO> selectRoleList(HashMap<String, Object> map) throws Exception {
        return selectList ("role.selectRoleList", map);
    }

}
