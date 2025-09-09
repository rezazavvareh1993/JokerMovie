package com.rezazavareh7.movies.data.mapper

import com.rezazavareh7.movies.data.model.MediaImagesResponse
import com.rezazavareh7.movies.domain.model.MediaImage
import com.rezazavareh7.movies.domain.networkstate.BasicNetworkState
import javax.inject.Inject

class MediaImagesMapper
    @Inject
    constructor() {
        operator fun invoke(result: Result<MediaImagesResponse>): BasicNetworkState<List<MediaImage>> =
            result.fold(
                onSuccess = { onSuccess(it) },
                onFailure = { onFailure(it) },
            )

        private fun onFailure(throwable: Throwable): BasicNetworkState<Nothing> =
            BasicNetworkState.Error(throwable = throwable, message = throwable.message.toString())

        private fun onSuccess(response: MediaImagesResponse): BasicNetworkState<List<MediaImage>> {
            val images = mutableListOf<MediaImage>()
            response.posters.filter { it.iso_639_1 == "en" }.map { poster ->
                with(poster) {
                    images.add(MediaImage(filePath = file_path, height = height, width = width))
                }
            }
            response.backdrops.filter { it.iso_639_1 == "en" }.map { backdrop ->
                with(backdrop) {
                    images.add(MediaImage(filePath = file_path, height = height, width = width))
                }
            }
            return BasicNetworkState.Success(data = images)
        }
    }
