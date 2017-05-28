package me.ezeh.nirct

import me.ezeh.nirct.event.ChatEvent
import me.ezeh.nirct.protocol.IrcConnection
import me.ezeh.nirct.protocol.IrcResponse
import java.util.regex.Pattern


class IrcClient(var connection: IrcConnection) {
    constructor(ip: String, port: Int) : this(IrcConnection(ip, port))

    var channels: MutableList<String> = ArrayList()
    var log: Boolean = true
    var listeners: MutableList<Listener> = ArrayList()
    private val loginListeners = ArrayList<() -> Any>()
    private var name: String = "NIRCTools"
    private var user: String = "NIRCTools"
    private var pass: CharArray = CharArray(0)
    private var nick: String = "NIRCTools"
    fun getNick(): String {
        return nick
    }

    fun addLoginHook(hook: () -> Any) {
        loginListeners.add(hook)
    }

    fun addLoginHook(hook: Runnable) {
        loginListeners.add { hook.run() }
    }

    private fun runLoginHooks() {
        for (listener in loginListeners) {
            listener()
        }
    }

    fun start() {
        val t = Thread(Runnable { listen() })
        t.start()
    }

    fun command(cmd: String, vararg args: String) {
        var parameters = ""
        for (arg in args) {
            parameters += " "
            if (arg.contains(' ')) parameters += ":$arg:" else parameters += arg
        }
        connection.sendText("$cmd ${parameters.substring(0)}") // Substring to remove the first space
    }

    fun me(action: String, person: String) {
        sendMessage(String.format("\u0001ACTION %s\u0001", action), person)
    }

    fun setNick(nick: String) {
        this.nick = nick
        connection.sendText("NICK " + nick)
    }

    fun setName(name: String) {
        this.name = name
        connection.sendText("NAME " + name)
    }

    fun setUser(name: String) {
        this.user = name
        connection.sendText("USER " + name)
    }

    fun setPass(pass: String) {
        this.pass = pass.toCharArray()
        connection.sendText("PASS " + pass)
    }

    fun setPass(pass: CharArray) {
        var f = ""
        this.pass = CharArray(pass.size)
        for (c in pass) {
            f += c
        }
        this.pass = pass
        connection.sendText("PASS " + f)
    }

    fun joinChannel(channel: String) {
        if (!channels.contains(channel))
            channels.add(channel)
        connection.sendText("JOIN " + channel)

    }

    fun leaveChannel(channel: String) {
        if (channels.contains(channel))
            channels.remove(channel)
        connection.sendText("PART " + channel)
    }

    fun sendMessage(msg: String, person: String) {
        connection.sendText("PRIVMSG $person :$msg")
    }

    private fun listen() {
        var read: String?
        while (true) {
            read = connection.readText()
            if (read == null) return
            if (read != "") {
                if (read.startsWith("PING")) {
                    connection.sendText(read.replace("PING", "PONG"))
                    continue
                }
                if (log) println(read)
                process(this.parse(read))
            }
        }
    }

    fun addListener(listener: Listener) {
        listeners.add(listener)
    }

    fun callChatEvent(e: ChatEvent) {
        for (listener in listeners) {
            listener.onChat(e)
        }
    }

    fun process(response: IrcResponse) {
        val cmd = response.command.toLowerCase()
        val source = response.source.substring(1)// This is because the source should start with a colon, I don't want the colon, therefore I remove the colon using String#substring
        //Used to be used, Now the bot is expanding //if (!source.contains("!")) return
        //System.out.println("NICK: "+sendNICK+"\tText: "+response.EA+"\tName: "+response.FA[0]);
        val args = ArrayList<String>(response.args.asList())
        if (cmd == "001") {
            runLoginHooks()
        }
        if (cmd == "privmsg") {
            println(source)
            val sendNICK = source.split("!~".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
            val sendUser = source.split("@".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0].split("!~".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
            callChatEvent(ChatEvent(this, sendNICK, args.subList(1, args.size).joinToString(" "), args[0]))
        }
        if (cmd == "QUIT") {
            //user has left
        }
    }

    fun parse(message: String): IrcResponse {
        // :Macha!~macha@unaffiliated/macha PRIVMSG #botwar :Test response
        // Old regex //val pattern = Pattern.compile("(?<source>:.+?!~.+?@.+?) (?<command>[A-Za-z]+) (?<args>.*)")
        val pattern = Pattern.compile("(?<source>:(.+?!~.+?@.+?)|.+?) (?<command>[\\dA-Za-z]+) (?<args>.*)")// Better regex
        val matcher = pattern.matcher(message)
        if (matcher.matches()) {
            try {
                val source = matcher.group("source")
                val command = matcher.group("command")
                val rawArgs = matcher.group("args")
                return IrcResponse(message, source, command, parseArgs(rawArgs))
            } catch (e: IllegalStateException) {
                println("Malformed response: " + message)
            }
        }

        return IrcResponse("", "", "", emptyArray())
        /*var message = message
        val raw = message
        message = message.substring(1)//remove the preceding colon
        val split = message.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val source = split[0]
        val command = split[1]
        val nmessage = Arrays.copyOfRange(split, 2, split.size)
        val argS = TextUtils.join(" ", nmessage)
        val EA = argS.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[argS.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().size - 1]
        val FA = Arrays.copyOf(argS.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray(), argS.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().size - 1)
        */


        //return IrcResponse(message, source, command, parseArgs(rawArgs))
    }

    private fun parseArgs(rawInput: String): Array<String> {
        var input = rawInput
        val args = ArrayList<String>()
        var current = ""
        var withSpaces = false
        while (input.startsWith(" ")) {
            input = input.substring(0)
        }
        for (char in input) {
            if (char == ' ' && !withSpaces) {
                args.add(current)
                current = ""
            } else if (char == ':') {
                withSpaces = !withSpaces
            } else {
                current += char
            }
        }
        if (current != "") args.add(current)
        return args.toArray(emptyArray<String>())
    }
}

