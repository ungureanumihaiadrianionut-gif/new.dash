
package com.example.renaultobddash

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.*

class ObdManager(private val context: Context) {
    private var connector: BluetoothConnector? = null
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val _rpm = MutableStateFlow(0f)
    val rpm: StateFlow<Float> = _rpm
    private val _coolant = MutableStateFlow(0f)
    val coolant: StateFlow<Float> = _coolant
    private val _boost = MutableStateFlow(0f)
    val boost: StateFlow<Float> = _boost

    fun connect(address: String) {
        connector = BluetoothConnector(address)
        scope.launch {
            try {
                connector?.connect()
                initializeELM()
                pollLoop()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun initializeELM() {
        connector?.sendCommand("ATZ")
        delay(500)
        connector?.sendCommand("ATE0")
        delay(200)
        connector?.sendCommand("0100")
    }

    private suspend fun pollLoop() {
        while (isActive) {
            try {
                val r = connector?.queryPID("010C") // RPM
                r?.let { _rpm.value = parseRPM(it) }
                val t = connector?.queryPID("0105") // coolant
                t?.let { _coolant.value = parseTemp(it) }
                val p = connector?.queryPID("010B") // MAP candidate
                p?.let { _boost.value = parseMap(it) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            delay(500)
        }
    }

    fun close() { scope.cancel(); connector?.close() }

    private fun parseRPM(raw: String): Float {
        val bytes = raw.split(Regex("[^0-9A-Fa-f]+"))
        if (bytes.size >= 4) {
            val A = bytes[2].toInt(16)
            val B = bytes[3].toInt(16)
            return ((A*256)+B)/4f
        }
        return 0f
    }
    private fun parseTemp(raw: String): Float {
        val bytes = raw.split(Regex("[^0-9A-Fa-f]+"))
        if (bytes.size >= 3) {
            val A = bytes[2].toInt(16)
            return (A - 40).toFloat()
        }
        return 0f
    }
    private fun parseMap(raw: String): Float {
        val bytes = raw.split(Regex("[^0-9A-Fa-f]+"))
        if (bytes.size >= 3) {
            val A = bytes[2].toInt(16)
            return A.toFloat()
        }
        return 0f
    }
}
