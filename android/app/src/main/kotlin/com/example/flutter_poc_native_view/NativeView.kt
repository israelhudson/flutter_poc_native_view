package com.example.flutter_poc_native_view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import io.flutter.plugin.common.StandardMessageCodec
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory

class NativeView(context: Context) : LinearLayout(context) {
    private val counterTextView: TextView
    private var counter: Int = 0
    var onCounterChanged: ((Int) -> Unit)? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.native_view_layout, this, true)
        counterTextView = findViewById(R.id.counter_text)
        val incrementButton: Button = findViewById(R.id.increment_button)
        val decrementButton: Button = findViewById(R.id.decrement_button)

        incrementButton.setOnClickListener {
            counter++
            updateCounterText()
            onCounterChanged?.invoke(counter)
        }

        decrementButton.setOnClickListener {
            counter--
            updateCounterText()
            onCounterChanged?.invoke(counter)
        }
    }

    private fun updateCounterText() {
        counterTextView.text = counter.toString()
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
