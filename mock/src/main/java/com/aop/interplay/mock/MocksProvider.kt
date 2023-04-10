package com.aop.interplay.mock

import android.content.Context

object MocksProvider {

    private fun readJson(context: Context, fileName: String): String? {
        return try {
            context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }
}
