package aero.cubox.role.service.impl;

import aero.cubox.role.service.RoleService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("roleService")
public class RoleServiceImpl extends EgovAbstractServiceImpl implements RoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Resource(name = "roleDAO")
    private RoleDAO roleDAO;


}
