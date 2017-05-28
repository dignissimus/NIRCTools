package me.ezeh.nirct


open class Listener {
    open fun onChat(event: ChatEvent) {}
    fun onDisconnect(event: DisconnectEvent) {}
    fun onJoin(event: QuitEvent) {}

}
