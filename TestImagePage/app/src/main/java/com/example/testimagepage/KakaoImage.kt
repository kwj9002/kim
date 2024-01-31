package com.example.testimagepage

import com.google.gson.annotations.SerializedName
import java.util.Date

data class KakaoImage(
    @SerializedName("display_sitename")
    val siteName: String,
    val collection: String,
    @SerializedName("image_url")
    val imageUrl: String,
    var isFavorite: Boolean = false,
    val datetime: Date
)

data class Meta(
    @SerializedName("total_count")
    val totalCount: Int?,
    @SerializedName("pageable_count")
    val pageableCount: Int?,
    @SerializedName("is_end")
    val isEnd: Boolean?
)

data class ImageSearchResponse(
    val meta: Meta?,
    val documents: MutableList<KakaoImage>?
)