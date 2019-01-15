package vn.com.huong.comicskotlin.service

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Picasso
import ss.com.bannerslider.ImageLoadingService

/**
 * Created by HuongPN on 01/12/2019.
 */
class PicassoImageLoadingService(var context: Context) : ImageLoadingService {
    override fun loadImage(url: String?, imageView: ImageView?) {
        Picasso.get().load(url).into(imageView)
    }

    override fun loadImage(resource: Int, imageView: ImageView?) {
        Picasso.get().load(resource).into(imageView)
    }

    override fun loadImage(url: String?, placeHolder: Int, errorDrawable: Int, imageView: ImageView?) {
        Picasso.get().load(url).placeholder(placeHolder).error(errorDrawable).into(imageView)
    }
}