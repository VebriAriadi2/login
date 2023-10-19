package com.millenialzdev.myloginandregistersqlite

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var btnKeluar: Button
    private lateinit var db: DataBaseHelperLogin

    companion object {
        const val SHARED_PREF_NAME = "myPref"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnKeluar = findViewById(R.id.btnKeluar)

        db = DataBaseHelperLogin(this)

        val sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)

        val checkSession = db.checkSession("ada")
        if (!checkSession) {
            val login = Intent(applicationContext, Login::class.java)
            startActivity(login)
            finish()
        }

        btnKeluar.setOnClickListener {
            val updateSession = db.upgradeSession("kosong", 1)
            if (updateSession) {
                Toast.makeText(applicationContext, "Berhasil Keluar", Toast.LENGTH_LONG).show()

                val editor = sharedPreferences.edit()
                editor.putBoolean("masuk", false)
                editor.apply()

                val keluar = Intent(applicationContext, Login::class.java)
                startActivity(keluar)
                finish()
            }
        }
    }
}
