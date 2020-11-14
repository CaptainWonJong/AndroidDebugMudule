package com.wonjong.debugging.db.type

import android.content.SharedPreferences
import com.wonjong.debugging.db.Preference
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

internal abstract class BaseTypePreference<T>(
  override val key: String,
  private val keyFlow: Flow<String?>,
  private val sharedPreferences: SharedPreferences,
  private val coroutineContext: CoroutineContext
) : Preference<T> {

    private class ValueNotPersistedException(message: String) : RuntimeException(message)

    override fun isSet() = sharedPreferences.contains(key)

    override fun isNotSet() = !sharedPreferences.contains(key)

    override fun delete() = sharedPreferences.edit().remove(key).apply()

    override suspend fun deleteAndCommit() = withContext(coroutineContext) {
        sharedPreferences.edit().remove(key).commit()
    }

    @ExperimentalCoroutinesApi
    override fun asFlow() =
        keyFlow
            .filter { it == key || it == null } // null means preferences were cleared (Android R+ exclusive behavior)
            .onStart { emit("first load trigger") }
            .map { get() }
            .conflate()

    override fun asCollector() = object : FlowCollector<T> {
        override suspend fun emit(value: T) = set(value)
    }

    override fun asSyncCollector(throwOnFailure: Boolean) = object : FlowCollector<T> {
        override suspend fun emit(value: T) {
            if (!setAndCommit(value) && throwOnFailure) {
                throw ValueNotPersistedException("Value [$value] for key [$key] failed to be written to persistent storage.")
            }
        }
    }
}
