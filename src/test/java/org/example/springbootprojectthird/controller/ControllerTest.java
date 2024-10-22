package org.example.springbootprojectthird.controller;

import org.example.springbootprojectthird.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    @Mock
    Map<Long, Employee> map;
    @Mock
    AtomicLong currentId;
    @Mock
    Controller controller;

    @BeforeEach
    public void setMap() {

        controller.getMap().put(currentId.getAndIncrement(),new Employee("TestName", "TestLastName", "Position", "Department", new BigDecimal(10)));
        controller.getMap().put(currentId.getAndIncrement(),new Employee("TestName2", "TestLastName2", "Position2", "Department2", new BigDecimal(20)));
    }


    @DisplayName("Получение работника по ID, ID меньше нуля")
    @Test
    void getEmployeeByIdMinus() {
        ResponseEntity employeeById = controller.getEmployeeById(-1L);
        HttpStatusCode statusCode = employeeById.getStatusCode();
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
    }

    @DisplayName("Получение работника по ID, проверка на отсутствие ID")
    @Test
    void getEmployeeByIdNotContainsKey() {
        ResponseEntity employeeById = controller.getEmployeeById(3L);
        HttpStatusCode statusCode = employeeById.getStatusCode();
        Assertions.assertEquals(HttpStatus.NOT_FOUND, statusCode);
    }

    @DisplayName("Получение работника по ID, проверка на ID")
    @Test
    void getEmployeeByIdContainsKey() {
        ResponseEntity employeeById = controller.getEmployeeById(1L);
        HttpStatusCode statusCode = employeeById.getStatusCode();
        Assertions.assertEquals(HttpStatus.OK, statusCode);
    }

    @DisplayName("Получение всех работников, пустая мапа")
    @Test
    void getEmployeesIsEmpty() {
        controller.getMap().clear();
        ResponseEntity response = controller.getEmployees();
        HttpStatusCode status = response.getStatusCode();
        Assertions.assertEquals(HttpStatus.OK,status);
    }

    @DisplayName("Получение всех работников, заполненная мапа")
    @Test
    void getEmployees() {
        ResponseEntity response = controller.getEmployees();
        HttpStatusCode status = response.getStatusCode();
        Assertions.assertEquals(HttpStatus.OK,status);
    }

    @DisplayName("Добавление работника, пустое имя")
    @Test
    void addEmployeeEmptyName() {
        ResponseEntity response = controller.addEmployee(new Employee("", "TestLastName", "Position", "Department", new BigDecimal(10)));
        HttpStatusCode statusCode = response.getStatusCode();
        Assertions.assertEquals(HttpStatus.EXPECTATION_FAILED,statusCode);
    }

    @DisplayName("Добавление работника, пустая фамилия")
    @Test
    void addEmployeeEmptyLastName() {
        ResponseEntity response = controller.addEmployee(new Employee("TestName", "", "Position", "Department", new BigDecimal(10)));
        HttpStatusCode statusCode = response.getStatusCode();
        Assertions.assertEquals(HttpStatus.EXPECTATION_FAILED,statusCode);
    }

    @DisplayName("Добавление работника, пустая должность")
    @Test
    void addEmployeeEmptyPosition() {
        ResponseEntity response = controller.addEmployee(new Employee("TestName", "TestLastName", "", "Department", new BigDecimal(10)));
        HttpStatusCode statusCode = response.getStatusCode();
        Assertions.assertEquals(HttpStatus.EXPECTATION_FAILED,statusCode);
    }

    @DisplayName("Добавление работника, пустое отделение")
    @Test
    void addEmployeeEmptyDepartment() {
        ResponseEntity response = controller.addEmployee(new Employee("TestName", "TestLastName", "Position", "", new BigDecimal(10)));
        HttpStatusCode statusCode = response.getStatusCode();
        Assertions.assertEquals(HttpStatus.EXPECTATION_FAILED,statusCode);
    }

    @DisplayName("Добавление работника, зарплата меньше нуля")
    @Test
    void addEmployeeEmptySalary() {
        ResponseEntity response = controller.addEmployee(new Employee("TestName", "TestLastName", "Position", "Department", new BigDecimal(-10)));
        HttpStatusCode statusCode = response.getStatusCode();
        Assertions.assertEquals(HttpStatus.EXPECTATION_FAILED,statusCode);
    }

    @DisplayName("Добавление работника, всё хорошо")
    @Test
    void addEmployee() {
        ResponseEntity response = controller.addEmployee(new Employee("TestName", "TestLastName", "Position", "Department", new BigDecimal(30)));
        HttpStatusCode statusCode = response.getStatusCode();
        Assertions.assertEquals(HttpStatus.CREATED,statusCode);
    }

    @DisplayName("Обновление информации о работнике, ID меньше нуля")
    @Test
    void updateEmployeeMinusId() {
        ResponseEntity employeeById = controller.updateEmployee(-1L,new Employee("TestName", "TestLastName", "Position", "Department", new BigDecimal(30)));
        HttpStatusCode statusCode = employeeById.getStatusCode();
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
    }

    @DisplayName("Обновление информации о работнике, не содержит ID")
    @Test
    void updateEmployeeNotContainsId() {
        ResponseEntity employeeById = controller.updateEmployee(10L, new Employee("TestName", "TestLastName", "Position", "Department", new BigDecimal(30)));
        HttpStatusCode statusCode = employeeById.getStatusCode();
        Assertions.assertEquals(HttpStatus.NOT_FOUND, statusCode);
    }

    @DisplayName("Обновление информации о работнике, пустое имя")
    @Test
    void updateEmployeeEmptyName() {
        ResponseEntity response = controller.updateEmployee(1L,new Employee("", "TestLastName", "Position", "Department", new BigDecimal(10)));
        HttpStatusCode statusCode = response.getStatusCode();
        Assertions.assertEquals(HttpStatus.EXPECTATION_FAILED,statusCode);
    }

    @DisplayName("Обновление информации о работнике, пустая фамилия")
    @Test
    void updateEmployeeEmptyLastName() {
        ResponseEntity response = controller.updateEmployee(1L,new Employee("TestName", "", "Position", "Department", new BigDecimal(10)));
        HttpStatusCode statusCode = response.getStatusCode();
        Assertions.assertEquals(HttpStatus.EXPECTATION_FAILED,statusCode);
    }

    @DisplayName("Обновление информации о работнике, пустая должность")
    @Test
    void updateEmployeeEmptyPosition() {
        ResponseEntity response = controller.updateEmployee(1L,new Employee("TestName", "TestLastName", "", "Department", new BigDecimal(10)));
        HttpStatusCode statusCode = response.getStatusCode();
        Assertions.assertEquals(HttpStatus.EXPECTATION_FAILED,statusCode);
    }

    @DisplayName("Обновление информации о работнике, пустое отделение")
    @Test
    void updateEmployeeEmptyDepartment() {
        ResponseEntity response = controller.updateEmployee(1L,new Employee("TestName", "TestLastName", "Position", "", new BigDecimal(10)));
        HttpStatusCode statusCode = response.getStatusCode();
        Assertions.assertEquals(HttpStatus.EXPECTATION_FAILED,statusCode);
    }

    @DisplayName("Обновление информации о работнике, зарплата меньше")
    @Test
    void updateEmployeeEmptySalary() {
        ResponseEntity response = controller.updateEmployee(1L,new Employee("TestName", "TestLastName", "Position", "Department", new BigDecimal(-10)));
        HttpStatusCode statusCode = response.getStatusCode();
        Assertions.assertEquals(HttpStatus.EXPECTATION_FAILED,statusCode);
    }

    @DisplayName("Обновление информации о работнике, всё в порядке")
    @Test
    void updateEmployee() {
        ResponseEntity response = controller.updateEmployee(1L,new Employee("TestName", "TestLastName", "Position", "Department", new BigDecimal(10)));
        HttpStatusCode statusCode = response.getStatusCode();
        Assertions.assertEquals(HttpStatus.OK,statusCode);
    }

    @DisplayName("Удаление работника, ID меньше нуля")
    @Test
    void deleteEmployeeMinusId() {
        ResponseEntity employeeById = controller.deleteEmployee(-1L);
        HttpStatusCode statusCode = employeeById.getStatusCode();
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, statusCode);
    }

    @DisplayName("Удаление работника, ID нет в мапе")
    @Test
    void deleteEmployeeNotContainsId() {
        ResponseEntity employeeById = controller.deleteEmployee(10L);
        HttpStatusCode statusCode = employeeById.getStatusCode();
        Assertions.assertEquals(HttpStatus.NOT_FOUND, statusCode);
    }

    @DisplayName("Удаление работника")
    @Test
    void deleteEmployee() {
        ResponseEntity employeeById = controller.deleteEmployee(1L);
        HttpStatusCode statusCode = employeeById.getStatusCode();
        Assertions.assertEquals(HttpStatus.NO_CONTENT, statusCode);
    }
}