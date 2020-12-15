package home.automation.restclient;

import feign.Feign;
import feign.RequestLine;
import feign.Target;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@Import(FeignClientsConfiguration.class)
public class RelayClientImpl {

    RelayClient relayClient;

    @Autowired
    public RelayClientImpl(Decoder decoder, Encoder encoder) {
        relayClient = Feign.builder().encoder(encoder).decoder(decoder)
                .target(Target.EmptyTarget.create(RelayClient.class));
    }

    public void enableRelayWithTimer(String relayHostName, String relayNumber,String timerSeconds) throws URISyntaxException {
        relayClient.enableRelayWithTimer(URI.create("http://" + relayHostName + "/relay/" + relayNumber + "?turn=on&timer=" + timerSeconds));
    }

    @FeignClient(name="relay-service")
    public interface RelayClient {
        @RequestLine("GET")
        public void enableRelayWithTimer(URI uri);
    }

}
