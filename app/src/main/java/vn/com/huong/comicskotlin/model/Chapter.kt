package vn.com.huong.comicskotlin.model

import com.google.gson.annotations.SerializedName

/**
 * Created by HuongPN on 01/16/2019.
 */
data class Chapter(
        @SerializedName("ID")
        val id: Int,
        @SerializedName("Name")
        val name: String,
        @SerializedName("MangaID")
        val mangaId: Int)