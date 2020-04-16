package com.tenji.dao;

import com.tenji.entity.Employee;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EmployeeDao {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Autowired
    private DataSource dataSource;
    /**
     * 用户数据新增
     */
    public void addEmployee(Employee employee){
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("INSERT INTO m_Employee(UserCD,UserName,Sex) VALUES (#{userCD},#{userName},#{sex})");
        } catch (Exception e) {

        }
    }

    /**
     * 用户数据修改
     */
    public void updateEmployee(Employee employee){
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("update m_Employee set UserName=#{UserName},Sex=#{sex} where UserCD=#{userCD}");
        } catch (Exception e) {

        }
    }

    /**
     * 用户数据删除
     */
    //Delete("delete from m_Employee where UserCD=#{userCD}")
    public void deleteEmployee(String userCD){

    }

    /**
     * 根据用户名称查询用户信息
     *
     */
    //@Select("SELECT UserCD,UserName,Sex FROM m_Employee where UserName=#{userName}")
    public Employee findByName(String userName){
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT UserCD,UserName,Sex FROM m_Employee where UserName=#{userName}");

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

    @Bean
    public DataSource dataSource() throws SQLException {
        if (dbUrl == null || dbUrl.isEmpty()) {
            return new HikariDataSource();
        } else {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(dbUrl);
            return new HikariDataSource(config);
        }
    }
}
