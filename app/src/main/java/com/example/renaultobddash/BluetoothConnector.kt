
package com.example.renaultobddash

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID

class BluetoothConnector(private val address: String) {
    private val adapter = BluetoothAdapter.getDefaultAdapter()
    private var socket: BluetoothSocket? = null
    private var input: InputStream? = null
    private var output: OutputStream? = null

    suspend fun connect() {
        val device = adapter.getRemoteDevice(address)
        val uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
        socket = device.createRfcommSocketToServiceRecord(uuid)
        adapter.cancelDiscovery()
        socket?.connect()
        input = socket?.inputStream
        output = socket?.outputStream
    }

    suspend fun sendCommand(cmd: String) {
        output?.write((cmd + "\r").toByteArray())
        output?.flush()
    }

    suspend fun queryPID(pid: String): String? {
        sendCommand(pid)
        val buffer = ByteArray(1024)
        val sb = StringBuilder()
        var read: Int
        while (true) {
            read = input?.read(buffer) ?: -1
            if (read <= 0) break
            val s = String(buffer, 0, read)
            sb.append(s)
            if (s.contains('>')) break
        }
        return sb.toString()
    }

    fun close() {
        input?.close()
        output?.close()
        socket?.close()
    }
}
