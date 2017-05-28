package me.ezeh.nirct

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.Exception
import java.net.Socket


class IrcConnection {
    val ip: String
    val port: Int
    private val socket: java.net.Socket
    private var bw: java.io.BufferedWriter? = null
    private var br: java.io.BufferedReader? = null


    constructor(ip: String, port: Int) {
        this.ip = ip
        this.port = port
        socket = java.net.Socket(ip, port)
        setup()
    }

    constructor(socket: java.net.Socket) {
        ip = socket.inetAddress.hostAddress
        port = socket.port
        assert(socket.isConnected)
        this.socket = socket
        setup()
    }

    fun isConnected(): Boolean = socket.isConnected


    fun setup() {
        try {
            this.bw = java.io.BufferedWriter(java.io.OutputStreamWriter(this.socket.getOutputStream()))
            this.br = java.io.BufferedReader(java.io.InputStreamReader(this.socket.getInputStream()))
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

    }

    fun sendText(text: String) {
        try {
            println("Request: " + text)
            bw?.write(text + "\r\n")
            bw?.flush()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

    }

    fun readText(): String? {
        try {

            return br?.readLine()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            return ""
        }

    }
}