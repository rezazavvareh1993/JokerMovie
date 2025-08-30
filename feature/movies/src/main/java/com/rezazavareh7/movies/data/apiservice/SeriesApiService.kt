package com.rezazavareh7.movies.data.apiservice

import com.rezazavareh7.movies.data.model.MediaImagesResponse
import com.rezazavareh7.movies.data.model.SeriesDetailResponse
import com.rezazavareh7.movies.data.model.SeriesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SeriesApiService {
    @GET("tv/top_rated")
    suspend fun getTopRatedSeries(
        @Query("page") page: Int,
    ): Result<SeriesResponse>

    @GET("tv/popular")
    suspend fun getPopularSeries(
        @Query("page") page: Int,
    ): Result<SeriesResponse>

    @GET("tv/on_the_air")
    suspend fun getOnTheAirSeries(
        @Query("page") page: Int,
    ): Result<SeriesResponse>

    @GET("tv/airing_today")
    suspend fun getAiringTodaySeries(
        @Query("page") page: Int,
    ): Result<SeriesResponse>

    @GET("search/tv")
    suspend fun searchTV(
        @Query("page") page: Int,
        @Query("query") query: String,
    ): Result<SeriesResponse>

    @GET("tv/{series_id}")
    suspend fun getSeriesDetails(
        @Path("series_id") seriesId: Long,
    ): Result<SeriesDetailResponse>

    @GET("tv/{seriesId}/images")
    suspend fun getImages(
        @Path("seriesId") seriesId: Long,
    ): Result<MediaImagesResponse>
}
