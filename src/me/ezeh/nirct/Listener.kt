package me.ezeh.nirct

import me.ezeh.nirct.event.ChatEvent
import me.ezeh.nirct.event.DisconnectEvent
import me.ezeh.nirct.event.QuitEvent


open class Listener {
    open fun onChat(event: ChatEvent) {}
    fun onDisconnect(event: DisconnectEvent) {}
    fun onJoin(event: QuitEvent) {}

}
