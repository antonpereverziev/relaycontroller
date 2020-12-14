package home.automation.timer.controller;

import home.automation.repository.RelayTimerRepository;
import home.automation.restclient.WeatherClient;
import home.automation.timer.entity.RelayTimer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.Optional;

@Controller
public class MvcController {
    @Autowired
    RelayTimerRepository relayTimerRepository;

    @Autowired
    private WeatherClient client;

    @RequestMapping("/")
    public String index() {
        //client.getForecast();
        return "mainPage";
    }

    @RequestMapping("/add")
    public String editConfigPage() {
        return "editConfig";
    }

    @RequestMapping(value = "/save",  method = RequestMethod.POST)
    public String saveTimer( @ModelAttribute("timer") RelayTimer timer) {
        if (timer.getId() == null) {
          relayTimerRepository.save(timer);
        } else {
            RelayTimer byId = relayTimerRepository.findById(timer.getId()).get();
            byId.setName(timer.getName());
           // byId.setBeginTime(timer.getBeginTime());
            //byId.setEndTime(timer.getEndTime());
            byId.setControllerHostname(timer.getControllerHostname());
            byId.setHeatingPower(timer.getHeatingPower());
            byId.setPeriodsQuantity(timer.getPeriodsQuantity());
            byId.setPowerConsumption(timer.getPowerConsumption());
            relayTimerRepository.save(byId);
        }
        return "redirect:/";
    }

    @RequestMapping("/edit")
    public String editPage(@RequestParam String id) {
        System.out.println("---->>" + id);
        return "editConfig";
    }
}