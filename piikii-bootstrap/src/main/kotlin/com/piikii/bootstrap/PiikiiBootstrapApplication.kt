package com.piikii.bootstrap

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.piikii"])
@ConfigurationPropertiesScan("com.piikii.output.storage.ncp.config", "com.piikii.output.web.lemon.config")
class PiikiiBootstrapApplication

fun main(args: Array<String>) {
    runApplication<PiikiiBootstrapApplication>(*args)
}
