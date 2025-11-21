package com.example.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.presentation.fragments.LoginFragment

class AuthorizationActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorizations)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragContainer, LoginFragment())
            .addToBackStack(null)
            .commit()


    }
}