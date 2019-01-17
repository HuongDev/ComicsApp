package vn.com.huong.comicskotlin.Chapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import vn.com.huong.comicskotlin.R
import vn.com.huong.comicskotlin.common.Common
import vn.com.huong.comicskotlin.detail.ViewDetailActivity
import vn.com.huong.comicskotlin.model.Chapter

/**
 * Created by HuongPN on 01/16/2019.
 */
class ChapterAdapter(
        internal var context: Context,
        internal var chapterList: List<Chapter>) : RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder {
        return ChapterViewHolder(
                LayoutInflater.from(context)
                        .inflate(R.layout.item_chapter, parent, false)
        )
    }

    override fun getItemCount() = chapterList.size

    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) = holder.bind(chapterList[position])

    fun swapData(data: List<Chapter>) {
        this.chapterList = data
        notifyDataSetChanged()
    }

    inner class ChapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var chapterName : TextView
        init {
            chapterName = itemView.findViewById(R.id.tvChapter)
        }
        fun bind(item: Chapter) = with(itemView) {
            // Bind the data with View

            chapterName.text = item.name
            setOnClickListener {
                // Handle on click
                Common.selectedChapter = chapterList[adapterPosition]
                Common.chapterIndex = adapterPosition
                context.startActivity(Intent(context, ViewDetailActivity::class.java))
            }
        }
    }
}