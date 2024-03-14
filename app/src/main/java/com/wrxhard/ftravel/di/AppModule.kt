package com.wrxhard.ftravel.di

import android.app.Application
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.wrxhard.ftravel.data.local.DatabaseIns
import com.wrxhard.ftravel.data.local.food_repo.FoodRepo
import com.wrxhard.ftravel.data.local.food_repo.FoodRepoImp
import com.wrxhard.ftravel.data.local.location_repo.LocationRepo
import com.wrxhard.ftravel.data.local.location_repo.LocationRepoImp
import com.wrxhard.ftravel.data.remote.auth_api.AuthRepo
import com.wrxhard.ftravel.data.remote.auth_api.AuthRepoImp
import com.wrxhard.ftravel.data.remote.auth_api.AuthService
import com.wrxhard.ftravel.data.remote.direction_map_api.MapDirectionImpl
import com.wrxhard.ftravel.data.remote.direction_map_api.MapDirectionRepo
import com.wrxhard.ftravel.data.remote.direction_map_api.MapDirectionService
import com.wrxhard.ftravel.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


const val BASE_URL="http://192.168.88.143:3000"
//const val BASE_URL=""
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofitInstance(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    @Singleton
    @Provides
    fun provideAuthRepo(api: AuthService): AuthRepo = AuthRepoImp(api)
    @Singleton
    @Provides
    fun provideAuthService(retrofit: Retrofit): AuthService = retrofit.create(AuthService::class.java)

    @Singleton
    @Provides
    fun provideMapDirectionRepo(api: MapDirectionService): MapDirectionRepo = MapDirectionImpl(api)
    @Singleton
    @Provides
    fun provideMapDirectionService(retrofit: Retrofit): MapDirectionService = retrofit.create(MapDirectionService::class.java)
    @Singleton
    @Provides
    fun provideDispatchers(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }

    @Singleton
    @Provides
    fun provideDatabaseIns(application: Application): DatabaseIns {
        return Room.databaseBuilder(application, DatabaseIns::class.java,"local_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideFoodRepo(db: DatabaseIns): FoodRepo {
        return FoodRepoImp(db.getDatabaseFoodDAO())
    }

    @Provides
    @Singleton
    fun provideLocationRepo(db: DatabaseIns): LocationRepo {
        return LocationRepoImp(db.getDatabaseLocationDAO())
    }

}

