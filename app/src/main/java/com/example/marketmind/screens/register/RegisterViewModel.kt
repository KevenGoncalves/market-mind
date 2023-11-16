package com.example.marketmind.screens.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.marketmind.Model.Account
import com.example.marketmind.Model.User
import com.example.marketmind.Repository.AccountRepository
import com.example.marketmind.Repository.UserRepository

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val applicationContext = getApplication<Application>().applicationContext
    private val userRepository = UserRepository(applicationContext)
    private val accountRepository = AccountRepository(applicationContext)
    //register function
    fun registerUser(user:User, account: Account):String{
        if(user.email.trim() == "" || user.surname.trim() == ""
            || user.name.trim() == "" || user.password.trim() == ""){
            return "Por favor, preencha todos os campos"
        }else{
            //check if user exists before create
            val check = userRepository.checkUser(user)

            if(check.id != 0){
                return "Este email ja existe"
            }else{
                val res = userRepository.registerUser(user)

                if(res.toInt() != 0){
                    //register church
                    account.user.id = res.toInt()
                    val result = accountRepository.registerChurch(account)
                    if(result.toInt() != 0){
                        return "Registado com sucesso"
                    }else{
                        return "Houve um erro, volte a tentar mais tarde"
                    }
                }else{
                    return "Houve um erro, volte a tentar mais tarde"
                }
            }
        }
    }

}