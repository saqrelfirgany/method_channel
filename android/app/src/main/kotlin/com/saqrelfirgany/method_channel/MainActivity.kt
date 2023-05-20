package com.saqrelfirgany.method_channel

import io.flutter.embedding.android.FlutterActivity
import io.flutter.plugin.common.MethodChannel
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.annotation.NonNull
import io.flutter.embedding.engine.FlutterEngine

class MainActivity : FlutterActivity() {
    private val batteryChannel = "saqrelfirgany/battery"

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        // Create a method channel with a name that matches the one in Flutter
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, batteryChannel).setMethodCallHandler {
            // Note: this method is invoked on the main thread.
                call, result ->
            // Check the method name
            if (call.method == "getBatteryLevel") {
                // Call the getBatteryLevel function and handle the result
                val batteryLevel = getBatteryLevel()
                if (batteryLevel != -1) {
                    result.success(batteryLevel)
                } else {
                    result.error("UNAVAILABLE", "Battery level not available.", null)
                }
            } else {
                // If the method name is not recognized, report that
                result.notImplemented()
            }
        }
    }

    // This function returns the battery level as an integer percentage
    private fun getBatteryLevel(): Int {
        // Declare a variable to store the battery level
        val batteryLevelPercent: Int
        // Check the Android version
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            // If Android version is Lollipop or higher, use the BatteryManager class
            // Use a lazy initialization for the battery manager
            val batteryManager: BatteryManager by lazy {
                getSystemService(BATTERY_SERVICE) as BatteryManager
            }
            // Get the battery level property from the battery manager
            batteryLevelPercent = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        } else {
            // If Android version is lower than Lollipop, use a broadcast receiver
            // Use a lazy initialization for the intent filter
            val batteryChangedFilter: IntentFilter by lazy {
                IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            }
            // Register a null receiver to get the current battery status
            val batteryStatusIntent = ContextWrapper(applicationContext).registerReceiver(null, batteryChangedFilter)
            // Get the battery level and scale from the intent extras
            val batteryLevel = batteryStatusIntent!!.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val batteryScale = batteryStatusIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            // Calculate the battery level percentage
            batteryLevelPercent = batteryLevel * 100 / batteryScale
        }

        // Return the battery level percentage
        return batteryLevelPercent
    }


}
