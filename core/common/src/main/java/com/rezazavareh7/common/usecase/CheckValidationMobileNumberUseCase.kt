package com.rezazavareh7.common.usecase

import com.rezazavareh7.common.model.Validation
import com.rezazavareh7.common.util.extensions.isValidMobileNumber
import javax.inject.Inject

class CheckValidationMobileNumberUseCase
    @Inject
    constructor() {
        operator fun invoke(mobileNumber: String): Validation =
            Validation(
                isShowErrorMessage = mobileNumber.length == 11 && !mobileNumber.isValidMobileNumber(),
                isValidMobileNumber = mobileNumber.isValidMobileNumber(),
            )
    }
