package com.example.marketmind.screens.definicoes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.marketmind.Model.Account
import com.example.marketmind.Model.User
import com.example.marketmind.Repository.AccountRepository
import com.example.marketmind.Repository.UserRepository

class DefinicoesViewModel(application: Application) : AndroidViewModel(application) {
    private val applicationContext = getApplication<Application>().applicationContext
    private val userRepository = UserRepository(applicationContext)
    private val accountRepository = AccountRepository(applicationContext)

    fun getUserData(uid:Int):Account{
        return accountRepository.getChurchByUserId(uid)
    }

    fun updateOne(uid:Int, user: User, account: Account):String{
        val res = userRepository.updateUser(uid,user)

        if(res > 0 ){
            val rs = accountRepository.updateChurch(uid,account,'0')
            if(rs > 0){
                return "Dados atualizados com sucesso"
            }
        }

        return "Houve um erro"
    }
}