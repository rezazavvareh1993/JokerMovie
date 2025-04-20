package ir.bki.common.usecase

import ir.bki.common.model.Validation
import ir.bki.common.util.extensions.isValidMobileNumber
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
