package home.automation.restclient;

import home.automation.timer.entity.WeatherForecast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class WeatherClient {

    @Autowired
    private ForecastClient client;

    public void getForecast() {
        client.retrieveForecast();
    }

    @FeignClient(name="weather-service", url="localhost:8888")
    public interface ForecastClient {
        @GetMapping("/data/2.5/onecall?lat=50&lon=36.16667&appid=58c1d5503bf6df3ae991f84287406ba1")
        public WeatherForecast retrieveForecast();
    }
}