package lBank.controler;

import lBank.dom.InfoNeed;
import lBank.dom.Information;
import lBank.service.InfoService;
import lBank.service.impl.InfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Controller
public class InfoController {

    @Autowired
    InfoService infoService;


    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String textRe(Model model, HttpServletRequest request) throws IOException {
        model.addAttribute("modelForm", new InfoNeed());
        model.addAttribute("currCode", infoService.getCurrencyCode(request));
        List<Information> informationList = (List<Information>) model.asMap().get("informationList");
        String change = (String) model.asMap().get("change");
        InfoNeed datePick = (InfoNeed) model.asMap().get("datePick");
        model.addAttribute("infoList", informationList);
        model.addAttribute("change", change);
        model.addAttribute("datePick", datePick);
        /*
        //Holiday API---------
        model.addAttribute("holidays", infoService.readJson(LocalDate.now().getYear()));
        */

        //Simple Holiday string
        model.addAttribute("holidays",Arrays.toString(infoService.holidayString()));

        return "home";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String form(@ModelAttribute("modelForm") InfoNeed infoNeed, RedirectAttributes redirectAttrs) throws IOException {
        try {

            List<Information> informationList = infoService.readFile(infoNeed.getCurrencyCode(), infoNeed.getDatePick());
            redirectAttrs.addFlashAttribute("change", infoService.getChange(informationList));
            redirectAttrs.addFlashAttribute("informationList", informationList);
        } catch (ArrayIndexOutOfBoundsException e) {

        }
        redirectAttrs.addFlashAttribute("datePick", infoNeed);
        return "redirect:/";
    }
}
