package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val timeDiff = (date.time - time)
    return if (timeDiff >= 0) {
        when (timeDiff) {
            in 0 * SECOND..1 * SECOND -> "только что"
            in 2 * SECOND..45 * SECOND -> "несколько секунд назад"
            in 46 * SECOND..75 * SECOND -> "минуту назад"
            in 76 * SECOND..45 * MINUTE -> {
                val tmp = (timeDiff / MINUTE).toInt()
                "${TimeUnits.MINUTE.plural(tmp)} назад"
            }
            in 46 * MINUTE..75 * MINUTE -> "час назад"
            in 76 * MINUTE..22 * HOUR -> {
                val tmp = (timeDiff / HOUR).toInt()
                "${TimeUnits.HOUR.plural(tmp)} назад"
            }
            in 23 * HOUR..26 * HOUR -> "день назад"
            in 27 * HOUR..360 * DAY -> {
                val tmp = (timeDiff / DAY).toInt()
                "${TimeUnits.DAY.plural(tmp)} назад"
            }
            else -> "более года назад"
        }
    } else {
        when (timeDiff) {
            in -1 * SECOND..0 * SECOND -> "только что"
            in -45 * SECOND..-2 * SECOND -> "через несколько секунд"
            in -75 * SECOND..-46 * SECOND -> "через минуту"
            in -45 * MINUTE..-76 * SECOND -> {
                val tmp = (timeDiff / MINUTE).toInt()
                "через ${TimeUnits.MINUTE.plural(-tmp)}"
            }
            in -75 * MINUTE..-46 * MINUTE -> "через час"
            in -22 * HOUR..-76 * MINUTE -> {
                val tmp = (timeDiff / HOUR).toInt()
                "через ${TimeUnits.HOUR.plural(-tmp)}"
            }
            in -26 * HOUR..-23 * HOUR -> "через день"
            in -360 * DAY..-27 * HOUR -> {
                val tmp = (timeDiff / DAY).toInt()
                "через ${TimeUnits.DAY.plural(-tmp)}"
            }
            else -> "более чем через год"
        }
    }
}

val Int.asPlurals
    get() = when {
        this % 10 == 1 -> Plurals.ONE
        this % 10 in 2..4 -> Plurals.FEW
        else -> Plurals.MUCH
    }

val TimeUnits.pluralStrings
    get() = when (this) {
        TimeUnits.SECOND -> mapOf(
            Plurals.ONE to "секунду",
            Plurals.FEW to "секунды",
            Plurals.MUCH to "секунд"
        )
        TimeUnits.MINUTE -> mapOf(
            Plurals.ONE to "минуту",
            Plurals.FEW to "минуты",
            Plurals.MUCH to "минут"
        )
        TimeUnits.HOUR -> mapOf(
            Plurals.ONE to "час",
            Plurals.FEW to "часа",
            Plurals.MUCH to "часов"
        )
        TimeUnits.DAY -> mapOf(Plurals.ONE to "день", Plurals.FEW to "дня", Plurals.MUCH to "дней")
    }

enum class TimeUnits(val size: Long) {
    SECOND(1000L),
    MINUTE(60 * SECOND.size),
    HOUR(60 * MINUTE.size),
    DAY(24 * HOUR.size);

    fun plural(value: Int): String = "$value ${pluralStrings[value.asPlurals]}"
}

enum class Plurals {
    ONE,
    FEW,
    MUCH
}