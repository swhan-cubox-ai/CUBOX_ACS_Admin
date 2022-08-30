package aero.cubox.gate.service.impl;

import aero.cubox.board.service.vo.BoardVO;
import aero.cubox.core.vo.CodeVO;
import aero.cubox.core.vo.DateTimeVO;
import aero.cubox.core.vo.LoginVO;
import aero.cubox.gate.service.GateService;
import aero.cubox.util.DataScrty;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("gateService")
public class GateServiceImpl extends EgovAbstractServiceImpl implements GateService {

    @Resource(name="gateDAO")
    private GateDAO gateDAO;


}
