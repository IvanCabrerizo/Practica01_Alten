package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private val binding by lazy { ActivityWelcomeBinding.inflate(layoutInflater) }

    companion object{
        private const val KEY_NAME = "NAME"
        private const val KEY_EMAIL = "EMAIL"
        private const val KEY_PASSWORD = "PASSWORD"
        private const val KEY_AVATAR = "AVATAR"
        private const val KEY_REMEMBER = "REMEMBER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.welcomeLabelName.text = intent.getStringExtra(KEY_NAME)
        binding.welcomeLabelMail.text = intent.getStringExtra(KEY_EMAIL)
        binding.welcomeImageAvatar.glideUrl(KEY_AVATAR)

        binding.welcomeBtnClose.setOnClickListener {
            val builderDialog = AlertDialog.Builder(this)

            builderDialog.setTitle(getString(R.string.welcome_dialog_tittle))
            builderDialog.setMessage(getString(R.string.welcome_dialog_message))

            builderDialog.setPositiveButton("Sí") { dialog, which ->

            }

            builderDialog.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }

            val dialog = builderDialog.create()
            dialog.show()
        }
    }

    fun goToLogin(){
        val loginIntent = if(intent.getBooleanExtra(KEY_REMEMBER, false)){
            LogInActivity.newIntent()
        }
        else{

        }
    }
    fun ImageView.glideUrl(url: String) {
        Glide.with(this).load(url).into(this)
    }
}