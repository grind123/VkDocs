package com.grind.vksecondround.utils

class StringConverter {
    companion object {
        fun convertFileSize(fileSize: Long): String? {
            return when {
                fileSize > 1000000000 -> String.format(
                    "%.1f ГБ",
                    fileSize.toDouble() / 1000000000
                )
                fileSize > 1000000 -> String.format(
                    "%.1f МБ",
                    fileSize.toDouble() / 1000000
                )
                fileSize > 1000 -> String.format(
                    "%d КБ",
                    fileSize.toInt() / 1000
                )
                else -> String.format("%d Б", fileSize)
            }
        }
    }
}