package home.automation.restclient;

import feign.RequestLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class RelayClientImpl {
/*
    @Autowired
    private RelayClientImpl.RelayClient client;

    public void enableRelayWithTimer(URI uri, String relayNumber,String timerSeconds) throws URISyntaxException {
        client.enableRelayWithTimer(new URI ("http","localhost:8888","/relay/0?turn=on&timer=10"));
    }

    @FeignClient(name="relay-service")
    public interface RelayClient {
        @RequestLine("GET")
        public void enableRelayWithTimer(URI uri);
    }
*/
}
