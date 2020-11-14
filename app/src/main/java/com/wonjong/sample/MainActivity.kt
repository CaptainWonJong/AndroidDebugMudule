package com.wonjong.sample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wonjong.debugging.db.DebuggingPref
import com.wonjong.debugging.ui.AppDebuggingActivity
import com.wonjong.sample.databinding.ActivityMainBinding
import java.util.*

@Suppress("EXPERIMENTAL_API_USAGE")
class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        DebuggingPref(this).apply {
            apiPrefixUrl.set("SET YOUR API PREFIX URL")
            deviceId.set(UUID.randomUUID().toString()) // this is example. set your device id.
            pushToken.set("SET YOUR PUSH TOKEN")
        }

        binding.btnTest.setOnClickListener {
            startActivity(Intent(this, AppDebuggingActivity::class.java))
        }
    }
}