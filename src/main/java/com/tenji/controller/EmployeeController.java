package com.tenji.controller;
import com.tenji.entity.Employee;
import com.tenji.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class EmployeeController {

    private EmployeeService employeeService;

    @PostMapping(value = "/addEmployeeC")
    public boolean addEmployee( @RequestParam("usercd") String usercd,
                                @RequestParam("username") String username,
                                @RequestParam("sex") String sex) {
        System.out.println("开始新增...");
        Employee employee = new Employee();
        employee.setusername(username);
        employee.setusercd(usercd);
        employee.setsex(sex);
        return employeeService.addEmployee(employee);
    }

    @GetMapping(value = "/updateEmployeeC")
    public boolean updateEmployee( Employee employee) {
        System.out.println("开始更新...");
        return employeeService.updateEmployee(employee);
    }

    @GetMapping(value = "/deleteEmployeeC")
    public boolean deleteEmployee(@RequestParam(value = "userName", required = true) String userCD) {
        System.out.println("开始删除...");
        return employeeService.deleteEmployee(userCD);
    }


    @GetMapping(value = "/findByUserNameCol")
    public Employee findByUserName(Map<String, Object> model) {
        System.out.println("Start123...");
        model.put("records", "output123");
        return employeeService.findEmployeeByName("123");
    }
}
