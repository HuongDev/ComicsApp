package vn.com.huong.comicskotlin.detail

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.chrisbanes.photoview.PhotoView
import com.squareup.picasso.Picasso
import vn.com.huong.comicskotlin.R
import vn.com.huong.comicskotlin.model.Link


/**
 * Created by HuongPN on 01/16/2019.
 */
class MySliderAdapter(internal var context: Context,
                      internal var linkList: List<Link>) : PagerAdapter() {

    internal var inflater: LayoutInflater

    init {
        inflater = LayoutInflater.from(context)
    }


    override fun isViewFromObject(view: View, p1: Any): Boolean {
        return view == p1
    }

    override fun getCount(): Int {
        return linkList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
       var view = `object` as View?
        container.removeView(view)
        view = null
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageLayout = inflater.inflate(R.layout.item_viewpager, container, false)

        val photoView = imageLayout.findViewById(R.id.pageImage) as PhotoView
        val text = imageLayout.findViewById(R.id.textId) as TextView

        text.text = linkList[position].link

        Picasso.get().load(linkList[position].link).into(photoView)

        container.addView(imageLayout)
        return imageLayout
    }
}