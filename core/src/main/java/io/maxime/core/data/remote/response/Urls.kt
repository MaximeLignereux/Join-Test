package io.maxime.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class Urls(
    @SerializedName("square") val square: String? = null,
    @SerializedName("landscape") val landscape: String? = null,
    @SerializedName("portrait") val portrait: String? = null
)
