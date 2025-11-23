package com.rezazavareh7.movies.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

class GenericPagingSource<ResponseType, OutputType : Any> constructor(
    private val apiCall: suspend (page: Int) -> Result<ResponseType>,
    private val mapper: suspend (ResponseType) -> List<OutputType>,
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
            response.fold(
                onSuccess = {
                    val resultMapper = mapper(it)
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
}
