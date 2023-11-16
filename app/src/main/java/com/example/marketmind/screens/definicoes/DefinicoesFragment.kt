package com.example.marketmind.screens.definicoes

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.marketmind.Helper.Helper
import com.example.marketmind.Model.Account
import com.example.marketmind.Model.User
import com.example.marketmind.R
import com.example.marketmind.databinding.FragmentDefinicoesBinding
import java.util.Locale

class DefinicoesFragment : Fragment() {

    companion object {
        fun newInstance() = DefinicoesFragment()
    }

    private lateinit var viewModel: DefinicoesViewModel
    private lateinit var storage:SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentDefinicoesBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_definicoes, container, false)
        storage = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)
        viewModel = ViewModelProvider(this).get(DefinicoesViewModel::class.java)

        val userId = storage.getInt("id",0)

        val name = binding.nomeInput5
        val surname = binding.apelidoInput5
        val email = binding.emailInput2
        val pass = binding.passwordInput2
        val profile = binding.root.findViewById<Button>(R.id.profile_pic)
        val ctext = binding.root.findViewById<TextView>(R.id.custom_name)

        //load data to inputs
        val userData = viewModel.getUserData(userId)

        name.setText(userData.user.name)
        surname.setText(userData.user.surname)
        email.setText(userData.user.email)
        profile.setText(userData.user.name.substring(0,1)
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
        ctext.setText(userData.user.name.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        })

        //update user data listener
        binding.button7.setOnClickListener{
            val user = User(name.text.toString(),surname.text.toString(),
            email.text.toString(),pass.text.toString())

            val account = Account()

            val rs = viewModel.updateOne(userId,user,account)

            Toast.makeText(requireContext(),rs,Toast.LENGTH_SHORT).show()
        }

        Helper.getGreetings(binding.root)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DefinicoesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}