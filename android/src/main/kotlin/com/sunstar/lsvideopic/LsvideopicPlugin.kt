package com.sunstar.lsvideopic

import android.content.Context
import androidx.annotation.NonNull;
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

/** LsvideopicPlugin */
public class LsvideopicPlugin : FlutterPlugin, MethodCallHandler {
    var context: Context? = null
    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        staticContext = flutterPluginBinding.applicationContext;
        val channel = MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "lsvideopic")
        channel.setMethodCallHandler(LsvideopicPlugin());
    }

    companion object {
        var staticContext: Context? = null
        @JvmStatic
        fun registerWith(registrar: Registrar) {
            val channel = MethodChannel(registrar.messenger(), "lsvideopic")
            staticContext = registrar.context()
            channel.setMethodCallHandler(LsvideopicPlugin())
        }
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        when (call.method) {
            "getThumbPic" -> {
                staticContext?.run {
                    VideoUtil.getFrameImg(this, call.argument<String>("videoPath")!!, call.argument<Long>("second")!!, result)
                }
            }
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    }
}
