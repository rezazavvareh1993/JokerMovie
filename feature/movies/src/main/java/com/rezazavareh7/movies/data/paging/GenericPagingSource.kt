package com.rezazavareh7.movies.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rezazavareh7.movies.domain.networkstate.BasicNetworkState

class GenericPagingSource<ResponseType, OutputType : Any>(
    private val apiCall: suspend (page: Int) -> ResponseType,
    private val mapper: suspend (ResponseType) -> BasicNetworkState<List<OutputType>>
) : PagingSource<Int, OutputType>() {

    override fun getRefreshKey(state: PagingState<Int, OutputType>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, OutputType> =
        try {
            val page = params.key ?: 1
            val response = apiCall(page)
            val result = mapper(response)

            when (result) {
                is BasicNetworkState.Success ->
                    LoadResult.Page(
                        data = result.data,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (result.data.isEmpty()) null else page + 1
                    )

                is BasicNetworkState.Error ->
                    LoadResult.Error(result.throwable)
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
}