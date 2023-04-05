package com.example.flutter_poc_native_view

import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {
    private val CHANNEL = "com.example.my_flutter_app/native_view"

    private lateinit var nativeView: NativeView

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        nativeView = NativeView(this@MainActivity)
        flutterEngine.platformViewsController.registry.registerViewFactory("native_view", NativeViewFactory(nativeView))

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
            when (call.method) {
                "createView" -> {
                    result.success(0) // Devolvemos 0 como identificador
                }
                "updateText" -> {
                    val text = call.argument<String>("text")
                    text?.let {
                        nativeView.setText(it)
                        result.success(null)
                    } ?: result.error("INVALID_ARGUMENT", "Text is null", null)
                }
                else -> result.notImplemented()
            }
        }
    }
}

