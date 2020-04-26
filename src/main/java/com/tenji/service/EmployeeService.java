package com.tenji.service;
import com.tenji.AppConfig;
import com.tenji.dao.EmployeeDao;
import com.tenji.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@Service
public class EmployeeService {

    @Autowired
    AppConfig appConfig;

    public boolean addEmployee(Employee employee) {
        boolean flag=false;
        try{
            //employeeDao.addEmployee(employee);
            flag=true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    public boolean updateEmployee(Employee employee) {
        boolean flag=false;
        try{
            //employeeDao.updateEmployee(employee);
            flag=true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    public boolean deleteEmployee(String userCD) {
        boolean flag=false;
        try{
            //employeeDao.deleteEmployee(userCD);
            flag=true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    public Employee findEmployeeByCd(String userCD) {
        try (Connection connection = appConfig.dataSource().getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT UserCD,UserName,Sex FROM m_Employee where Usercd='" + userCD + "'");

            Employee emp = new Employee();

            while (rs.next()) {
                emp.setusercd(rs.getString("usercd"));
                emp.setusername(rs.getString("username"));
            }
            return emp;

        } catch (Exception e) {
            Employee emp = new Employee();
            emp.setusercd("nocd");
            emp.setusername("noname");
            return emp;
        }
    }
}
