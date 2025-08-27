package com.rezazavareh7.movies.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rezazavareh7.movies.data.apiservice.MoviesApiService
import com.rezazavareh7.movies.data.mapper.MoviesMapper
import com.rezazavareh7.movies.data.networkstate.BasicNetworkState
import com.rezazavareh7.movies.domain.model.MovieData
import javax.inject.Inject

class TopRatedMoviePagingSource
    @Inject
    constructor(
        private val moviesApiService: MoviesApiService,
        private val moviesMapper: MoviesMapper,
    ) : PagingSource<Int, MovieData>() {
        override fun getRefreshKey(state: PagingState<Int, MovieData>): Int? =
            state.anchorPosition?.let { anchorPosition ->
                state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                    ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
            }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieData> =
            try {
                val page = params.key ?: 1
                val response = moviesApiService.getTopRatedMovies(page = page)
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
