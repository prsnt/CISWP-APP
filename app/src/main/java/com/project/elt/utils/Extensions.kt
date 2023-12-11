package com.project.elt.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.elt.R

fun Dialog.setDialogSize(widthPercent: Float) {
    val display = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
    val size = Point()
    display.getSize(size)

    val displayWidth = size.x
    val displayHeight = size.y

    val layoutParams = WindowManager.LayoutParams()
    layoutParams.width = (displayWidth * widthPercent).toInt()
    layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
    this.window?.attributes = layoutParams
}