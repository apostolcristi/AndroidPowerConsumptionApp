package com.example.androidpowerconsumptionapp
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager

object CallManager {
    private const val PREFS_NAME = "CallManagerPrefs"
    private const val KEY_LAST_DIALED_NUMBER = "lastDialedNumber"

    private lateinit var sharedPreferences: SharedPreferences

    fun callNumber(context: Context, phoneNumber: String) {
        // Salvăm numărul apelat
        saveLastDialedNumber(context, phoneNumber)

        // Inițiem apelul
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        telephonyManager.listen(object : PhoneStateListener() {
            override fun onCallStateChanged(state: Int, phoneNumber: String?) {
                super.onCallStateChanged(state, phoneNumber)
                if (state == TelephonyManager.CALL_STATE_IDLE) {
                    // Apelul s-a încheiat, putem face acțiuni suplimentare aici
                }
            }
        }, PhoneStateListener.LISTEN_CALL_STATE)

        val uri = "tel:$phoneNumber"
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse(uri)
        context.startActivity(intent)
    }

    private fun saveLastDialedNumber(context: Context, phoneNumber: String) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_LAST_DIALED_NUMBER, phoneNumber)
        editor.apply()
    }

    fun getLastDialedNumber(context: Context): String? {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_LAST_DIALED_NUMBER, null)
    }
}
