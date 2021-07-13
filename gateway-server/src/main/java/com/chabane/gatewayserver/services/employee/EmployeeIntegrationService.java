package com.chabane.gatewayserver.services.employee;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Subscriber;

@Service
public class EmployeeIntegrationService {
//    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "stubEmployee")
    public Observable<Employee> getEmployee(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<String> request = new HttpEntity<String>(headers);

        return Observable.create(new Observable.OnSubscribe<Employee>(){
            @Override
            public void call(Subscriber<? super Employee> observer){
                try{
                    if(observer.isUnsubscribed()){
                        observer.onNext(restTemplate.exchange("http://localhost:8887/employee-server/employees/firstAvailableEmployee",
                                HttpMethod.GET,request,Employee.class).getBody());
                    }
                }catch (Throwable e){
                    observer.onError(e);
                }
            }
        });
    }

    @SuppressWarnings("unused")
    private Employee stubEmployee() {
        Employee stub = new Employee();
        stub.setId(0);
        stub.setFullName("employee not available");
        stub.setSex(null);
        stub.setPhone("employee not available");
        return stub;
    }
}
