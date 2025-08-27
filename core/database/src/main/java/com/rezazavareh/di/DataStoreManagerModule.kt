package com.rezazavareh.di

import com.rezazavareh.prefrences.RegularDataStoreManager
import com.rezazavareh.prefrences.RegularDataStoreManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreManagerModule {
    @Binds
    internal abstract fun bindRegularDataStoreManager(regularDataStoreManagerImpl: RegularDataStoreManagerImpl): RegularDataStoreManager
}
