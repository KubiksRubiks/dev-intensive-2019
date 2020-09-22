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
}
