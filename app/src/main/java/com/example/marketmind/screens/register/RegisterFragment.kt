package com.example.marketmind.screens.register

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.marketmind.Model.Account
import com.example.marketmind.Model.User
import com.example.marketmind.R
import com.example.marketmind.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentRegisterBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_register,container, false)

        binding.textView8.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.button.setOnClickListener{
            //get all the input data
            val name = binding.nomeInput.text.toString()
            val email = binding.emailInput.text.toString()
            val surname = binding.apelidoInput.text.toString()
            val password = binding.passwordInput.text.toString()

            //organize the data to send to repo
            val user = User(name,surname,email,password)
            val account = Account(0.0,user)
            //send data to repo
            val res = viewModel.registerUser(user,account)
            Toast.makeText(requireContext(),res,Toast.LENGTH_LONG).show()

            //clear for on success register
            if(res == "Registado com sucesso"){
                binding.emailInput.setText("")
                binding.nomeInput.setText("")
                binding.apelidoInput.setText("")
                binding.passwordInput.setText("")

                //go back to login
                findNavController().popBackStack()
            }

        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        // TODO: Use the ViewModel
    }


}