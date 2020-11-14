package com.wonjong.debugging.db.type

import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

internal class StringPreference(
    override val key: String,
    override val defaultValue: String?,
    keyFlow: Flow<String?>,
    private val sharedPreferences: SharedPreferences,
    private val coroutineContext: CoroutineContext
) : BaseTypePreference<String?>(key, keyFlow, sharedPreferences, coroutineContext) {

    override fun get() = sharedPreferences.getString(key, defaultValue)

    override fun set(value: String?) = sharedPreferences.edit().putString(key, value).apply()

    override suspend fun setAndCommit(value: String?) = withContext(coroutineContext) {
        sharedPreferences.edit().putString(key, value).commit()
    }
}
