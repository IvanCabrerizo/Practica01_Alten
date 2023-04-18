package com.example.myapplication

import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.myapplication.databinding.ActivityLogInBinding


class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usersList = listOf(
            User("Manolo", "manolo@gmail.com", "Manolo92"),
            User("Paco", "paco@gmail.com", "Paco92"),
            User("Maria", "maria@gmail.com", "Maria92")
        )

        binding.loginInputEmail.addTextChangedListener {
            binding.loginBtnEnter.isEnabled = checkMail(binding.loginInputEmail.text.toString()) && checkPassword(binding.loginInputPassword.text.toString())
        }

        binding.loginInputPassword.addTextChangedListener {
            binding.loginBtnEnter.isEnabled = checkMail(binding.loginInputEmail.text.toString()) && checkPassword(binding.loginInputPassword.text.toString())
        }

        binding.loginBtnEnter.setOnClickListener {
            Thread {
                runOnUiThread {
                    binding.loginBtnEnter.isEnabled = false
                    binding.loginBtnEnter.text = getString(R.string.login_loading)
                }
                Thread.sleep(1000)
            }.start()
            binding.loginBtnEnter.isEnabled = true
            binding.loginBtnEnter.text = getString(R.string.login_enter)
            checkLoginData(binding.loginInputEmail.text.toString(), binding.loginInputPassword.text.toString(), usersList)
        }

        binding.loginBtnClose.setOnClickListener{
            finish()
        }
    }

    private fun checkMail(mail: String): Boolean {
        val regex = Regex("^([\\w.\\-]+)@([\\w\\-]+)((\\.(\\w){2,3})+)\$")
        return regex.matches(mail)
    }

    private fun checkPassword(password: String): Boolean {
        val regex = Regex("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,8}$")
        return regex.matches(password)
    }

    private fun checkLoginData(mail: String, password: String, userList: List<User>){
        val userFound = userList.find { user -> user.email == mail && user.password == password}
        val mailFound = userList.find { user -> user.email == mail }
        val passwordFound = userList.find { user -> user.password == password }

        when {
            mailFound?.email != mail -> showToast(getString(R.string.login_no_exist_mail))
            passwordFound?.password != password -> showToast(getString(R.string.login_no_exist_password))
            userFound == null -> showToast(getString(R.string.login_no_exist_user))
            else -> {
                showToast(getString(R.string.login_correct_user))
                val intent = Intent(this, WelcomeActivity::class.java)
                intent.putExtra("NAME", userFound.name)
                startActivity(intent)
            }
        }
    }
}