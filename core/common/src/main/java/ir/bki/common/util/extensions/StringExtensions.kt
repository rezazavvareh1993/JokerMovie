package ir.bki.common.util.extensions

fun String.toPersianDigits(): String {
    val englishDigits = listOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
    val persianDigits = listOf('۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹')

    var persianNumber = this
    englishDigits.forEachIndexed { index, c ->
        persianNumber = persianNumber.replace(c, persianDigits[index])
    }
    return persianNumber
}

fun String.isValidMobileNumber(): Boolean {
    val iranianMobileRegex = "^09\\d{9}$".toRegex()
    return iranianMobileRegex.matches(this)
}