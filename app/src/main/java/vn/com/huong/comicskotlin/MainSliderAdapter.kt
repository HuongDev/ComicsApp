package vn.com.huong.comicskotlin

import ss.com.bannerslider.adapters.SliderAdapter
import ss.com.bannerslider.viewholder.ImageSlideViewHolder
import vn.com.huong.comicskotlin.model.Banner

/**
 * Created by HuongPN on 01/12/2019.
 */
class MainSliderAdapter(private val bannerList: List<Banner>) : SliderAdapter() {
    override fun getItemCount(): Int {
        return bannerList.size
    }

    override fun onBindImageSlide(position: Int, imageSlideViewHolder: ImageSlideViewHolder?) {
        return imageSlideViewHolder!!.bindImageSlide(bannerList[position].image)
    }

}