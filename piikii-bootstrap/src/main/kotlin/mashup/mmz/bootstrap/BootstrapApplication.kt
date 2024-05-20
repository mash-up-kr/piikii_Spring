package mashup.mmz.bootstrap

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BootstrapApplication

fun main(args: Array<String>) {
    runApplication<BootstrapApplication>(*args)
}
