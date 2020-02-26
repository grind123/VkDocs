package com.grind.vksecondround.utils

import android.content.res.Resources

class DpPxUtil {
    companion object{
        fun toDp(value: Int): Int = (value / Resources.getSystem().displayMetrics.density).toInt()
        fun toPx(value: Int): Int = (value * Resources.getSystem().displayMetrics.density).toInt()
    }
}