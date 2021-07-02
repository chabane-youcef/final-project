package com.chabane.gatewayserver;

import com.chabane.gatewayserver.models.Delivery;
import com.chabane.gatewayserver.services.employee.EmployeeIntegrationService;
import com.chabane.gatewayserver.services.order.OrderIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;
import rx.Observable;
import rx.Observer;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
@EnableHystrix
public class GatewayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class, args);
    }

    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    OrderIntegrationService orderIntegrationService;

    @Autowired
    EmployeeIntegrationService employeeIntegrationService;

    @RequestMapping(value = "/orders/order-delivery", params = {"orderId", "employeeId", "address"})
    public DeferredResult<Delivery> deliveryDetails
            (@PathVariable int orderId, @PathVariable int employeeId, @PathVariable String address) {
        Observable<Delivery> delivery = Observable.zip(orderIntegrationService.getOrder(orderId),
                employeeIntegrationService.getEmployee(employeeId), (order, employee) -> {
                    Delivery deliverOrder = new Delivery();
                    deliverOrder.setAddress(address);
                    deliverOrder.setOrder(order);
                    deliverOrder.setEmployee(employee);

                    return deliverOrder;
                });
        return toDeferredResult(delivery);
    }

    public DeferredResult<Delivery> toDeferredResult(Observable<Delivery> details) {
        DeferredResult<Delivery> result = new DeferredResult<>();
        details.subscribe(new Observer<Delivery>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onNext(Delivery delivery) {
                result.setResult(delivery);
            }
        });
        return result;
    }
}
