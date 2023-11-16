package com.example.marketmind.screens.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.marketmind.Repository.UserRepository

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val applicationContext = getApplication<Application>().applicationContext
    private val userRepository = UserRepository(applicationContext)

    //login function
    fun loginUser(email:String, password:String):Int{
        val user = userRepository.login(email,password)
        return user.id
    }
}