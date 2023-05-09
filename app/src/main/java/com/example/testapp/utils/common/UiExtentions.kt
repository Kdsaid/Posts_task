package com.example.testapp.utils.common

import android.content.res.Resources.getSystem

val Int.px: Int get() = (this * getSystem().displayMetrics.density).toInt()

