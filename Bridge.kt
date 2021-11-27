interface Switch {
    var appliance: Appliance
    fun turnOn()
}
interface Appliance {
    fun run()
}
class RemoteControl(override var appliance: Appliance) : Switch {
    override fun turnOn() = appliance.run()
}
class Lamp : Appliance {
    override fun run() = println("The lamp is on")
}
class VacuumCleaner : Appliance {
    override fun run() = println("The vacuum cleaner is on")
}
fun main(args: Array<String>) {
    var lampRemoteControl = RemoteControl(appliance = Lamp())
    lampRemoteControl.turnOn()
    var vacuumCleanerRemoteControl = RemoteControl(appliance = VacuumCleaner())
    vacuumCleanerRemoteControl.turnOn()
}