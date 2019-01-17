package vn.com.huong.comicskotlin.model

import com.google.gson.annotations.SerializedName

/**
 * Created by HuongPN on 01/17/2019.
 */
data class Category(
        @SerializedName("ID")
        var id: Int = 0,
        @SerializedName("Name")
        var name: String? = null
)