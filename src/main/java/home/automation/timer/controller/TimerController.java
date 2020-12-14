package home.automation.timer.controller;

import home.automation.repository.RelayTimerRepository;
import home.automation.timer.entity.RelayTimer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class TimerController {

    @Autowired
    RelayTimerRepository relayTimerRepository;

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
}
