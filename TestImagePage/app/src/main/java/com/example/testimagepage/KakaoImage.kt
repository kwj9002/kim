package com.example.testimagepage

import com.google.gson.annotations.SerializedName
import java.util.Date

data class KakaoImage(
    @SerializedName("display_sitename")
    val siteName: String,
    val collection: String,
    @SerializedName("image_url")
    val imageUrl: String,
    var isFavorite: Boolean,
    @SerializedName("datetime")
    val datetime: Date
)

data class MetaData(
    @SerializedName("total_count")
    val totalCount: Int?,
    @SerializedName("is_end")
    val isEnd: Boolean?
)

data class ImageSearchResponse(
    @SerializedName("meta")
    val metaData: MetaData?,
    @SerializedName("documents")
    val documents: MutableList<KakaoImage>?
)