package ru.skillbranch.devintensive.extensions

fun String.truncate(limit: Int = 16) : String {
    val str = this.trim()
    when (true){
        str.length <= limit -> return str
        else -> return "${str.substring(0,limit).trim()}..."
    }
}