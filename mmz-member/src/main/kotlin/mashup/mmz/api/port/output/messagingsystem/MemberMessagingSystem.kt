package mashup.mmz.api.port.output.messagingsystem

interface MemberMessagingSystem {
    fun produce(event: Event)
    fun consume(): Event
}