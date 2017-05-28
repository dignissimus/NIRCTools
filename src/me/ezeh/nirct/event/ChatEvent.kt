package me.ezeh.nirct.event

import me.ezeh.nirct.IrcClient


class ChatEvent(val client: IrcClient, val sender: String, val message: String, var receiver: String) {

}
