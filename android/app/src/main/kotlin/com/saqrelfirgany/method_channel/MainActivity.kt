package com.saqrelfirgany.method_channel

import android.os.Build.VERSION
import io.flutter.embedding.android.FlutterActivity
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity() {
    private val batteryChannel = "saqrelfirgany/battery"

    private lateinit var channel: MethodChannel

    @Override
    fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        channel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, batteryChannel)
        channel.setMethodCallHandler { call, result ->
            if (call.method == "getBatteryLevel") {
                val arguments = call.arguments as Map<*, *>
                val name = arguments["name"]
                val batteryLevel = getBatteryLevel()
                result.success(batteryLevel)
            }
        }
    }

    private fun getBatteryLevel(): Int {
        val batteryLevel:Int
        if (VERSION.SDK_INT >= VERSION.CODEN)
    }
}
