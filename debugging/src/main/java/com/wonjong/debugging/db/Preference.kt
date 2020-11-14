package com.wonjong.debugging.db

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

/**
 * @author CaptainWonJong@gmail.com
 */
interface Preference<T> {

    val key: String

    val defaultValue: T

    fun get(): T

    fun set(value: T)

    suspend fun setAndCommit(value: T): Boolean

    fun isSet(): Boolean

    fun isNotSet(): Boolean

    fun delete()

    suspend fun deleteAndCommit(): Boolean

    fun asFlow(): Flow<T>

    fun asCollector(): FlowCollector<T>

    fun asSyncCollector(throwOnFailure: Boolean = false): FlowCollector<T>
}
