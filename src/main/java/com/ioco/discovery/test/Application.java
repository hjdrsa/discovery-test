package com.ioco.discovery.test;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(
        title = "Discovery",
        description = "The goal of the Bank Balance and Dispensing System is to calculate and display the financial position to a client on an ATM screen.  "
                + "In addition, a client must also be able to make a request for a cash withdrawal.",
        version = "1.0.0",
        contact = @Contact(
                name = "Henry-John Davis",
                email = "henry.davis@eoh.com"
        )
))
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
