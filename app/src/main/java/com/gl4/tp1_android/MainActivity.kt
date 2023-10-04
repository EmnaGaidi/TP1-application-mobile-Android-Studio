package com.gl4.tp1_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var txtEmail : EditText
    lateinit var txtPassword : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtEmail = findViewById(R.id.editText)
        txtPassword = findViewById(R.id.editTextTextPassword)

    }

    fun login(view: View) {
        txtEmail = findViewById(R.id.editText)
        txtPassword = findViewById(R.id.editTextTextPassword)
        var email = txtEmail.text.toString()
        var password = txtPassword.text.toString()
        if (email == "gl4@insat.tn" && password == "insat2022"){
            val intent = Intent(this,WelcomeActivity::class.java)
            intent.putExtra("email",email)
            startActivity(intent)
        }else{
            showToast("Authentification échouée")
        }
    }

    private fun showToast(message:String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}