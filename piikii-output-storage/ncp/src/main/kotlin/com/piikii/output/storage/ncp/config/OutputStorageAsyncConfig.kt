import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@EnableAsync
@Configuration
class OutputStorageAsyncConfig {
    @Bean(name = ["AsyncStorageExecutor"])
    fun storageExecutor(): Executor {
        return ThreadPoolTaskExecutor().apply {
            corePoolSize = 7
            maxPoolSize = 30
            queueCapacity = 0
            setThreadNamePrefix("AsyncStorageExecutor-")
            initialize()
        }
    }
}
