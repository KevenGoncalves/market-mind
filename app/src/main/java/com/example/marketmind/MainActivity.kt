package com.example.marketmind

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.marketmind.databinding.ActivityMainBinding
import com.example.marketmind.screens.contribuicoes.ContribuicoesFragment
import com.example.marketmind.screens.dashboard.DashboardFragment
import com.example.marketmind.screens.definicoes.DefinicoesFragment
import com.example.marketmind.screens.despesas.DespesasFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

   private lateinit var binding: ActivityMainBinding
   private lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navView= binding.bottomNavigationView
        setupNavigation()

        val dashboardFragment = DashboardFragment()
        val despesasFragment = DespesasFragment()
        val contribuicoesFragment = ContribuicoesFragment()
        val definicoesFragment = DefinicoesFragment()

        navView.setOnItemSelectedListener {
            when(it.itemId){
                    R.id.dashboard -> setCurrentFragment(dashboardFragment)
                    R.id.despesas -> setCurrentFragment(despesasFragment)
                    R.id.contribuicos -> setCurrentFragment(contribuicoesFragment)
                    R.id.definicoes -> setCurrentFragment(definicoesFragment)
                else -> setCurrentFragment(dashboardFragment)
            }
        }
    }

    private fun setupNavigation() {
        val navHost = supportFragmentManager.findFragmentById(R.id.myNavHost) as NavHostFragment
        val navController = navHost.navController

        navController.addOnDestinationChangedListener { _, destination, _->
            when (destination.id) {
                R.id.loginFragment-> navView.visibility = View.GONE
                R.id.registerFragment-> navView.visibility = View.GONE
                else -> navView.visibility = View.VISIBLE
            }
        }
    }

    private fun setCurrentFragment(fragment:Fragment): Boolean {
        supportFragmentManager.beginTransaction().
        replace(R.id.myNavHost,fragment)
            .commit()

        return false
    }

}