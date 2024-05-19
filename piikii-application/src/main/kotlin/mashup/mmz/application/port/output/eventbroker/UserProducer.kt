package mashup.mmz.application.port.output.eventbroker

interface UserProducer {
    fun execute(event: Event)
}