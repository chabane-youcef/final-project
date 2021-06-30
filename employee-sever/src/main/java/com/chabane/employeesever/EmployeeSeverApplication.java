package com.chabane.employeesever;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EmployeeSeverApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeSeverApplication.class, args);
    }

}
