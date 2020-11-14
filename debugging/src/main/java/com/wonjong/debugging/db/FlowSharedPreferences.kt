package com.wonjong.debugging.db

import android.content.SharedPreferences
import com.wonjong.debugging.db.type.BooleanPreference
import com.wonjong.debugging.db.type.StringPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlin.coroutines.CoroutineContext

/**
 * @author CaptainWonJong@gmail.com
 */
@ExperimentalCoroutinesApi
class FlowSharedPreferences @JvmOverloads constructor(
    private val sharedPreferences: SharedPreferences,
    private val coroutineContext: CoroutineContext = Dispatchers.IO
) {
    private val keyFlow: Flow<String?> = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            offer(key)
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
        awaitClose {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }

    @JvmOverloads
    fun getBoolean(key: String, defaultValue: Boolean = false): Preference<Boolean> =
        BooleanPreference(key, defaultValue, keyFlow, sharedPreferences, coroutineContext)

    @JvmOverloads
    fun getString(key: String, defaultValue: String? = null): Preference<String?> =
        StringPreference(key, defaultValue, keyFlow, sharedPreferences, coroutineContext)

    fun clear() = sharedPreferences.edit().clear().apply()
}
