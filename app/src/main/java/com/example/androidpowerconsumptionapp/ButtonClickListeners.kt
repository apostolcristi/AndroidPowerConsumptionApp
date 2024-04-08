package com.example.androidpowerconsumptionapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.io.File
import java.io.FileWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.concurrent.timer

class ButtonClickListeners(
    private val context: Context,
    private val startButton: Button,
    private val stopButton: Button,
    private val callButton: Button,
    private val phoneNumber: String
) : View.OnClickListener {

    private var timer: Timer? = null
    private var smsCount = 0
    private var callCount = 0
    private lateinit var sharedPreferences: SharedPreferences

    private val logFileName = "logs.txt"
    private val logFile: File = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), logFileName)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View) {
        when (v.id) {
            startButton.id -> startSendingSMS()
            stopButton.id -> stopSendingSMS()
            callButton.id -> makeCall()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun startSendingSMS() {
        timer = timer(period = 1000) {
            val message = "Hello, this is a test SMS!"
            SMSManager.sendSMS(context, phoneNumber, message)
            smsCount++
            appendToLogFile("SMS sent: $smsCount")
        }
    }

    private fun stopSendingSMS() {
        timer?.cancel()
        saveSMSCount()
        showSMSCount()
    }

    private fun saveSMSCount() {
        sharedPreferences = context.getSharedPreferences("SMS_COUNT", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("count", smsCount)
        editor.apply()
    }

    private fun showSMSCount() {
        val count = sharedPreferences.getInt("count", 0)
        val message = "Number of SMS sent: $count"
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun makeCall() {
        val uri = "tel:$phoneNumber"
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse(uri)
        context.startActivity(intent)
        callCount++
        appendToLogFile("Call made: $callCount")
        saveCallCount()
        showCallCount()
    }

    private fun saveCallCount() {
        sharedPreferences = context.getSharedPreferences("CALL_COUNT", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("count", callCount)
        editor.apply()
    }

    private fun showCallCount() {
        val count = sharedPreferences.getInt("count", 0)
        val message = "Number of calls made: $count"
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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
