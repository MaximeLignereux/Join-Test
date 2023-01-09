package io.maxime.core.data.remote

import android.util.Log
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CoreRemoteRepository @Inject constructor(val service: CoreService) {

    fun getStories() = flow {
        service
            .getStories()
            .collect { response ->
                val body = response.body()
                Log.d("response", response.toString())
                when {
                    response.isSuccessful && body != null -> emit(body)
                }
            }
    }
}
