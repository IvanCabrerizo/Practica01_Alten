package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.security.keystore.UserPresenceUnavailableException
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.example.myapplication.databinding.ActivityLogInBinding
import java.util.logging.Handler

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usersList = listOf(
            User("manolo@gmail.com", "Manolo92"),
            User("paco@gmail.com", "Paco92"),
            User("maria@gmail.com", "Maria92")
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
                    binding.loginBtnEnter.text = "Cargando"
                }
                Thread.sleep(1000)
                runOnUiThread{
                    binding.loginBtnEnter.isEnabled = true
                    binding.loginBtnEnter.text = "Entrar"
                    checkLoginData(binding.loginInputEmail.text.toString(), binding.loginInputPassword.text.toString(), usersList)
                }
            }.start()
        }

        binding.loginBtnClose.setOnClickListener{
            finish()
        }
    }

    private fun checkMail(mail: String): Boolean {
        val regex = Regex("^([\\w\\.\\-]+)@([\\w\\-]+)((\\.(\\w){2,3})+)\$")
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
            mailFound?.email != mail -> showToast("No existe el email")
            passwordFound?.password != password -> showToast("No existe la contraseña")
            userFound == null -> showToast("No existen usuario ni contraseña")
            else -> {
                showToast("Datos correctos")
                val intent = Intent(this, WelcomeActivity::class.java)
                intent.putExtra(userFound.email, userFound.password)
                startActivity(intent)
            }
        }
    }
}