package com.example.marketmind.screens.contribuicoes

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marketmind.Adapter.ContributionListAdapter
import com.example.marketmind.Adapter.Listeners.ContributionClickListener
import com.example.marketmind.Helper.Helper
import com.example.marketmind.Model.Contribution
import com.example.marketmind.Model.User
import com.example.marketmind.R
import com.example.marketmind.databinding.FragmentContribuicoesBinding
import java.util.Locale

class ContribuicoesFragment : Fragment(), ContributionClickListener {

    companion object {
        fun newInstance() = ContribuicoesFragment()
    }

    private lateinit var viewModel: ContribuicoesViewModel
    private lateinit var adapter:ContributionListAdapter
    private lateinit var storage:SharedPreferences
    private var selectedType = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentContribuicoesBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_contribuicoes, container, false)
        val recyclerView = binding.contribuicaoView
        storage = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)
        viewModel  = ViewModelProvider(this).get(ContribuicoesViewModel::class.java)
        var order = -1

        val list = viewModel.listContribution(storage.getInt("id",0))

        adapter = ContributionListAdapter(list,this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        //CUSTOM TEXT
        binding.textView23.setText(adapter.itemCount.toString())
        val customName = binding.root.findViewById<TextView>(R.id.custom_name)
        val profile = binding.root.findViewById<Button>(R.id.profile_pic)

        val userData = viewModel.getChurchByUser(storage.getInt("id",0))
        customName.setText(userData.user.name.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        })
        profile.setText(userData.user.name.substring(0,1)
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
        //new contribution
        binding.button6.setOnClickListener{
            val name = binding.churchNameInput4.text.toString()
            val type = selectedType
            val price = binding.apelidoInput4.text.toString().toDouble()
            val id = storage.getInt("id",0)
            val res = viewModel.registerContribution(Contribution(name,price,type,0,User(id = id)),id)

            Toast.makeText(requireContext(),res,Toast.LENGTH_SHORT).show()
            //clear form
            if(res == "Adicionado com sucesso") {
                binding.churchNameInput4.setText("")
                binding.apelidoInput4.setText("0")
            }

            //update list in realtime
            val list = viewModel.listContribution(storage.getInt("id",0))
            adapter.updateList(list)

        }

        //change order of list

        binding.imageButton3.setOnClickListener{
            val userId = storage.getInt("id",0)
            if(order == -1){
                val list = viewModel.orderedList(userId)
                adapter.updateList(list)
                order = 1
            }else{
                val list = viewModel.listContribution(userId)
                adapter.updateList(list)
                order = -1
            }
        }

        Helper.getGreetings(binding.root)

        //load list type
//        val list2 = arrayOf("item 1", "item 2", "item 3")
        val list2 = arrayOf("Em Mão", "Mpesa", "Emola", "Banco")

        val typeAdapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,list2)
        binding.nomeInput4.adapter = typeAdapter

        binding.nomeInput4.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
               selectedType = list2[p2].toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ContribuicoesViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onClick(contribuicao: Contribution, view: View) {
        val popupMenu = PopupMenu(requireContext(),view)
        popupMenu.inflate(R.menu.contribuicao_dropdown)
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.c1 -> {
                    viewModel.deleteOne(contribuicao)
                    val list = viewModel.listContribution(storage.getInt("id",0))
                    adapter.updateList(list)
                }

                else -> {

                }
            }
            true
        }

        popupMenu.show()
    }
}