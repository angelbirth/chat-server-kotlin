@file:JvmName("Client")
package com.manciti.chatserver

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import java.io.PrintWriter
import java.net.InetSocketAddress
import java.net.Socket

fun main(args: Array<String>) = runBlocking<Unit> {
    val socket = Socket().apply {
        val addr = InetSocketAddress("localhost", 1253)
        connect(addr)
    }
    val out = PrintWriter(socket.getOutputStream())
    val `in` = socket.getInputStream().bufferedReader()
    val writer = launch(CommonPool) {
        while (true) {
            val inputLine = readLine()
            inputLine?.let {
                out.println(it)
                out.flush()
            }
        }
    }
    val reader = launch(CommonPool) {
        while (true) {
            val outputLine: String? = `in`.readLine()
            outputLine?.let { println(outputLine) }
        }
    }
    writer.join()
    reader.join()
}