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

import org.jscience.physics.amount.Amount;
import org.jscience.physics.model.RelativisticModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.measure.quantity.Mass;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import static javax.measure.unit.SI.KILOGRAM;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Controller
@SpringBootApplication
public class Main {

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

}
