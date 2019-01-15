package vn.com.huong.comicskotlin.model

import com.google.gson.annotations.SerializedName

/**
 * Created by HuongPN on 01/11/2019.
 */
data class Comic(
        @SerializedName("ID")
        val id: Int,
        @SerializedName("Name")
        val name: String,
        @SerializedName("Image")
        val image: String)