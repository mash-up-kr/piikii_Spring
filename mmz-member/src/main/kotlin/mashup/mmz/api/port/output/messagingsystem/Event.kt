package mashup.mmz.api.port.output.messagingsystem

interface Event {
    fun getTopic(): String
}