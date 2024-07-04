import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@Configuration
@EnableAsync
class AsyncConfig {
    @Bean(name = ["hookLoggerTaskExecutor"])
    fun taskExecutorForHookLogger(): Executor =
        ThreadPoolTaskExecutor().apply {
            corePoolSize = 2
            maxPoolSize = 2
            queueCapacity = 500
            setThreadNamePrefix("AsyncHookLogger-")
            initialize()
        }

    @Bean(name = ["StorageTaskExecutor"])
    fun taskExecutorForExternalStorage(): Executor =
        ThreadPoolTaskExecutor().apply {
            corePoolSize = 2
            maxPoolSize = 2
            queueCapacity = 500
            setThreadNamePrefix("AsyncStorageExecutor-")
            initialize()
        }
}
