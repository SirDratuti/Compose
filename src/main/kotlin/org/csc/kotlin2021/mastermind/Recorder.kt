package org.csc.kotlin2021.mastermind

import com.google.gson.Gson
import java.io.FileReader
import java.io.FileWriter

object Recorder {
    private const val fileName = "records.txt"
    private val gson = Gson()
    var userRecords: Records = getRecords()

    fun addRecord(gameResult: String) {
        userRecords.playerResults.add(gameResult)
        save()
    }

    private fun getRecords(): Records {
        try {
            FileReader(fileName).use {
                return gson.fromJson(it.readLines().joinToString(""), Records::class.java)
            }
        } catch (e: Exception) {
            return Records(mutableListOf())
        }
    }

    private fun save() {
        try {
            FileWriter(fileName).use {
                it.write(gson.toJson(userRecords))
            }
        } catch (ignored: Exception) {
        }
    }

    data class Records(val playerResults: MutableList<String>)
}
