package com.example.androidpowerconsumptionapp
import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

object PermissionManager {

    const val PERMISSION_REQUEST_SEND_SMS = 123
    const val PERMISSION_REQUEST_CALL_PHONE = 124

    fun requestSendSMSPermission(activity: Activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.SEND_SMS), PERMISSION_REQUEST_SEND_SMS)
        }
    }

    fun isSendSMSPermissionGranted(activity: Activity): Boolean {
        return ActivityCompat.checkSelfPermission(activity, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED
    }

    fun requestCallPhonePermission(activity: Activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CALL_PHONE), PERMISSION_REQUEST_CALL_PHONE)
        }
    }

    fun isCallPhonePermissionGranted(activity: Activity): Boolean {
        return ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED
    }
}
