package io.treblekit.utils

import android.content.res.Resources
import androidx.annotation.RawRes
import java.util.Scanner

fun Resources.loadShader(@RawRes id: Int): String? {
    try {
        val openRawResource = openRawResource(id)
        try {
            val scanner = Scanner(openRawResource)
            try {
                val builder = kotlin.text.StringBuilder()
                while (scanner.hasNextLine()) {
                    builder.append(scanner.nextLine())
                    builder.append("\n")
                }
                val str = builder.toString()
                scanner.close()
                openRawResource.close()
                return str
            } finally {
            }
        } finally {
        }
    } catch (_: Exception) {
        return null
    }
}