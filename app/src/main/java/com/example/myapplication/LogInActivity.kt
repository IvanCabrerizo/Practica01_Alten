package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import com.example.myapplication.databinding.ActivityLogInBinding

class LogInActivity : AppCompatActivity(), TextWatcher {

    private lateinit var binding: ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginInputEmail.addTextChangedListener(this)

        binding.loginInputPassword.addTextChangedListener(this)

        binding.loginBtnViewPassword.setOnClickListener {
            if(binding.loginInputPassword.transformationMethod == null){
                binding.loginInputPassword.transformationMethod = PasswordTransformationMethod()
            }
            else{
                binding.loginInputPassword.transformationMethod = null
            }
        }
    }

    fun checkMail(mail: String): Boolean{
        val regex = Regex("^([\\w\\.\\-]+)@([\\w\\-]+)((\\.(\\w){2,3})+)\$")
        return regex.matches(mail)
    }

    fun checkPassword(password: String): Boolean{
        val regex = Regex("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,8}$")
        return regex.matches(password)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (checkPassword(binding.loginInputPassword.text.toString()) && checkMail(binding.loginInputEmail.text.toString())){
            binding.loginBtnEnter.isEnabled = true
        }
        else{
            binding.loginBtnEnter.isEnabled = false
        }
    }

    override fun afterTextChanged(s: Editable?) {

    }
}