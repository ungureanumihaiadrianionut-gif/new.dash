
package com.example.renaultobddash

class DtcManager(private val connector: BluetoothConnector) {
    suspend fun readDTCs(): List<String> {
        val raw = connector.queryPID("03") ?: return emptyList()
        val tokens = raw.split(Regex("[^0-9A-Fa-f]+"))
        val dtcs = mutableListOf<String>()
        for (i in tokens.indices) {
            if (tokens[i].equals("43", ignoreCase = true) && i+2 < tokens.size) {
                val A = tokens.getOrNull(i+1)
                val B = tokens.getOrNull(i+2)
                if (A != null && B != null) {
                    dtcs.add(A + B)
                }
            }
        }
        return dtcs
    }

    suspend fun clearDTCs(): Boolean {
        connector.sendCommand("04")
        return true
    }
}
