package com.chabane.gatewayserver.services.employee;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee {
    private int id;
    private String fullName;
    private String phone;
    private EmployeeSex sex;
    private EmployeeStatus status;
}
