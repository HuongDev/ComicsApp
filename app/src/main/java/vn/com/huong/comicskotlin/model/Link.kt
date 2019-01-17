package vn.com.huong.comicskotlin.model

import com.google.gson.annotations.SerializedName

/**
 * Created by HuongPN on 01/16/2019.
 */
data class Link(
        @SerializedName("ID")
        var id: Int = 0,
        @SerializedName("Link")
        var link: String? = null,
        @SerializedName("ChapterID")
        var chapterId: Int = 0)