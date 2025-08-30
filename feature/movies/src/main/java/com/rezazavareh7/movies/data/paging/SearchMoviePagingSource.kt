package com.rezazavareh7.movies.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rezazavareh7.movies.data.apiservice.MovieApiService
import com.rezazavareh7.movies.data.mapper.MoviesMapper
import com.rezazavareh7.movies.data.networkstate.BasicNetworkState
import com.rezazavareh7.movies.domain.model.MediaData
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class SearchMoviePagingSource
    @AssistedInject
    constructor(
        private val movieApiService: MovieApiService,
        private val moviesMapper: MoviesMapper,
        @Assisted private val query: String,
    ) : PagingSource<Int, MediaData>() {
        override fun getRefreshKey(state: PagingState<Int, MediaData>): Int? =
            state.anchorPosition?.let { anchorPosition ->
                state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                    ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
            }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MediaData> =
            try {
                val page = params.key ?: 1
                val response = movieApiService.searchMovie(page = page, query = query)
                val result = moviesMapper.mapToData(response)

                when (result) {
                    is BasicNetworkState.Success ->
                        LoadResult.Page(
                            data = result.data,
                            prevKey = if (page == 1) null else page.minus(1),
                            nextKey = if (result.data.isEmpty()) null else page.plus(1),
                        )

                    is BasicNetworkState.Error ->
                        LoadResult.Error(throwable = result.throwable)
                }
            } catch (e: Exception) {
                LoadResult.Error(e)
            }

        @AssistedFactory
        interface Factory {
            fun create(query: String): SearchMoviePagingSource
        }
    }
