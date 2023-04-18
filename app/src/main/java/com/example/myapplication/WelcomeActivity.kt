package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.welcomeLabelName.text = intent.getStringExtra("NAME")
        binding.welcomeLabelMail.text = intent.getStringExtra("EMAIL")
        Glide.with(this).load(intent.getStringExtra("AVATAR")).into(binding.welcomeImageAvatar)

        binding.welcomeBtnClose.setOnClickListener {
            val builderDialog = AlertDialog.Builder(this)

            builderDialog.setTitle(getString(R.string.welcome_dialog_tittle))
            builderDialog.setMessage(getString(R.string.welcome_dialog_message))

            builderDialog.setPositiveButton("Sí") { dialog, which ->
                if(intent.getBooleanExtra("REMEMBER", false)){
                    val welcomeIntent = Intent(this, LogInActivity::class.java)
                    welcomeIntent.putExtra("EMAIL", intent.getStringExtra("EMAIL"))
                    welcomeIntent.putExtra("PASSWORD", intent.getStringExtra("PASSWORD"))
                    startActivity(welcomeIntent)
                }
                else{
                    val welcomeIntent = Intent(this, LogInActivity::class.java)
                    startActivity(welcomeIntent)
                }
            }

            builderDialog.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }

            val dialog = builderDialog.create()
            dialog.show()
        }
    }
}