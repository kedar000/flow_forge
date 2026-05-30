package org.flowforge.test;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {

        log.info("Test endpoint called");

        return "FlowForge running";
    }

    @GetMapping("/error")
    public String error() {

        log.info("Error endpoint called");

        throw new RuntimeException("Something went wrong");
    }
}
