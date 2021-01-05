package com.example.musictheory.adapters

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SignItemDecoration(var offset: Int) : RecyclerView.ItemDecoration() {



    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = offset
        outRect.left = offset
        outRect.right = offset
        super.getItemOffsets(outRect, view, parent, state)
    }
}