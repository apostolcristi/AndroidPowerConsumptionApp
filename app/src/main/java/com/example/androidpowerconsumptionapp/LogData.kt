package com.example.androidpowerconsumptionapp

import android.content.Context
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class LogData(private val context: Context) {

    private val logFileName = "logs.txt"
    private val logFile: File = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), logFileName)
    @RequiresApi(Build.VERSION_CODES.O)
    fun startNetworkData() {
        appendToLogFile("Network data enabled")
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun stopNetworkData() {
        appendToLogFile("Network data disabled")
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun enableAirplaneMode() {
        appendToLogFile("Airplane mode enabled")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun disableAirplaneMode() {
        appendToLogFile("Airplane mode disabled")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun appendToLogFile(message: String) {
        val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS", Locale.getDefault()))
        val logMessage = "[$timestamp] $message\n"
        FileWriter(logFile, true).use { writer ->
            writer.append(logMessage)
        }
    }
}
