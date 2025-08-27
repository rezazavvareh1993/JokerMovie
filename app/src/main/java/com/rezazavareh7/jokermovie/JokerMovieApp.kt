package com.rezazavareh7.jokermovie

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JokerMovieApp : Application() {
    override fun onCreate() {
        super.onCreate()

//        if (language.isNotEmpty()) {
//            setLocale(this, language = language)
//        }
    }
}
