package org.example.springbootprojectthird.controller;

import org.apache.coyote.Response;
import org.example.springbootprojectthird.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/employees")
public class Controller {
    private Map<Long, Employee> map;

    private AtomicLong currentId = new AtomicLong(1);
    public Controller() {
        map = new ConcurrentHashMap<>();
    }

    public Map<Long, Employee> getMap() {
        return map;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity getEmployeeById(@PathVariable Long id) {
        if (id < 0) {
            return new ResponseEntity("Введите корректный ID", HttpStatus.BAD_REQUEST);
        }
        if (!map.containsKey(id)) {
            return new ResponseEntity("Сотрутник с ID " + id + " не найден", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(map.get(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getEmployees() {
        if (map.isEmpty()) {
            return new ResponseEntity("База данных пуста", HttpStatus.OK);
        }
        return new ResponseEntity(map,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addEmployee(@RequestBody Employee employee) {
        if (employee.getDepartment().isEmpty()
        || employee.getFirstName().isEmpty()
        || employee.getLastName().isEmpty()
        || employee.getPosition().isEmpty()
        || employee.getSalary().compareTo(BigDecimal.ZERO) < 0) {
            return new ResponseEntity("Проверьте правильность введенных данных", HttpStatus.EXPECTATION_FAILED);
        }
        employee.setId(currentId.getAndIncrement());
        map.put(employee.getId(),employee);
        return new ResponseEntity("Пользователь внесен", HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        if (id < 0) {
            return new ResponseEntity("Введите корректный ID", HttpStatus.BAD_REQUEST);
        }
        if (!map.containsKey(id)) {
            return new ResponseEntity("Сотрутник с ID " + id + " не найден", HttpStatus.NOT_FOUND);
        }
        if (employee.getDepartment().isEmpty()
                || employee.getFirstName().isEmpty()
                || employee.getLastName().isEmpty()
                || employee.getPosition().isEmpty()
                || employee.getSalary().compareTo(BigDecimal.ZERO) < 0) {
            return new ResponseEntity("Проверьте правильность введенных данных", HttpStatus.EXPECTATION_FAILED);
        }
        employee.setId(id);
        map.put(employee.getId(),employee);
        return new ResponseEntity("Пользователь изменен", HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteEmployee(@PathVariable Long id) {
        if (id < 0) {
            return new ResponseEntity("Введите корректный ID", HttpStatus.BAD_REQUEST);
        }
        if (!map.containsKey(id)) {
            return new ResponseEntity("Сотрутник с ID " + id + " не найден", HttpStatus.NOT_FOUND);
        }
        map.remove(id);
        return new ResponseEntity("Сотрудник удален", HttpStatus.NO_CONTENT);
    }
}
