package aero.cubox.link.service.impl;

import aero.cubox.link.service.MdmService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("MdmService")
public class MdmServiceImpl implements MdmService {
    @Resource(name = "mdmDAO")
    private MdmDAO mdmDAO;

    @Override
    public String getTmMdmCpgnList() throws Exception {
        return mdmDAO.selectTmMdmCpgnList();
    }

}
