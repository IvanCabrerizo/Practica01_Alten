package com.example.myapplication

import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.myapplication.databinding.ActivityLogInBinding


class LogInActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLogInBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            setupLogInBtnEnter()
            setupLogInBtnClose()
            setupLogInInputEmail()
            setupLogInInputPassword()
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

    private fun checkLoginData(mail: String, password: String, userList: List<User>) {
        val userFound = userList.find { user -> user.email == mail && user.password == password }
        val mailFound = userList.find { user -> user.email == mail }
        val passwordFound = userList.find { user -> user.password == password }

        showToast(getString(when {
            mailFound?.email != mail -> R.string.login_no_exist_mail
            passwordFound?.password != password -> R.string.login_no_exist_password
            userFound == null -> R.string.login_no_exist_user
            else -> {
                val intent = Intent(this, WelcomeActivity::class.java)
                intent.putExtra("EMAIL", userFound.password)
                startActivity(intent)
                R.string.login_correct_user
            }
        }))

    }

    private fun ActivityLogInBinding.setupLogInBtnEnter() {
        loginBtnEnter.setOnClickListener {
            Thread {
                runOnUiThread {
                    binding.loginBtnEnter.isEnabled = false
                    binding.loginBtnEnter.text = getString(R.string.login_loading)
                }
                Thread.sleep(1000)
            }.start()
            runOnUiThread {
                binding.loginBtnEnter.isEnabled = true
                binding.loginBtnEnter.text = getString(R.string.login_enter)
                checkLoginData(
                    binding.loginInputEmail.text.toString(),
                    binding.loginInputPassword.text.toString(),
                    usersList
                )
            }
        }
    }

    private fun ActivityLogInBinding.setupLogInBtnClose() {
        loginBtnClose.setOnClickListener {
            finishAffinity()
        }
    }

    private fun ActivityLogInBinding.setupLogInInputEmail() {
        loginInputEmail.addTextChangedListener {
            binding.loginBtnEnter.isEnabled =
                checkMail(binding.loginInputEmail.text.toString()) && checkPassword(binding.loginInputPassword.text.toString())
        }
    }

    private fun ActivityLogInBinding.setupLogInInputPassword() {
        loginInputPassword.addTextChangedListener {
            binding.loginBtnEnter.isEnabled =
                checkMail(binding.loginInputEmail.text.toString()) && checkPassword(binding.loginInputPassword.text.toString())
        }
    }
}