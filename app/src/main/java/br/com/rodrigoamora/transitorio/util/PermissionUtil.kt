package br.com.rodrigoamora.transitorio.util

import android.app.Activity
import androidx.core.app.ActivityCompat

class PermissionUtil {
    companion object {
        private const val PERMISSION_REQUEST_CODE = 200

        fun requestPermissions(activity: Activity, permissions: Array<String>) {
            ActivityCompat.requestPermissions(activity, permissions, PERMISSION_REQUEST_CODE)
        }

        fun requestPermission(activity: Activity, permission: String) {
            ActivityCompat.requestPermissions(activity, arrayOf(permission), PERMISSION_REQUEST_CODE)
        }
    }
}