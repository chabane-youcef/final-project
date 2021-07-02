package com.chabane.gatewayserver.services.order;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import rx.Observable;
import rx.Subscriber;;

@Service
public class OrderIntegrationService {
    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "stubOrder")
    public Observable<Order> getOrder(final int orderId){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

        return Observable.create(new Observable.OnSubscribe<Order>(){
            @Override
            public void call(Subscriber<? super Order> observer){
                try {
                    if(!observer.isUnsubscribed()){
                        observer.onNext(restTemplate.exchange("http://oder-server/orders/{id}",
                                HttpMethod.GET,request, Order.class,orderId).getBody());
                        observer.onCompleted();
                    }
                }catch (Throwable e){
                    observer.onError(e);
                }
            }
        });
    }

    private Order stubOrder(final String orderId){
        Order stub = new Order();
        stub.setId(0);
        stub.setClientId(0);
        stub.setOrderDate(null);
        stub.setStatus(OrderStatus.PENDING);
        stub.setOrderDetails(null);
        return stub;
    }
}