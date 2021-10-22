package com.simbirsoftintensiv.intensiv.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Version controller")
@RequestMapping("/version")
public class VersionRestController {

    @GetMapping
    public String getVersion() {
        String pom = null;
        try {
            pom = Files.lines(Paths.get("POM.xml")).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(pom);
        return "0.9.0";
    }
}
