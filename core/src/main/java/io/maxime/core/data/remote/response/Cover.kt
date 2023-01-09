package io.maxime.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class Cover(
    @SerializedName("urls") val urls: Urls? = Urls()
)
