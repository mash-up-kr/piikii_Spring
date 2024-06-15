package com.piikii.output.persistence.postgresql.persistence.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaAuditing
@EntityScan(basePackages = ["com.piikii"])
@EnableJpaRepositories(basePackages = ["com.piikii"])
class PostgreSQLConfig
