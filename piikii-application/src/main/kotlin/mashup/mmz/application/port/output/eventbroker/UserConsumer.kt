package mashup.mmz.application.port.output.eventbroker

interface UserConsumer {
    fun execute(): Event
}