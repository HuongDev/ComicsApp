package vn.com.huong.comicskotlin

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import vn.com.huong.comicskotlin.Chapter.ChapterActivity
import vn.com.huong.comicskotlin.common.Common
import vn.com.huong.comicskotlin.model.Comic

/**
 * Created by HuongPN on 01/12/2019.
 */
class MyComicAdapter(internal var context: Context,
                     internal var mangaList: List<Comic>) : RecyclerView.Adapter<MyComicAdapter.ComicViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ComicViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_comic, p0, false)
        return ComicViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mangaList.size
    }

    override fun onBindViewHolder(viewHolder: ComicViewHolder, position: Int) {
        Picasso.get().load(mangaList[position].image).into(viewHolder.mImage)
        viewHolder.mName.text = mangaList[position].name

        viewHolder.setClick(object : IRecyclerViewOnClick {
            override fun onClick(view: View, position: Int) {
                Common.selectedComic = mangaList[position]
                context.startActivity(Intent(context, ChapterActivity::class.java))
            }
        })
    }

    inner class ComicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        internal var mImage: ImageView
        internal var mName: TextView
        lateinit var iRecyclerViewOnClick: IRecyclerViewOnClick

        fun setClick(iRecyclerViewOnClick: IRecyclerViewOnClick) {
            this.iRecyclerViewOnClick = iRecyclerViewOnClick
        }

        init {
            mImage = itemView.findViewById(R.id.imageComic) as ImageView
            mName = itemView.findViewById(R.id.textNameComic) as TextView
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            iRecyclerViewOnClick.onClick(v!!, adapterPosition)
        }

    }

    interface IRecyclerViewOnClick{
        fun onClick(view: View, position: Int)
    }
}