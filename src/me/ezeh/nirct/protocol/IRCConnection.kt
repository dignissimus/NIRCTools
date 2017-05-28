package me.ezeh.nirct.protocol

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.Exception
import java.net.Socket


class IrcConnection(private val socket: Socket) {
    val ip: String = socket.inetAddress.hostName
    val port: Int = socket.port
    private var bw: BufferedWriter = BufferedWriter(OutputStreamWriter(socket.getOutputStream()))
    private var br: BufferedReader = BufferedReader(InputStreamReader(socket.getInputStream()))


    constructor(ip: String, port: Int) : this(Socket(ip, port))

    fun isConnected(): Boolean = socket.isConnected


    private fun setup() {
        try {
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun sendText(text: String) {
        try {
            println("Request: " + text)
            bw.write(text + "\r\n")
            bw.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun readText(): String? {
        try {

            return br.readLine()
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }

    }
}