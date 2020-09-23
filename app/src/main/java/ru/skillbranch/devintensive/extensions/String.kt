package ru.skillbranch.devintensive.extensions

fun String.truncate(limit: Int = 16) : String {
    val str = this.trim()
    when (true){
        str.length <= limit -> return str
        else -> return "${str.substring(0,limit).trim()}..."
    }
}

fun String.stripHtml(): String {
    return this
        .replace(Regex("<[^>]*>"), "") //удаляет HTML-теги
        .replace(Regex("\\s+"), " ") //удаляет пустые символы (пробелы) между словами если их больше 1
        .replace(Regex("[&'\"]"), "") //удаляет html escape последовательности ("& < > ' "")
}
