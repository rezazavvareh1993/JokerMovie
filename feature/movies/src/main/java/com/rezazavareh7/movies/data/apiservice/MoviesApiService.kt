package com.rezazavareh7.movies.data.apiservice

import com.rezazavareh7.movies.data.model.MovieDetailsResponse
import com.rezazavareh7.movies.data.model.MoviesResponse
import com.rezazavareh7.movies.data.model.SearchMovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiService {
    @GET("movie/top_rated?language=en-US&page=1")
    suspend fun getTopRatedMovies(): Result<MoviesResponse>

    @GET("movie/{movie_id}?language=en-US")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Long,
    ): Result<MovieDetailsResponse>

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String,
    ): Result<SearchMovieResponse>
}
