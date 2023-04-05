package com.example.flutter_poc_native_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.StandardMessageCodec
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView

class NativeView(context: Context) : LinearLayout(context) {
    private val textView: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.native_view_layout, this, true)
        textView = findViewById(R.id.native_text)
    }

    fun setText(text: String) {
        textView.text = text
    }
}


class NativeViewFactory(private val nativeView: NativeView) : PlatformViewFactory(
    StandardMessageCodec.INSTANCE) {
    override fun create(context: Context, viewId: Int, args: Any?): PlatformView {
        return object : PlatformView {
            override fun getView(): View {
                return nativeView
            }

            override fun dispose() {}
        }
    }
}
