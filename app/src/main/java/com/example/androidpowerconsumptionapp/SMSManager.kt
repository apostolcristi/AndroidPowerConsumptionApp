package com.example.androidpowerconsumptionapp
import android.content.Context
import android.telephony.SmsManager

object SMSManager {
    fun sendSMS(context: Context, phoneNumber: String, message: String) {
        try {
            val smsManager: SmsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
