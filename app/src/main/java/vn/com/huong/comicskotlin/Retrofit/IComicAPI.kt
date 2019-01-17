package vn.com.huong.comicskotlin.Retrofit

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import vn.com.huong.comicskotlin.model.Banner
import vn.com.huong.comicskotlin.model.Chapter
import vn.com.huong.comicskotlin.model.Comic
import vn.com.huong.comicskotlin.model.Link

/**
 * Created by HuongPN on 01/11/2019.
 */
interface IComicAPI {
    @get:GET("banner")
    val getBannerList: Observable<List<Banner>>

    @get:GET("comic")
    val getComicList: Observable<List<Comic>>

    @GET("chapter/{mangaid}")
    fun getChapterList(@Path("mangaid") mangaId: Int): Observable<List<Chapter>>

    @GET("link/{chapterid}")
    fun getLinkList(@Path("chapterid") chapterId: Int): Observable<List<Link>>
}