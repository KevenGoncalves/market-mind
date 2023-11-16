package com.example.marketmind.screens.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.marketmind.Model.Account
import com.example.marketmind.Model.Tuition
import com.example.marketmind.Repository.AccountRepository
import com.example.marketmind.Repository.ContributionRepository
import com.example.marketmind.Repository.TuitionRespository

class DashboardViewModel(application: Application) : AndroidViewModel(application) {
    private val applicationContext = getApplication<Application>().applicationContext
    private val accountRepository = AccountRepository(applicationContext)
    private  val contributionRepository = ContributionRepository(applicationContext)
    private  val tuitionRepository = TuitionRespository(applicationContext)

    fun getChurchByUser(userId:Int):Account{
        val res = accountRepository.getChurchByUserId(userId)

        if(res.id != 0){
            return res
        }

        return Account()
    }

    fun getCountTuition(uid:Int):Int{
        return tuitionRepository.countTuition(uid)
    }

    fun getCountContribution(uid:Int):Int{
        return contributionRepository.getContributionByUserId(uid).count()
    }

    fun listRecentTuition(uid:Int):List<Tuition>{
        return tuitionRepository.getRecentTuitionByUserId(uid)
    }

    fun updateTuition(id:Int,tuition: Tuition):String{
        val res =  tuitionRepository.updateTuition(id,tuition)

        if(res > 0 ){
            val r = accountRepository.updateChurch(tuition.user.id,Account(tuition.price),'-')
            if(r > 0){
                return "Atualizado com sucesso"
            }

        }
        return "Houve um erro ao atualizar"
    }

    fun deleteOne(id:Int):Int{
        return tuitionRepository.deleteTuition(id)
    }
}