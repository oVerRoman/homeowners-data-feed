package com.simbirsoftintensiv.intensiv;

import com.simbirsoftintensiv.intensiv.controller.LoginController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
 public class IntensivApplication {
    static final Logger log = LoggerFactory.getLogger(LoginController.class);

    public static void main(String[] args) {

        SpringApplication.run(IntensivApplication.class, args);
        log.info("Starting my application with {} args.", args.length);
    }
}
