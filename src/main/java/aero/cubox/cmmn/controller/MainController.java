package aero.cubox.cmmn.controller;

import aero.cubox.cmmn.service.MainService;
import aero.cubox.core.vo.EntHistVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;


@Controller
public class MainController {

	@Resource(name="mainService")
	private MainService mainService;

	private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

	@RequestMapping(value="/main/entHist")
	public ModelAndView mainEntHistList() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsonView");

		List<EntHistVO> entHistList = mainService.getMainEntHistList();

		modelAndView.addObject("entHistList", entHistList);

		return modelAndView;
	}
}
