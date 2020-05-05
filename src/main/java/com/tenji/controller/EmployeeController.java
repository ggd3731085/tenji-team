package com.tenji.controller;
import com.tenji.entity.Employee;
import com.tenji.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
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

    @RequestMapping(path = "/input")
    public String findEmployeeByCd(@ModelAttribute Employee employee, Map<String, Object> model) {
        System.out.println("Start123...");

        Employee em = employeeService.findEmployeeByCd(employee.getusercd());

        ArrayList<String> output = new ArrayList<String>();
        output.add("usercd: " + em.getusercd());
        output.add("username: " + em.getusername());
        model.put("records", output);

        return "input";
    }

    @RequestMapping(path = "/employeeList")
    public String findEmployees(@ModelAttribute Employee employee, ModelAndView model) {
        System.out.println("Start123list...");

        List<Employee> emLst = employeeService.findEmployeeList(employee);

        model.addObject("records", emLst);

        return "employeeList";
    }
}
