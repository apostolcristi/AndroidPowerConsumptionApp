package com.example.androidpowerconsumptionapp

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var phoneNumber: String
    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private lateinit var callButton: Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var logData: LogData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logData = LogData(this)
        val startNetworkButton = findViewById<Button>(R.id.startNetworkButton)
        val stopNetworkButton = findViewById<Button>(R.id.stopNetworkButton)
        val enableAirplaneModeButton = findViewById<Button>(R.id.enableAirplaneModeButton)
        val disableAirplaneModeButton = findViewById<Button>(R.id.disableAirplaneModeButton)
        startButton = findViewById(R.id.startButton)
        stopButton = findViewById(R.id.stopButton)
        callButton = findViewById(R.id.callButton)
        val editTextPhoneNumber = findViewById<EditText>(R.id.editTextPhoneNumber)
        sharedPreferences = this.getSharedPreferences("PHONE_NUMBER", Context.MODE_PRIVATE)
        phoneNumber = sharedPreferences.getString("phoneNumber", "0728400117") ?: "0728400117"
        editTextPhoneNumber.setText(phoneNumber)

        val clickListeners = ButtonClickListeners(this, startButton, stopButton, callButton, phoneNumber)
        startButton.setOnClickListener(clickListeners)
        stopButton.setOnClickListener(clickListeners)
        callButton.setOnClickListener(clickListeners)

        if (!PermissionManager.isSendSMSPermissionGranted(this)) {
            PermissionManager.requestSendSMSPermission(this)
        }

        if (!PermissionManager.isCallPhonePermissionGranted(this)) {
            PermissionManager.requestCallPhonePermission(this)
        }
        startNetworkButton.setOnClickListener {
            // Code to start network data
            logData.startNetworkData()
        }

        stopNetworkButton.setOnClickListener {
            // Code to stop network data
            logData.stopNetworkData()
        }

        enableAirplaneModeButton.setOnClickListener {
            // Code to enable airplane mode
            logData.enableAirplaneMode()
        }

        disableAirplaneModeButton.setOnClickListener {
            // Code to disable airplane mode
            logData.disableAirplaneMode()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PermissionManager.PERMISSION_REQUEST_SEND_SMS) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startSendingSMS()
            }
        } else if (requestCode == PermissionManager.PERMISSION_REQUEST_CALL_PHONE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val clickListeners = ButtonClickListeners(this, startButton, stopButton, callButton, phoneNumber )
                clickListeners.makeCall()
            }
        }
    }

    private fun startSendingSMS() {
        val clickListeners = ButtonClickListeners(this, startButton, stopButton, callButton, phoneNumber)
        clickListeners.startSendingSMS()
    }

}
