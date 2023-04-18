package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.text.Editable

import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.myapplication.databinding.ActivityLogInBinding


class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val emailRemember = Editable.Factory.getInstance().newEditable(intent.getStringExtra("EMAIL") ?:"")
        val passwordRemember = Editable.Factory.getInstance().newEditable(intent.getStringExtra("PASSWORD") ?:"")


        binding.loginInputEmail.text = emailRemember
        binding.loginInputPassword.text = passwordRemember

        val usersList = listOf(
            User("Manolo", "manolo@gmail.com", "Manolo92", "https://upload.wikimedia.org/wikipedia/commons/thumb/4/48/Outdoors-man-portrait_%28cropped%29.jpg/800px-Outdoors-man-portrait_%28cropped%29.jpg"),
            User("Paco", "paco@gmail.com", "Paco92", "https://www.westyorkshire.police.uk/sites/default/files/2022-11/matthew_fisher.jpg"),
            User("Maria", "maria@gmail.com", "Maria92", "https://upload.wikimedia.org/wikipedia/commons/1/1f/Woman_1.jpg")
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
                runOnUiThread {
                    binding.loginBtnEnter.isEnabled = true
                    binding.loginBtnEnter.text = getString(R.string.login_enter)
                    checkLoginData(binding.loginInputEmail.text.toString(), binding.loginInputPassword.text.toString(), usersList)
                }
            }.start()
        }

        binding.loginBtnClose.setOnClickListener{
            finishAffinity()
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
                val loginIntent = Intent(this, WelcomeActivity::class.java)
                loginIntent.putExtra("NAME", userFound.name)
                loginIntent.putExtra("EMAIL", userFound.email)
                loginIntent.putExtra("AVATAR", userFound.avatar)
                loginIntent.putExtra("PASSWORD", userFound.password)
                loginIntent.putExtra("REMEMBER", binding.loginSwitchRemember.isChecked)
                startActivity(loginIntent)
            }
        }
    }
}