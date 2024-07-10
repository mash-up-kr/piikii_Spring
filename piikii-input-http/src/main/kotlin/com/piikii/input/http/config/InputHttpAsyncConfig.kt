import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@EnableAsync
@Configuration
class InputHttpAsyncConfig {
    @Bean(name = ["HookLoggerExecutor"])
    fun hookLoggerExecutor(): Executor {
        return ThreadPoolTaskExecutor().apply {
            corePoolSize = 2
            maxPoolSize = 2
            queueCapacity = 500
            setThreadNamePrefix("AsyncHookLoggerExecutor-")
            initialize()
        }
    }
}
