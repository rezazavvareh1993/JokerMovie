package com.rezazavareh7.movies.data.apiservice

import com.rezazavareh7.movies.data.model.MovieDetailsResponse
import com.rezazavareh7.movies.data.model.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiService {
    @GET("movie/top_rated?language=en-US")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
    ): Result<MoviesResponse>

    @GET("movie/now_playing?language=en-US")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int,
    ): Result<MoviesResponse>

    @GET("movie/popular?language=en-US")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
    ): Result<MoviesResponse>

    @GET("movie/upcoming?language=en-US")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int,
    ): Result<MoviesResponse>

    @GET("movie/{movie_id}?language=en-US")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Long,
    ): Result<MovieDetailsResponse>

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("page") page: Int,
        @Query("query") query: String,
    ): Result<MoviesResponse>
}
