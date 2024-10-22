package org.example.springbootprojectthird;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private String position;
    private String department;
    private BigDecimal salary;

    public Employee(String firstName, String lastName, String position, String department, BigDecimal salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.department = department;
        this.salary = salary;
    }
}
