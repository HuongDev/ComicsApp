package vn.com.huong.comicskotlin.Retrofit

import io.reactivex.Observable
import retrofit2.http.*
import vn.com.huong.comicskotlin.model.*

/**
 * Created by HuongPN on 01/11/2019.
 */
interface IComicAPI {
    @get:GET("banner")
    val getBannerList: Observable<List<Banner>>

    @get:GET("comic")
    val getComicList: Observable<List<Comic>>

    @get:GET("categories")
    val getCategoryList: Observable<List<Category>>

    @GET("chapter/{mangaid}")
    fun getChapterList(@Path("mangaid") mangaId: Int): Observable<List<Chapter>>

    @GET("link/{chapterid}")
    fun getLinkList(@Path("chapterid") chapterId: Int): Observable<List<Link>>

    @POST("filter")
    @FormUrlEncoded
    fun getFilterComicList(@Field("data") data: String): Observable<List<Comic>>

    @POST("search")
    @FormUrlEncoded
    fun getSearchedComicList(@Field("search") data: String): Observable<List<Comic>>
}