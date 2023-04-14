package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.myapplication.databinding.ActivityLogInBinding

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginInputEmail.addTextChangedListener {
            binding.loginBtnEnter.isEnabled = checkMail(binding.loginInputEmail.text.toString()) && checkPassword(binding.loginInputPassword.text.toString())
        }

        binding.loginInputPassword.addTextChangedListener {
            binding.loginBtnEnter.isEnabled = checkMail(binding.loginInputEmail.text.toString()) && checkPassword(binding.loginInputPassword.text.toString())
        }
    }

    fun checkMail(mail: String): Boolean {
        val regex = Regex("^([\\w\\.\\-]+)@([\\w\\-]+)((\\.(\\w){2,3})+)\$")
        return regex.matches(mail)
    }

    fun checkPassword(password: String): Boolean {
        val regex = Regex("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,8}$")
        return regex.matches(password)
    }
}