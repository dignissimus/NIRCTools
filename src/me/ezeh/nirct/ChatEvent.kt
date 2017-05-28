package me.ezeh.nirct


class ChatEvent(val client: IrcClient, val sender: String, val message: String, var receiver: String) {

}
