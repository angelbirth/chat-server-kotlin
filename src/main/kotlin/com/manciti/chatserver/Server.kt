@file:JvmName("Server")

package com.manciti.chatserver

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import java.io.PrintWriter
import java.net.ServerSocket

fun main(args: Array<String>) = runBlocking<Unit> {
    val socket = ServerSocket(1253)
    while (true) {
        val client = socket.accept()
        val a = launch(CommonPool) {
            val `in` = client.getInputStream().bufferedReader()
            var inputLine: String? = null
            while (true) {
                inputLine = `in`.readLine()
                inputLine?.let {
                    println(it)
                }
            }
        }
        val b = launch(CommonPool) {
            val out = PrintWriter(client.getOutputStream(), true)
            var outputLine: String? = null
            while (true) {
                outputLine = readLine()
                outputLine?.let {
                    out.println(it)
                    out.flush()
                }
            }
        }
        a.join()
        b.join()
    }
}