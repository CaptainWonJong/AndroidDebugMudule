package com.wonjong.debugging.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wonjong.debugging.DebuggingProvider
import com.wonjong.debugging.db.DebuggingPref
import com.wonjong.debugging.utils.SingleLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * @author CaptainWonJong@gmail.com
 */
@ExperimentalCoroutinesApi
class AppDebuggingViewModel(
    private val pref: DebuggingPref
) : ViewModel() {
    val appVersion = DebuggingProvider.applicationContext?.let {
        it.packageManager.getPackageInfo(it.packageName, 0).versionName
    }
    val appName = DebuggingProvider.applicationContext?.let {
        it.applicationInfo.loadLabel(it.packageManager)
    }.toString()
    val deviceManufacturer: String = android.os.Build.MANUFACTURER
    val deviceModel: String = android.os.Build.MODEL
    val osVersion: String = android.os.Build.VERSION.RELEASE
    val guid = pref.deviceId.get()
    val pushToken = pref.pushToken.get()
    val apiPrefixUrl = pref.apiPrefixUrl.get()

    val backClickEvent = SingleLiveData<Unit>()
    fun onBackClickEventListener() = backClickEvent.call()

    @Suppress("UNCHECKED_CAST")
    class ViewModelFactory(
        private val pref: DebuggingPref
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(AppDebuggingViewModel::class.java)) {
                AppDebuggingViewModel(pref = pref) as T
            } else {
                throw IllegalArgumentException()
            }
        }
    }
}