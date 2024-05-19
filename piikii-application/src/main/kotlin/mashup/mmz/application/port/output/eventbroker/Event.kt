package mashup.mmz.application.port.output.eventbroker

interface Event {

    fun getTopic(): Event
}