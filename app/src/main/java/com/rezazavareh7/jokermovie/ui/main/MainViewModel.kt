package com.rezazavareh7.jokermovie.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rezazavareh.usecase.GetLanguageUseCase
import com.rezazavareh.usecase.GetThemeUseCase
import com.rezazavareh7.jokermovie.BuildConfig
import com.rezazavareh7.movies.domain.usecase.GetVersionUseCase
import com.rezazavareh7.movies.domain.usecase.SaveVersionUseCase
import com.rezazavareh7.movies.ui.setting.ThemeSegmentButtonType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.invoke

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        private val getThemeUseCase: GetThemeUseCase,
        private val getLanguageUseCase: GetLanguageUseCase,
        private val saveVersionUseCase: SaveVersionUseCase,
        private val getVersionUseCase: GetVersionUseCase,
    ) : ViewModel() {
        val mMainState = MutableStateFlow(MainUiState())
        val mainState: StateFlow<MainUiState> =
            mMainState.onStart {
                getTheme()
                getLanguage()
                saveVersion()
            }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MainUiState())

        private fun getTheme() {
            viewModelScope.launch {
                getThemeUseCase().collect { currentTheme ->
                    mMainState.update {
                        it.copy(
                            currentTheme = ThemeSegmentButtonType.valueOf(currentTheme),
                        )
                    }
                }
            }
        }

        private fun saveVersion() {
            viewModelScope.launch {
                val versionName = BuildConfig.VERSION_NAME
                val lastVersionName = getVersionUseCase.invoke()
                if (lastVersionName.isEmpty() && versionName != lastVersionName) {
                    saveVersionUseCase(versionName)
                }
            }
        }

        private fun getLanguage() {
            viewModelScope.launch {
                getLanguageUseCase().collect { currentLanguageLocale ->
                    mMainState.update {
                        it.copy(
                            currentLanguage = currentLanguageLocale,
                        )
                    }
                }
            }
        }
    }
