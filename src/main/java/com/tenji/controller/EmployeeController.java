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

    @RequestMapping(value = "/addEmployee")
    public String addEmployee(@ModelAttribute Employee employee) {
        System.out.println("开始新增...");
        employeeService.addEmployee(employee);
        return "newEmployee";
    }

    @GetMapping(value = "/updateEmployee")
    public boolean updateEmployee( Employee employee) {
        System.out.println("开始更新...");
        return employeeService.updateEmployee(employee);
    }

    @GetMapping(value = "/deleteEmployee")
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
    public ModelAndView findEmployees(@ModelAttribute Employee employee) {
        System.out.println("Start123list...");

        List<Employee> emLst = employeeService.findEmployeeList(employee);
        ModelAndView mv=new ModelAndView();
        //model.addAttribute("records", emLst);
        mv.addObject("records",emLst);
        mv.setViewName("employeeList.html");
        return mv;
    }
}
