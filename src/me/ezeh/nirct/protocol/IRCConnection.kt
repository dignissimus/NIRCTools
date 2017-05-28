package me.ezeh.nirct.protocol

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.Exception
import java.net.Socket


class IrcConnection {
    val ip: String
    val port: Int
    private val socket: Socket
    private var bw: BufferedWriter? = null
    private var br: BufferedReader? = null


    constructor(ip: String, port: Int) {
        this.ip = ip
        this.port = port
        socket = Socket(ip, port)
        setup()
    }

    constructor(socket: Socket) {
        ip = socket.inetAddress.hostAddress
        port = socket.port
        assert(socket.isConnected)
        this.socket = socket
        setup()
    }

    fun isConnected(): Boolean = socket.isConnected


    fun setup() {
        try {
            this.bw = BufferedWriter(OutputStreamWriter(this.socket.getOutputStream()))
            this.br = BufferedReader(InputStreamReader(this.socket.getInputStream()))
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun sendText(text: String) {
        try {
            println("Request: " + text)
            bw?.write(text + "\r\n")
            bw?.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun readText(): String? {
        try {

            return br?.readLine()
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }

    }
}