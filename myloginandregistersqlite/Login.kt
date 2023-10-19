package com.millenialzdev.myloginandregistersqlite

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.millenialzdev.myloginandregistersqlite.DataBaseHelperLogin



class Login : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvRegister: TextView
    private lateinit var db: DataBaseHelperLogin

    // Shared preferences
    private val SHARED_PREF_NAME = "myPref"
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvRegister = findViewById(R.id.tvRegister)

        tvRegister.setOnClickListener {
            Register.newInstance().show(supportFragmentManager, Register.TAG)
        }

        db = DataBaseHelperLogin(this)

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)

        btnLogin.setOnClickListener {
            val getUsername = etUsername.text.toString()
            val getPassword = etPassword.text.toString()

            if (getUsername.isEmpty() || getPassword.isEmpty()) {
                Toast.makeText(applicationContext, "Username atau password salah!", Toast.LENGTH_LONG).show()
            } else {
                val masuk = db.checkLogin(getUsername, getPassword)
                if (masuk == true) {
                    val updateSession = db.upgradeSession("ada", 1)
                    if (updateSession == true) {
                        Toast.makeText(applicationContext, "Berhasil Masuk", Toast.LENGTH_LONG).show()
                        val editor = sharedPreferences.edit()

                        editor.putBoolean("masuk", true)
                        editor.apply()
                        val dashboard = Intent(applicationContext, MainActivity::class.java)
                        startActivity(dashboard)
                        finish()
                    }
                } else {
                    Toast.makeText(applicationContext, "Gagal Masuk", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
