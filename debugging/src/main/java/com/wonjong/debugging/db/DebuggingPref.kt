package com.wonjong.debugging.db

import android.content.Context
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * @author CaptainWonJong@gmail.com
 */
@ExperimentalCoroutinesApi
class DebuggingPref(context: Context) {
    private val flowPreference by lazy {
        FlowSharedPreferences(context.getSharedPreferences("app_debugging_preference", Context.MODE_PRIVATE))
    }

    var pushToken = flowPreference.getString(PUSH_TOKEN)
    var apiPrefixUrl = flowPreference.getString(API_PREFIX_URL)
    var deviceId = flowPreference.getString(DEVICE_ID)
}

private const val PUSH_TOKEN = "PUSH_TOKEN"
private const val API_PREFIX_URL = "API_PREFIX_URL"
private const val DEVICE_ID = "DEVICE_ID"