package com.example.marketmind.screens.login

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.marketmind.R
import com.example.marketmind.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel
    private lateinit var storage:SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLoginBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_login,container, false)
        storage = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)

        //listeners
        binding.textView5.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.button3.setOnClickListener{
            val login = viewModel.loginUser(binding.editTextTextEmailAddress2.text.toString(),binding.editTextTextPassword.text.toString())
            if(login != 0) {
                //store data to localStorage
                val editor = storage.edit()
                editor.putInt("id",login)
                editor.apply()

                Navigation.findNavController(it)
                    .navigate(R.id.action_loginFragment_to_dashboardFragment)
            }else{
                Toast.makeText(requireContext(),"Dados incorrectos",Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }


}