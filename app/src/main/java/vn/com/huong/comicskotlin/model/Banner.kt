package vn.com.huong.comicskotlin.model

import com.google.gson.annotations.SerializedName

/**
 * Created by HuongPN on 01/11/2019.
 */
data class Banner(
        @SerializedName("ID")
        val id: Int,
        @SerializedName("Link")
        val image: String)