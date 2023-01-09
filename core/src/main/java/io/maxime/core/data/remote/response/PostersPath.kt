package io.maxime.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class PostersPath(
    @SerializedName("firstFrame") val firstFrame: String? = null,
    @SerializedName("lastFrame") val lastFrame: String? = null
)
