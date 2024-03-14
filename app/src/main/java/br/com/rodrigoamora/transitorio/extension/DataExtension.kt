package br.com.rodrigoamora.transitorio.extension

import java.time.LocalDate
import java.time.format.DateTimeFormatter


fun LocalDate.getDateFormatted(pattern: String): String {
    val dateTimeFormatter = DateTimeFormatter.ofPattern(pattern)
    return this.format(dateTimeFormatter)
}
