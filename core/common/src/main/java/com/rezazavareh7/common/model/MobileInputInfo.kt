package com.rezazavareh7.common.model

data class MobileInputInfo(
    val mobileNumber: String = "",
    val validation: Validation = Validation(),
)

data class Validation(
    val isShowErrorMessage: Boolean = false,
    val isValidMobileNumber: Boolean = false,
)
