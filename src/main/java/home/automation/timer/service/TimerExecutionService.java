package home.automation.timer.service;

import home.automation.repository.RelayTimerRepository;
import home.automation.restclient.RelayClientImpl;
import home.automation.timer.entity.RelayTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

@Service
public class TimerExecutionService {

    Logger LOGGER = LoggerFactory.getLogger(TimerExecutionService.class);

    @Autowired
    private RelayTimerRepository relayTimerRepository;

    @Autowired
    private RelayClientImpl relayClient;

    private List<RelayTimer> timers = new CopyOnWriteArrayList<>();

    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(10);

    @PostConstruct
    public void beepForAnHour() {
        timers.addAll(relayTimerRepository.findAll());
        System.out.println(timers.size() + " timers has been initialised.");
        final Runnable beeper = new Runnable() {
            @Override
            public void run() {
                long secondsFromDayBeginning = LocalTime.now().toSecondOfDay();
                for (RelayTimer timer : timers) {
                    for (int second : timer.getEnableAtSecondOfDay()) {
                        if (second - secondsFromDayBeginning < 10 && second - secondsFromDayBeginning >= 0) {
                            LOGGER.info("Enabled " + timer.getName() + " timer id = " + timer.getId());
                            /*try {
                                relayClient.enableRelayWithTimer(new URI("http","localhost:8888",""), "0", "");
                            } catch (URISyntaxException e) {
                                LOGGER.error("Cannot execte rest call", e);
                            }*/
                        }
                    }
                }
            }
        };
        final ScheduledFuture<?> beeperHandle =
                scheduler.scheduleAtFixedRate(beeper, 10, 10, SECONDS);
    }

    public List<RelayTimer> getTimers() {
        return timers;
    }
}
