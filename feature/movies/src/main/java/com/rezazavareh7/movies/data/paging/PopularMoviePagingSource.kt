package com.rezazavareh7.movies.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rezazavareh7.movies.data.apiservice.MovieApiService
import com.rezazavareh7.movies.data.mapper.MoviesMapper
import com.rezazavareh7.movies.data.networkstate.BasicNetworkState
import com.rezazavareh7.movies.domain.model.MediaData
import javax.inject.Inject

class PopularMoviePagingSource
    @Inject
    constructor(
        private val movieApiService: MovieApiService,
        private val moviesMapper: MoviesMapper,
    ) : PagingSource<Int, MediaData>() {
        override fun getRefreshKey(state: PagingState<Int, MediaData>): Int? =
            state.anchorPosition?.let { anchorPosition ->
                state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                    ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
            }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MediaData> =
            try {
                val page = params.key ?: 1
                val response = movieApiService.getPopularMovies(page = page)
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
    }
