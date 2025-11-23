package com.rezazavareh7.movies.data.apiservice

import com.rezazavareh7.movies.data.model.MediaImagesResponse
import com.rezazavareh7.movies.data.model.MovieCreditsResponse
import com.rezazavareh7.movies.data.model.MovieDetailsResponse
import com.rezazavareh7.movies.data.model.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
    ): Result<MoviesResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int,
    ): Result<MoviesResponse>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
    ): Result<MoviesResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int,
    ): Result<MoviesResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Long,
    ): Result<MovieDetailsResponse>

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("page") page: Int,
        @Query("query") query: String,
    ): Result<MoviesResponse>

    @GET("movie/{movieId}/images")
    suspend fun getImages(
        @Path("movieId") movieId: Long,
    ): Result<MediaImagesResponse>

    @GET("movie/{movieId}/credits")
    suspend fun getCredits(
        @Path("movieId") movieId: Long,
    ): Result<MovieCreditsResponse>

    @GET("movie/{movieId}/similar")
    suspend fun getSimilarMovies(
        @Path("movieId") movieId: Long,
        @Query("page") page: Int,
    ): Result<MoviesResponse>
}
