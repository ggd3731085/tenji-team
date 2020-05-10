package com.tenji.service;
import com.tenji.AppConfig;
import com.tenji.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

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

    public List<Employee>  findEmployeeList(Employee empPara) {
        try (Connection connection = appConfig.dataSource().getConnection()) {

            List<Employee> emplist = new ArrayList<Employee>();

            Statement stmt = connection.createStatement();

            String strSql;
            if(getWhere(empPara) != "") {
                strSql = "SELECT UserCD,UserName,Sex FROM m_Employee where " + getWhere(empPara);
                System.out.println("strSql.:" + strSql);
            }else{
                strSql = "SELECT UserCD,UserName,Sex FROM m_Employee ";
            }
            ResultSet rs = stmt.executeQuery(strSql);

             while (rs.next()) {
                 Employee emp = new Employee();
                 emp.setusercd(rs.getString("usercd"));
                 emp.setusername(rs.getString("username"));
                 emplist.add(emp);
             }

            return emplist;

        } catch (Exception e) {
            List<Employee> emplist = new ArrayList<Employee>();
            Employee emp = new Employee();
            emp.setusercd("nocd");
            emp.setusername("noname");
            emplist.add(emp);
            return emplist;
        }
    }

    //レコード検索条件：WHERE句設定
    private String getWhere(Employee empPara){
        List<String> param = new ArrayList<String>();

        //Usercd
        if(empPara.getusercd() != ""){
            param.add("Usercd LIKE %" + empPara.getusercd() + "%");
        }
        //UserName
        if(empPara.getusername() != ""){
            param.add("UserName LIKE %" + empPara.getusername() + "%");
        }

        if(param.size()>0){
            return String.join( " AND ",param);
        }else{
            return "";
        }
    }
}
