package home.automation.timer.controller;

import home.automation.repository.RelayTimerRepository;
import home.automation.restclient.RelayClientImpl;
import home.automation.timer.entity.RelayTimer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class TimerController {

    @Autowired
    RelayTimerRepository relayTimerRepository;
    @Autowired
    private RelayClientImpl relayClientImpl;

    @RequestMapping("/timer")
    public ResponseEntity<List<RelayTimer>> getTimers() {
        return ResponseEntity.ok(new ArrayList<>(relayTimerRepository.findAll()));
    }

    @RequestMapping("/timer/{id}")
    public ResponseEntity<RelayTimer> getTimer(@PathVariable Integer id) {
        return ResponseEntity.ok(relayTimerRepository.findById(id).get());
    }
    @DeleteMapping(value = "/timer/{id}")
    public void deleteTimer(@PathVariable Integer id) {
        relayTimerRepository.deleteById(id);
    }
    @RequestMapping("/exe")
    public void getExec() {
        try {
            relayClientImpl.enableRelayWithTimer(new URI("http","localhost:8888","/relay/0?turn=on&timer=10"),"0","10");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
