package org.flowforge.config.logger;

//package org.flowforge.config;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StartupLogger implements CommandLineRunner {

    @Override
    public void run(String... args) {

        log.info("=================================");
        log.info("FlowForge Backend Started");
        log.info("=================================");
    }
}