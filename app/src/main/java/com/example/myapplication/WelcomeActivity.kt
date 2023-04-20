package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private val binding by lazy { ActivityWelcomeBinding.inflate(layoutInflater) }

    companion object {
        const val KEY_NAME = "NAME"
        const val KEY_EMAIL = "EMAIL"
        const val KEY_PASSWORD = "PASSWORD"
        const val KEY_AVATAR = "AVATAR"
        const val KEY_REMEMBER = "REMEMBER"
    }

    private val userName by lazy { intent.getStringExtra(KEY_NAME) }
    private val userMail by lazy { intent.getStringExtra(KEY_EMAIL) }
    private val userPassword by lazy { intent.getStringExtra(KEY_PASSWORD) }
    private val userAvatar by lazy { intent.getStringExtra(KEY_AVATAR) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding){
            welcomeLabelName.text = userName
            welcomeLabelMail.text = userMail
            welcomeImageAvatar.glideUrl(userAvatar.toString())
            setupWelcomeBtnClose(this@WelcomeActivity)
        }
    }

    private fun goToLogin() {
        val loginIntent = if (intent.getBooleanExtra(KEY_REMEMBER, false)) {
            Intent(this, LogInActivity::class.java)
                .putExtra(KEY_EMAIL, userMail)
                .putExtra(KEY_PASSWORD, userPassword)
        } else {
            Intent(this, LogInActivity::class.java)
        }
        startActivity(loginIntent)
        finish()
    }

    private fun ActivityWelcomeBinding.setupWelcomeBtnClose(context: Context) {
        welcomeBtnClose.setOnClickListener {
            AlertDialog.Builder(context).apply {
                setTitle(getString(R.string.welcome_dialog_tittle))
                setMessage(getString(R.string.welcome_dialog_message))
                setPositiveButton(getString(R.string.welcome_yes)) { dialog, which ->
                    goToLogin()
                }
                setNegativeButton(getString(R.string.welcome_no)) { dialog, which ->
                    dialog.dismiss()
                }
            }.create().show()
        }
    }

    private fun ImageView.glideUrl(url: String) {
        Glide.with(this).load(url).into(this)
    }
}