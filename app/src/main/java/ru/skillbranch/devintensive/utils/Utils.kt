package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> = when {
        fullName?.trim() == null -> null to null
        fullName.trim().isEmpty() -> null to null
        else -> fullName.trim().split(" ").let { it.getOrNull(0) to it.getOrNull(1)
        }
    }

    fun toInitials(firstName: String?, lastName: String?): String? = when {
        (firstName?.trim().isNullOrEmpty() && lastName?.trim().isNullOrEmpty()) -> null
        (firstName != null && lastName.isNullOrEmpty()) ->
            firstName.trim().first().toString().toUpperCase()
        (firstName?.trim().isNullOrEmpty() && lastName != null) ->
            lastName.trim().first().toString().toUpperCase()
        else -> (firstName!!.trim().first().toString().toUpperCase() + lastName!!.trim().first().toString().toUpperCase())
    }

    fun transliteration(payload: String, divider: String = " "): String {
        var str: String = ""
        var payload = payload.trim()
        for (i in 0..payload.length - 1) {
            when (true) {
                payload[i].isUpperCase() -> {
                    var tmp = replaceRusToEngLetter(payload[i].toString().toLowerCase(), divider)
                    if (tmp.length > 1) {
                        str += tmp[0].toUpperCase()
                        var j = 1
                        while(j < tmp.length){
                            str += tmp[j]
                            j++
                        }
                    } else str += tmp.toUpperCase()
                }
                else -> str += replaceRusToEngLetter(payload[i].toString(), divider)
            }
        }
        return str
    }

    fun replaceRusToEngLetter(letter: String, div: String): String = when (letter) {
        "а" -> "a"
        "б" -> "b"
        "в" -> "v"
        "г" -> "g"
        "д" -> "d"
        "е" -> "e"
        "ё" -> "e"
        "ж" -> "zh"
        "з" -> "z"
        "и" -> "i"
        "й" -> "i"
        "к" -> "k"
        "л" -> "l"
        "м" -> "m"
        "н" -> "n"
        "о" -> "o"
        "п" -> "p"
        "р" -> "r"
        "с" -> "s"
        "т" -> "t"
        "у" -> "u"
        "ф" -> "f"
        "х" -> "h"
        "ц" -> "c"
        "ч" -> "ch"
        "ш" -> "sh"
        "щ" -> "sh'"
        "ъ" -> ""
        "ы" -> "i"
        "ь" -> ""
        "э" -> "e"
        "ю" -> "yu"
        "я" -> "ya"
        " " -> div
        else -> letter
    }
}
