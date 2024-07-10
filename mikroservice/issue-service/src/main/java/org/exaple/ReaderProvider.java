package org.exaple;


import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import io.netty.util.internal.ThreadLocalRandom;
import lombok.Data;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.UUID;
import java.util.List;
import org.exaple.api.Reader;

@Service
public class ReaderProvider {
    private final WebClient webClient;
    //private final EurekaClient eurekaClient;

    public ReaderProvider(EurekaClient eurekaClient, ReactorLoadBalancerExchangeFilterFunction loadBalancerExchangeFilterFunction) {
        webClient = WebClient.builder()
                .filter(loadBalancerExchangeFilterFunction)
                .build();
        //    this.eurekaClient = eurekaClient;
    }

    public Reader getRandomReader() {
        Reader randomReader = webClient.get()
                .uri( "http://reader-service/api/reader/random")
                .retrieve()
                .bodyToMono(Reader.class)
                .block();

        return randomReader;
    }

//    public UUID getRandomReaderId() {
//        ReaderResponse randomReader = webClient.get()
//                .uri( "http://reader-service/api/reader/random")
//                .retrieve()
//                .bodyToMono(ReaderResponse.class)
//                .block();
//
//        return randomReader.getId();
//    }

//    private String getBookServiceIp() {
//        Application application = eurekaClient.getApplication("BOOK-SERVICE");
//        List<InstanceInfo> instances = application.getInstances();
//        int randomIndex = ThreadLocalRandom.current().nextInt(instances.size());
//        InstanceInfo randomInstance = instances.get(randomIndex);
//        return "http://" + randomInstance.getIPAddr() + ":" + randomInstance.getPort();
//    }

    @Data
    private static class ReaderResponse {
        private UUID id;
        //private String firstname;
    }
}