package vn.com.huong.comicskotlin.Retrofit

import io.reactivex.Observable
import retrofit2.http.GET
import vn.com.huong.comicskotlin.model.Banner
import vn.com.huong.comicskotlin.model.Comic

/**
 * Created by HuongPN on 01/11/2019.
 */
interface IComicAPI {
    @get:GET("banner")
    val getBannerList: Observable<List<Banner>>

    @get:GET("comic")
    val getComicList: Observable<List<Comic>>
}