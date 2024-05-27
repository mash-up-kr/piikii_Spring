package com.piikii.bootstrap

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.piikii"])
class BootstrapApplication

fun main(args: Array<String>) {
     System.setProperty("spring.config.location", "classpath:/database-config/,classpath:/cache-config/,classpath:/broker-config/");
    runApplication<BootstrapApplication>(*args)
}
