package com.quinyx.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.quinyx.test"})
@EnableJpaRepositories(basePackages = {"com.quinyx.test.repositories"},
    enableDefaultTransactions = false)
public class PetsApplication {

  public static void main(String[] args) {
    SpringApplication.run(PetsApplication.class, args);
  }
}