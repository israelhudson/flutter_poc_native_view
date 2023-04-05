package com.example.flutter_poc_native_view

import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel


class MainActivity: FlutterActivity() {
    private val CHANNEL = "com.example.my_flutter_app/native_view"

    // Adicione esta linha para criar uma variável lateinit para o MethodChannel
    private lateinit var methodChannel: MethodChannel

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        val nativeView = NativeView(this@MainActivity)

        // Configurar o nativeView.onCounterChanged
        nativeView.onCounterChanged = { counter ->
            methodChannel.invokeMethod("onCounterChanged", counter)
        }

        flutterEngine.platformViewsController.registry.registerViewFactory("native_view", NativeViewFactory(nativeView))

        methodChannel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
        methodChannel.setMethodCallHandler { call, result ->
            when (call.method) {
                "createView" -> {
                    result.success(0) // Retornamos 0 como identificador
                }
                else -> result.notImplemented()
            }
        }
    }
}


