package ir.bki.common.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.bki.common.data.DataStoreManagerImpl
import ir.bki.common.doamin.DataStoreManager

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreManagerModule {
    @Binds
    internal abstract fun bindDataStoreManager(dataStoreManagerImpl: DataStoreManagerImpl): DataStoreManager
}
