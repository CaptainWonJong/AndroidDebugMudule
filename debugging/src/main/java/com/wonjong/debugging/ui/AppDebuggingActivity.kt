package com.wonjong.debugging.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.wonjong.debugging.R
import com.wonjong.debugging.databinding.ActivityAppDebuggingBinding
import com.wonjong.debugging.db.DebuggingPref
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * @author CaptainWonJong@gmail.com
 */
@ExperimentalCoroutinesApi
class AppDebuggingActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityAppDebuggingBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
    }
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            AppDebuggingViewModel.ViewModelFactory(DebuggingPref(this))
        ).get(AppDebuggingViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.model = viewModel
        observeLiveData()
        with(binding) {
            listOf(tvAppName, tvAppVersion, tvDeviceManufacturer, tvDeviceModel, tvGuid, tvOsVersion, tvPushToken).let {
                it.setOnClickEvent()
                it.setOnLongClickEvent()
            }
        }
    }

    private fun observeLiveData() {
        viewModel.backClickEvent.observe(this) {
            onBackPressed()
        }
    }

    private fun List<TextView>?.setOnClickEvent() {
        this?.forEach { textView ->
            textView.setOnClickListener {
                Toast.makeText(this@AppDebuggingActivity, textView.text, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun List<TextView>?.setOnLongClickEvent() {
        this?.forEach { textView ->
            textView.setOnLongClickListener {
                (getSystemService(CLIPBOARD_SERVICE) as? ClipboardManager)?.setPrimaryClip(
                    ClipData.newPlainText("debugging", textView.text)
                ).also {
                    Toast.makeText(
                        this@AppDebuggingActivity,
                        getString(R.string.debugging_msg_copy_to_clipboard),
                        Toast.LENGTH_LONG
                    ).show()
                }
                true
            }
        }
    }
}