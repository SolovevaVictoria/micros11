package org.exaple;


import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import io.netty.util.internal.ThreadLocalRandom;
import lombok.Data;
import org.exaple.api.Reader;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;
import java.util.List;
import org.exaple.api.Book;


// HttpClient - java.net
// RestTemplate - spring.web
// WebClient - spring.reactive

@Service
public class BookProvider {
    // цель: вызовать GET http:localhost:8180/api/book/random, получить ID и вернуть

    private final WebClient webClient;
    //private final EurekaClient eurekaClient;

    public BookProvider(EurekaClient eurekaClient, ReactorLoadBalancerExchangeFilterFunction loadBalancerExchangeFilterFunction) {
        webClient = WebClient.builder()
                .filter(loadBalancerExchangeFilterFunction)
                .build();
    //    this.eurekaClient = eurekaClient;
    }

    public Book getRandomBook() {
        Book randomBook = webClient.get()
                .uri( "http://book-service/api/book/random")
                .retrieve()
                .bodyToMono(Book.class)
                .block();

        return randomBook;
    }

//    public UUID getRandomBookId() {
//         BookResponse randomBook = webClient.get()
//                .uri( "http://book-service/api/book/random")
//                .retrieve()
//                .bodyToMono(BookResponse.class)
//                .block();
//
//        return randomBook.getId();
//    }

//    private String getBookServiceIp() {
//        Application application = eurekaClient.getApplication("BOOK-SERVICE");
//        List<InstanceInfo> instances = application.getInstances();
//        int randomIndex = ThreadLocalRandom.current().nextInt(instances.size());
//        InstanceInfo randomInstance = instances.get(randomIndex);
//        return "http://" + randomInstance.getIPAddr() + ":" + randomInstance.getPort();
//    }

    @Data
    private static class BookResponse {
        private UUID id;
    }
}