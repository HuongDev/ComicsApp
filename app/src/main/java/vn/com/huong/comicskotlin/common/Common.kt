package vn.com.huong.comicskotlin.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import vn.com.huong.comicskotlin.Retrofit.ApiClient
import vn.com.huong.comicskotlin.Retrofit.IComicAPI
import vn.com.huong.comicskotlin.model.Category
import vn.com.huong.comicskotlin.model.Chapter
import vn.com.huong.comicskotlin.model.Comic

/**
 * Created by HuongPN on 01/11/2019.
 */
object Common {

    // Save selected comic
    var selectedComic : Comic? = null

    var chapterList : List<Chapter> = mutableListOf()

    var selectedChapter : Chapter? = null
    var chapterIndex : Int? = 0

    var categoriesList: List<Category> = ArrayList()

    // Call Api
    val api: IComicAPI
    get(){
        val retrofit = ApiClient.instance
        return retrofit.create(IComicAPI::class.java)
    }

    fun isConnectedToInternet(baseContext: Context?): Boolean {
        val cm = baseContext!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm != null){
            if (Build.VERSION.SDK_INT < 23) {
                val ni = cm.activeNetworkInfo
                if (ni != null) {
                    return ni.isConnected && (ni.type == ConnectivityManager.TYPE_WIFI || ni.type == ConnectivityManager.TYPE_MOBILE)
                }
            } else {
                val n = cm.activeNetwork
                if (n != null){
                    val nc = cm.getNetworkCapabilities(n)
                    return nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                }
            }
        }

        return false
    }

    fun formatString(name: String?): CharSequence {
        val result = StringBuilder(if (name!!.length > 15) name.substring(0, 15) + "..." else name)
        return result.toString()
    }
}