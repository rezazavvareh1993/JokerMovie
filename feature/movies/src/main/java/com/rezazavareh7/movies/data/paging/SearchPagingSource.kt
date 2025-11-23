package com.rezazavareh7.movies.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rezazavareh7.movies.data.apiservice.MovieApiService
import com.rezazavareh7.movies.data.apiservice.SeriesApiService
import com.rezazavareh7.movies.data.mapper.MoviesMapper
import com.rezazavareh7.movies.data.mapper.SeriesMapper
import com.rezazavareh7.movies.data.model.MoviesResponse
import com.rezazavareh7.movies.data.model.SeriesResponse
import com.rezazavareh7.movies.domain.model.MediaCategory
import com.rezazavareh7.movies.domain.model.MediaData
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class SearchPagingSource
    @AssistedInject
    constructor(
        private val movieApiService: MovieApiService,
        private val seriesApiService: SeriesApiService,
        private val moviesMapper: MoviesMapper,
        private val seriesMapper: SeriesMapper,
        @Assisted private val category: MediaCategory,
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
                val response =
                    if (category == MediaCategory.SERIES) {
                        seriesApiService.searchTV(page, query)
                    } else {
                        movieApiService.searchMovie(page, query)
                    }
                response.fold(
                    onSuccess = {
                        val resultMapper =
                            if (category == MediaCategory.SERIES) {
                                seriesMapper(it as SeriesResponse)
                            } else {
                                moviesMapper(it as MoviesResponse)
                            }
                        LoadResult.Page(
                            data = resultMapper,
                            prevKey = if (page == 1) null else page - 1,
                            nextKey = if (resultMapper.isEmpty()) null else page + 1,
                        )
                    },
                    onFailure = {
                        LoadResult.Error(it)
                    },
                )
            } catch (e: Exception) {
                LoadResult.Error(e)
            }

        @AssistedFactory
        interface Factory {
            fun create(
                category: MediaCategory,
                query: String,
            ): SearchPagingSource
        }
    }
