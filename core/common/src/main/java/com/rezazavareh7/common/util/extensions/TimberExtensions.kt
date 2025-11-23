package com.rezazavareh7.common.util.extensions

import timber.log.Timber

fun Throwable.timberLog() {
    Timber.e(this, this.message.toString())
}
