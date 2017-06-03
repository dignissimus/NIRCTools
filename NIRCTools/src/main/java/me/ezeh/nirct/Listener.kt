package me.ezeh.nirct

import me.ezeh.nirct.event.ChatEvent
import me.ezeh.nirct.event.DisconnectEvent
import me.ezeh.nirct.event.QuitEvent


open class Listener {
    open fun onChat(event: ChatEvent) {}
    open fun onDisconnect(event: DisconnectEvent) {}
    open fun onJoin(event: QuitEvent) {}

}
