package com.techquantum.template.common.logger

import android.util.Log

interface AppLogger {
    fun v(tag: String, message: String, throwable: Throwable? = null)
    fun d(tag: String, message: String, throwable: Throwable? = null)
    fun i(tag: String, message: String, throwable: Throwable? = null)
    fun w(tag: String, message: String, throwable: Throwable? = null)
    fun e(tag: String, message: String, throwable: Throwable? = null)
}

internal class DefaultAppLogger : AppLogger {
    override fun v(tag: String, message: String, throwable: Throwable?) {
        Log.v(tag, message, throwable)
    }

    override fun d(tag: String, message: String, throwable: Throwable?) {
        Log.d(tag, message, throwable)
    }

    override fun i(tag: String, message: String, throwable: Throwable?) {
        Log.i(tag, message, throwable)
    }

    override fun w(tag: String, message: String, throwable: Throwable?) {
        Log.w(tag, message, throwable)
    }

    override fun e(tag: String, message: String, throwable: Throwable?) {
        Log.e(tag, message, throwable)
    }
}
