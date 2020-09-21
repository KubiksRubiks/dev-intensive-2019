package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> = when {
        fullName?.trim() == null -> null to null
        fullName.trim().isEmpty() -> null to null
        else -> fullName.trim().split(" ").let { it.getOrNull(0) to it.getOrNull(1)
        }
    }
}
