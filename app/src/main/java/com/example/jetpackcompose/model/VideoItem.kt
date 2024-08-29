package com.example.jetpackcompose.model

import java.util.Date
import kotlin.time.Duration

class VideoItem(
    var mPath: String,
    var isSelected: Boolean,
    var date: Date,
    var duration: Long,
    var isDate: Boolean
) {

}