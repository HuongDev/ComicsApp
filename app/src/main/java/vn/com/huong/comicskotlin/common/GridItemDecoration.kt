package vn.com.huong.comicskotlin.common

import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by HuongPN on 01/15/2019.
 */
class GridItemDecoration(private var offset: Int): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val layoutPrams = view.layoutParams as GridLayoutManager.LayoutParams


        if (layoutPrams.spanIndex.div(2) == 0) {

            // Right
            outRect.top = offset
            outRect.left = offset / 2
            outRect.right = offset / 2
        } else {

            // Item of column left
            outRect.top = offset
            outRect.right = offset / 2
            outRect.left = offset / 2
        }
    }
}