package com.rezazavareh7.movies.domain.usecase

// class GetMoviesUseCase
//    @Inject
//    constructor(private val repository: MoviesRepository) {
//        suspend operator fun invoke(): MoviesResult =
//            when (val result = repository.getMovies()) {
//                is GetMoviesNetworkState.Success ->
//                    MoviesResult(
//                        hasError = false,
//                        moviesData = result.data,
//                    )
//
//                is GetMoviesNetworkState.Error ->
//                    MoviesResult(
//                        hasError = true,
//                        errorMessage = result.message,
//                    )
//            }
//    }
