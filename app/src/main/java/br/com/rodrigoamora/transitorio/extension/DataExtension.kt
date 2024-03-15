package br.com.rodrigoamora.transitorio.extension

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


fun LocalDateTime.getDateFormatted(pattern: String): String {
    val dateTimeFormatter = DateTimeFormatter.ofPattern(pattern)
    return this.format(dateTimeFormatter)
}
