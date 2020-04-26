/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tenji;

import com.tenji.entity.Employee;
import com.tenji.service.EmployeeService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jscience.physics.amount.Amount;
import org.jscience.physics.model.RelativisticModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.measure.quantity.Mass;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import static javax.measure.unit.SI.KILOGRAM;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Controller
@SpringBootApplication
public class Main {

  /*@Value("${spring.datasource.url}")
  private String dbUrl;*/

  /*@Autowired
  private DataSource dataSource;*/
  ApplicationContext appConf = new AnnotationConfigApplicationContext(AppConfig.class);

    @Autowired
    AppConfig appConfig;

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Main.class, args);
  }

  @RequestMapping("/")
  String index() {
    return "index";
  }
  @RequestMapping("/hello")
  String hello(Map<String, Object> model) {
    RelativisticModel.select();
    String energy = System.getenv().get("ENERGY");
    if (energy == null) {
       energy = "12 GeV";
    }
    Amount<Mass> m = Amount.valueOf(energy).to(KILOGRAM);
    model.put("science", "E=mc^2: " + energy + " = "  + m.toString());
    return "hello";
  }
  @RequestMapping("/db")
  String db(Map<String, Object> model) {
    try (Connection connection = appConfig.dataSource().getConnection()) {
      Statement stmt = connection.createStatement();
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
      stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
      ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

      ArrayList<String> output = new ArrayList<String>();
      while (rs.next()) {
        output.add("Read from DB: " + rs.getTimestamp("tick"));
      }

      model.put("records", output);
      return "db";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }
  }
    private EmployeeService employeeService;

    @RequestMapping(value = "/addEmployee")
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

    @RequestMapping(value = "/updateEmployee")
    public boolean updateEmployee( Employee employee) {
        System.out.println("开始更新...");
        return employeeService.updateEmployee(employee);
    }

    @RequestMapping(value = "/deleteEmployee")
    public boolean deleteEmployee(@RequestParam(value = "userName", required = true) String userCD) {
        System.out.println("开始删除...");
        return employeeService.deleteEmployee(userCD);
    }

    @RequestMapping("/findByUserName")
    String  findByUserName(Map<String, Object> model) {
        System.out.println("Start123...");
        try{
            System.out.println("employeeService_Start...");
            //Employee emp =  employeeService.findEmployeeByName("天時くん００１");
            Employee emp = new Employee();
            try (Connection connection = appConfig.dataSource().getConnection()) {
                Statement stmt = connection.createStatement();
                ResultSet rs =
                        stmt.executeQuery(
                                "SELECT UserCD,UserName,Sex FROM m_Employee where UserName='天時くん００１'"
                        );

                while (rs.next()) {
                    emp.setusercd(rs.getString("usercd"));
                    emp.setusername(rs.getString("username"));
                }
            }catch (Exception ex){

            }
            System.out.println("employeeService_End...");
            ArrayList<String> output = new ArrayList<String>();
            output.add("usercd: " + emp.getusercd());
            output.add("username: " + emp.getusername());
            model.put("records", output);

        }catch(Exception e){
            System.out.println("error...");
            System.out.println(e.getMessage());
            model.put("message", e.getMessage());
            return "error";
        }

        return "findByUserName";
    }
    @RequestMapping("/input2")
     String input2(Map<String, Object> model) {
         return "input";
    }
    @RequestMapping(path = "/input1")
     String  input(@ModelAttribute Employee employee,Map<String, Object> model) {
        try{
            System.out.println("employeeService_Start...");
            System.out.println("employeeService_usercd..."+ employee.getusercd());
            //Employee emp =  employeeService.findEmployeeByName("天時くん００１");
            try (Connection connection = appConfig.dataSource().getConnection()) {
                Statement stmt = connection.createStatement();
                ResultSet rs =
                        stmt.executeQuery(
                                "SELECT UserCD,UserName,Sex FROM m_Employee where Usercd='" + employee.getusercd() + "'"
                        );

                while (rs.next()) {
                    System.out.println("employeeService_End...");
                    ArrayList<String> output = new ArrayList<String>();
                    output.add("usercd: " + rs.getString("usercd"));
                    output.add("username: " + rs.getString("username"));
                    model.put("records", output);
                }
            }catch (Exception ex){

            }
            System.out.println("employeeService_End...");

        }catch(Exception e){
            System.out.println("error...");
            System.out.println(e.getMessage());
        }
        return "input1";
    }
/*
  @Bean
  public DataSource dataSource() throws SQLException {
    if (dbUrl == null || dbUrl.isEmpty()) {
      return new HikariDataSource();
    } else {
      HikariConfig config = new HikariConfig();
      config.setJdbcUrl(dbUrl);
      return new HikariDataSource(config);
    }
  }*/

}
