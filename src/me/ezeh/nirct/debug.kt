package me.ezeh.nirct

import me.ezeh.nirct.examples.LetterCounter

fun main(args: Array<String>) {
    val client = IrcClient("irc.spi.gt", 6667)
    client.log = false
    client.start()
    client.command("USER", "nirct", "8", "*", "NIRCT TEST")
    client.setNick("NIRCTtest")
    client.addLoginHook {
        client.joinChannel("#spammy23")
        client.sendMessage("test", "#spammy23")
    }
    client.addListener(LetterCounter())
}