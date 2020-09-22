package edu.nitt.vortex

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import edu.nitt.vortex.databinding.ActivitySplashBinding
import edu.nitt.vortex.helpers.viewLifecycle

class SplashActivity : AppCompatActivity() {

    private val binding by viewLifecycle(ActivitySplashBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        startNextActivity()
    }

    private fun startNextActivity() {
        val intent = Intent(this, MainActivity::class.java)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(intent)
            finish()
            overridePendingTransition(0, 0)
        }, 1000)

    }
}