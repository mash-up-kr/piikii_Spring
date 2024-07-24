package com.piikii.bootstrap.annotation

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@ComponentScan(basePackages = ["com.piikii.*"])
@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration
@ContextConfiguration(classes = [TestConfiguration::class])
@ActiveProfiles("dev")
annotation class AcceptanceTest
