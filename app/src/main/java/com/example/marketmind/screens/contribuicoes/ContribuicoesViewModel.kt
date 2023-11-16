package com.example.marketmind.screens.contribuicoes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.marketmind.Model.Account
import com.example.marketmind.Model.Contribution
import com.example.marketmind.Repository.AccountRepository
import com.example.marketmind.Repository.ContributionRepository

class ContribuicoesViewModel(application: Application) : AndroidViewModel(application) {
    private val applicationContext = getApplication<Application>().applicationContext
    private val contributionRepository = ContributionRepository(applicationContext)

    private val accountRepository = AccountRepository(applicationContext)

    fun getChurchByUser(userId:Int):Account{
        val res = accountRepository.getChurchByUserId(userId)

        if(res.id != 0){
            return res
        }

        return Account()
    }

    fun registerContribution(contribution: Contribution, uid:Int):String{

        if(contribution.title == "" || contribution.price <= 0 || contribution.type == ""){
            return "Dados preenchidos nao sao validos"
        }
        val res = contributionRepository.createContribution(contribution)

        if(res.toInt() > 0){
            val result = AccountRepository(applicationContext)
                .updateChurch(uid, Account(contribution.price),'+')

            if(result > 0){
                return "Adicionado com sucesso"
            }

        }

        return "Houve um erro ao adicionar"
    }

    fun listContribution(uid:Int):List<Contribution>{
        return contributionRepository.getContributionByUserId(uid)
    }

    fun deleteOne(contribution: Contribution):String{
        val res = contributionRepository.deleteContribution(contribution.id)

        if(res != 0){
            val result = AccountRepository(applicationContext)
                .updateChurch(contribution.user.id, Account(contribution.price),'-')
           if(result != 0){
               return "Apagado com sucesso"
           }
        }

        return "Erro ao apagar"
    }

    fun orderedList(uid:Int):List<Contribution>{
        return contributionRepository.getContributionByUserIdDesc(uid)
    }
}