package com.millenialzdev.myloginandregistersqlite

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val decorView: View = window.decorView
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        Handler().postDelayed({
            val sharedPreferences = getSharedPreferences("myPref", 0)
            val logged = sharedPreferences.getBoolean("masuk", false)

            if (logged) {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            } else {
                startActivity(Intent(applicationContext, Login::class.java))
                finish()
            }
        }, 3000)
    }
}
