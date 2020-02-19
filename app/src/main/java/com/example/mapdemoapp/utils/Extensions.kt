package com.example.mapdemoapp.utils

import android.content.res.Resources


/**
 * Extended functions that can be useful later
 */

//get conversion from px to dp
fun Int.toDp() = (this / Resources.getSystem().displayMetrics.density).toInt()

//get conversion from dp to px
fun Int.toPx() = (this * Resources.getSystem().displayMetrics.density).toInt()