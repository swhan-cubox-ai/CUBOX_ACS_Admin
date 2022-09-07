package aero.cubox.gate.controller;

import aero.cubox.cmmn.controller.CommonController;
import aero.cubox.cmmn.service.CommonService;
import aero.cubox.gate.service.GateService;
import aero.cubox.menu.service.MenuService;
import aero.cubox.util.CommonUtils;
import aero.cubox.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.Map;

@Controller
@RequestMapping(value = "/gate/")
public class GateController {

    @Resource(name = "gateService")
    private GateService gateService;

    @Resource(name = "commonUtils")
    private CommonUtils commonUtils;

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonController.class);

    @RequestMapping(value="/management.do")
    public String managent(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        return "cubox/gate/management";
    }

    @RequestMapping(value="/schedule.do")
    public String schedule(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        return "cubox/gate/schedule";
    }

    @RequestMapping(value = "/schedule_add.do")
    public String schedule_add(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        if (commandMap.get("editMode").equals("edit")) {
            model.addAttribute("schName", commandMap.get("schName"));
            model.addAttribute("schUseYn", commandMap.get("schUseYn"));
            model.addAttribute("gateGroup", commandMap.get("gateGroup"));
        }
        model.addAttribute("editMode", commandMap.get("editMode"));
        System.out.println(commandMap.get("editMode"));
        System.out.println(model.get("editMode"));
        System.out.println(model.get("schName"));
        return "cubox/gate/schedule_add";
    }

    @RequestMapping(value="/detail.do")
    public String schedule_detail(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        String[] days = {"월", "화", "수", "목", "금", "토", "일"};
        String[] days_eng = {"mon", "tue", "wed", "thu", "fri", "sat", "sun"};
        model.addAttribute("days", days);
        model.addAttribute("days_eng", days_eng);
        return "cubox/gate/detail";
    }

    @RequestMapping(value="/schedule_delete.do")
    public String schedule_delete(ModelMap model, @RequestParam Map<String, Object> commandMap, RedirectAttributes redirectAttributes) throws Exception {

        return "cubox/gate/schedule";
    }

}
