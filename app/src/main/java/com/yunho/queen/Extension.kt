package com.yunho.queen

import android.view.View

class SingleClickListener(private val singleClick: (View) -> Unit) : View.OnClickListener {
    companion object {
        private const val CLICK_INTERVAL = 500
    }

    private var lastClickTime: Long = 0L

    override fun onClick(v: View?) {
        if (isSafe() && v != null) {
            lastClickTime = System.currentTimeMillis()
            singleClick(v)
        }
    }

    private fun isSafe(): Boolean = (System.currentTimeMillis() - lastClickTime) > CLICK_INTERVAL
}

fun View.singleClickListener(onSingleClick: (View) -> Unit) {
    val singleClickListener = SingleClickListener { onSingleClick(it) }
    setOnClickListener(singleClickListener)
}

fun View.toGone() {
    this.visibility = View.GONE
}

fun View.toVisible() {
    this.visibility = View.VISIBLE
}

fun View.toInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.toEnable() {
    this.isEnabled = true
}

fun View.toDisable() {
    this.isEnabled = false
}