package com.tenji.service;
import com.tenji.dao.EmployeeDao;
import com.tenji.entity.Employee;
import org.springframework.stereotype.Service;

@Service("EmployeeService")
public class EmployeeService {

    private EmployeeDao employeeDao;

    public boolean addEmployee(Employee employee) {
        boolean flag=false;
        try{
            employeeDao.addEmployee(employee);
            flag=true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    public boolean updateEmployee(Employee employee) {
        boolean flag=false;
        try{
            employeeDao.updateEmployee(employee);
            flag=true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    public boolean deleteEmployee(String userCD) {
        boolean flag=false;
        try{
            employeeDao.deleteEmployee(userCD);
            flag=true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    public Employee findEmployeeByName(String userName) {
        try{
            Employee emp = employeeDao.findByName(userName);
            return emp;
        }catch (Exception ex){
            Employee emp = new Employee();
            emp.setusercd("nocdSer");
            emp.setusername("nonameSer");
            return emp;
        }


    }
}
