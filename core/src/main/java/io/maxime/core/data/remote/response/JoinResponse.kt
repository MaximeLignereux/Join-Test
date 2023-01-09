package io.maxime.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class JoinResponse(
    @SerializedName("_id") val id: String? = null,
    @SerializedName("containersLabel") val containersLabel: String? = null,
    @SerializedName("cover") val cover: Cover? = Cover(),
    @SerializedName("lastEditDate") val lastEditDate: Long? = null,
    @SerializedName("lastEditor") val lastEditor: String? = null,
    @SerializedName("lastPublishDate") val lastPublishDate: Long? = null,
    @SerializedName("owner") val owner: String? = null,
    @SerializedName("postersPath") val postersPath: PostersPath? = PostersPath(),
    @SerializedName("slug") val slug: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("url") val url: String? = null
)
