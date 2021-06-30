package es.webandroid.marvel.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import es.webandroid.marvel.core.connectivity.base.ConnectivityProvider
import es.webandroid.marvel.data.mapper.HeroMapper
import es.webandroid.marvel.data.repository.RepositoryImpl
import es.webandroid.marvel.data.source.LocalDataSource
import es.webandroid.marvel.data.source.RemoteDataSource
import es.webandroid.marvel.domain.repository.Repository
import es.webandroid.marvel.domain.use_cases.HeroDetailUseCase
import es.webandroid.marvel.domain.use_cases.HeroUseCase
import es.webandroid.marvel.framework.local.RoomDataSource
import es.webandroid.marvel.framework.local.base.HeroDao
import es.webandroid.marvel.framework.local.base.HeroDatabase
import es.webandroid.marvel.framework.remote.*
import es.webandroid.marvel.framework.remote.mapper.HeroDataMapper
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ConnectivityModule {
    @Provides
    @Singleton
    fun connectivityProvider(@ApplicationContext context: Context): ConnectivityProvider =
        ConnectivityProvider.createProvider(context)
}

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun heroService(): NetworkApi = NetworkServiceProvider().networkApi()
}

@Module
@InstallIn(SingletonComponent::class)
class MappersModule {
    @Provides
    @Singleton
    fun heroDataMapper(): HeroDataMapper = HeroDataMapper()

    @Provides
    @Singleton
    fun heroMapper(): HeroMapper = HeroMapper()
}

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource,
        heroMapper: HeroMapper
    ): Repository = RepositoryImpl(remoteDataSource, localDataSource, heroMapper)
}

@Module
@InstallIn(SingletonComponent::class)
class UseCasesModule {
    @Provides
    fun providerHeroUseCase(repository: Repository): HeroUseCase = HeroUseCase(repository)

    @Provides
    fun providerHeroDetailUseCase(repository: Repository): HeroDetailUseCase = HeroDetailUseCase(repository)
}

@Module
@InstallIn(SingletonComponent::class)
object FrameworkModule {

    @Provides
    @Singleton
    fun heroRemoteDataSource(
        networkApi: NetworkApi,
        heroDataMapper: HeroDataMapper
    ): RemoteDataSource = HeroRemoteDataSource(networkApi, heroDataMapper)

    @Provides
    @Singleton
    fun provideHeroDatabase(@ApplicationContext context: Context): HeroDatabase = HeroDatabase.build(context)

    @Provides
    @Singleton
    fun provideRoomDataSource(heroDB: HeroDatabase): LocalDataSource = RoomDataSource(heroDB)

    @Provides
    fun provideHeroDao(HeroDB: HeroDatabase): HeroDao = HeroDB.heroDao()
}
