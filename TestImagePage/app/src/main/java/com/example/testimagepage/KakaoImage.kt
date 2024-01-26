package com.example.testimagepage

import com.google.gson.annotations.SerializedName

data class KakaoImage(
    @SerializedName("display_sitename")
    val siteName: String,
    val collection: String,
    @SerializedName("image_url")
    val imageUrl: String,
    var isFavorite: Boolean = false,
    @SerializedName("datetime")
    val datetime: String
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
