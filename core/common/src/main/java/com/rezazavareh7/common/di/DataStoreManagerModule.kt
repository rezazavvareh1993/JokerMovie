package com.rezazavareh7.common.di

import com.rezazavareh7.common.data.DataStoreManagerImpl
import com.rezazavareh7.common.doamin.DataStoreManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreManagerModule {
    @Binds
    internal abstract fun bindDataStoreManager(dataStoreManagerImpl: DataStoreManagerImpl): DataStoreManager
}
