package com.rezazavareh7.movies.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rezazavareh.usecase.GetLanguageUseCase
import com.rezazavareh.usecase.GetThemeUseCase
import com.rezazavareh.usecase.SaveLanguageUseCase
import com.rezazavareh.usecase.SaveThemeUseCase
import com.rezazavareh7.movies.domain.usecase.GetVersionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel
    @Inject
    constructor(
        private val saveLanguageUseCase: SaveLanguageUseCase,
        private val getLanguageUseCase: GetLanguageUseCase,
        private val saveThemeUseCase: SaveThemeUseCase,
        private val getThemeUseCase: GetThemeUseCase,
        private val getVersionUseCase: GetVersionUseCase,
    ) : ViewModel() {
        private val mSettingUiState = MutableStateFlow<SettingUiState>(SettingUiState())
        val settingUiState: StateFlow<SettingUiState> =
            mSettingUiState.onStart {
                getTheme()
                getLanguage()
                getVersion()
            }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SettingUiState())

        fun onEvent(event: SettingUiEvent) {
            when (event) {
                is SettingUiEvent.OnThemeSegmentButtonClicked -> updateTheme(event.type, event.index)

                is SettingUiEvent.OnLanguageSegmentButtonClicked ->
                    updateLanguage(event.locale, event.index)
            }
        }

        private fun updateTheme(
            selectedTheme: String,
            index: Int,
        ) {
            viewModelScope.launch {
                saveThemeUseCase(selectedTheme)
            }
        }

        private fun getVersion() {
            viewModelScope.launch {
                val version = getVersionUseCase.invoke()
                mSettingUiState.update { it.copy(version = version) }
            }
        }

        private fun updateLanguage(
            selectedLanguage: String,
            index: Int,
        ) {
            viewModelScope.launch {
                saveLanguageUseCase(selectedLanguage)
            }
        }

        private fun getTheme() {
            viewModelScope.launch {
                getThemeUseCase().collect { currentTheme ->
                    mSettingUiState.update {
                        it.copy(
                            selectedThemeSegmentButtonIndex = if (currentTheme == ThemeSegmentButtonType.DARK.toString()) 1 else 0,
                        )
                    }
                }
            }
        }

        private fun getLanguage() {
            viewModelScope.launch {
                getLanguageUseCase().collect { currentLanguage ->
                    mSettingUiState.update {
                        it.copy(
                            selectedLanguageSegmentButtonIndex = if (currentLanguage == LanguageSegmentButtonType.FARSI.locale) 1 else 0,
                        )
                    }
                }
            }
        }
    }
