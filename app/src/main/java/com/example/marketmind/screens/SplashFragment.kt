package com.example.marketmind.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.marketmind.R
import com.example.marketmind.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private lateinit var binding:FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_splash,container,false)

//        view.findViewById<TextView>(R.id.)

        return binding.root
    }
}