package io.maxime.core.data.remote

import io.maxime.core.data.remote.response.JoinResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET

interface CoreService {

    @GET("join-demo/stories")
    fun getStories(): Flow<Response<List<JoinResponse>>>
}
