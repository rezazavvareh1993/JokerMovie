package com.rezazavareh7.movies.di

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.rezazavareh7.movies.data.paging.SearchMoviePagingSource
import com.rezazavareh7.movies.domain.model.MediaData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MoviePagerModule {
    @Provides
    @Singleton
    fun providePagerFactory(searchPagingSourceFactory: SearchMoviePagingSource.Factory): (String) -> Pager<Int, MediaData> =
        { query ->
            Pager(
                config = PagingConfig(pageSize = 20),
                pagingSourceFactory = { searchPagingSourceFactory.create(query) },
            )
        }
}
