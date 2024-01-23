package com.dicoding.userapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.dicoding.userapplication.R
import com.dicoding.userapplication.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val topAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.top_text_anim)
        val middleAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.bottom_text_anim)
        val bottomAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.middle_text_anim)

        binding.tvTop.startAnimation(topAnimation)
        binding.tvMiddle.startAnimation(middleAnimation)
        binding.tvBottom.startAnimation(bottomAnimation)

        supportActionBar?.hide()
    }

    override fun onStart() {
        super.onStart()

        val splashScreenTimeOut: Long = 3000

        Handler(Looper.myLooper() ?: return).postDelayed({
            goToMainActivity()
        }, splashScreenTimeOut)
    }

    private fun goToMainActivity() {
        val mainActivityIntent = Intent(this@SplashScreenActivity, MainActivity::class.java)
        startActivity(mainActivityIntent)
        finish()
    }
}