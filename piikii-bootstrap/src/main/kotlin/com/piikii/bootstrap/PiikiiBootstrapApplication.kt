package com.piikii.bootstrap

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.piikii"])
class PiikiiBootstrapApplication

fun main(args: Array<String>) {
    runApplication<PiikiiBootstrapApplication>(*args)
}
