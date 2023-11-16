package com.example.marketmind.screens.despesas

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
import com.example.marketmind.Adapter.TuitionListAdapter
import com.example.marketmind.Adapter.Listeners.TuitionClickListener
import com.example.marketmind.Helper.Helper
import com.example.marketmind.Model.Tuition
import com.example.marketmind.Model.User
import com.example.marketmind.R
import com.example.marketmind.databinding.FragmentDespesasBinding
import java.util.Locale

class DespesasFragment : Fragment(), TuitionClickListener {

    companion object {
        fun newInstance() = DespesasFragment()
    }

    private lateinit var viewModel: DespesasViewModel
    private lateinit var adapter: TuitionListAdapter
    private lateinit var storage:SharedPreferences
    private var selectedType = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentDespesasBinding =  DataBindingUtil.inflate(inflater,R.layout.fragment_despesas, container, false)
        val recyclerView = binding.despesasView
        storage = requireActivity().getSharedPreferences("user",Context.MODE_PRIVATE)
        viewModel = ViewModelProvider(this).get(DespesasViewModel::class.java)
        var order = -1

        val userId = storage.getInt("id",0)

        val list = viewModel.listTuition(userId)
        adapter = TuitionListAdapter(list,this)

        recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        recyclerView.adapter = adapter

        //customize UI text
        val profile = binding.root.findViewById<Button>(R.id.profile_pic)
        val customName = binding.root.findViewById<TextView>(R.id.custom_name)
        val userData = viewModel.getUserData(userId)

        profile.setText(userData.user.name.substring(0,1)
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })

        customName.setText(userData.user.name.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        })
        
        binding.textView18.setText(viewModel.countAll(userId).toString())
        binding.textView20.setText(viewModel.countFinishedTuition(userId).toString())

        //register new tuition
        binding.button5.setOnClickListener{
            val title = binding.churchNameInput2.text.toString()
            val price = binding.apelidoInput2.text.toString().toDouble()
            val type = selectedType

            val res = viewModel.createTuition(Tuition(title,price,type,0,User(id = userId)))

            //clear
            if(res == "Criado com sucesso"){
                binding.churchNameInput2.setText("")
                binding.apelidoInput2.setText("0")
            }

            Toast.makeText(requireContext(),res,Toast.LENGTH_SHORT).show()
            val list = viewModel.listTuition(userId)
            adapter.updateList(list)
        }

        //order items
        binding.imageButton4.setOnClickListener{
           if(order == -1){
               order = 1
               val list = viewModel.getTuitionInDescOrder(userId)
               adapter.updateList(list)
           }else{
               val list = viewModel.listTuition(userId)
               adapter.updateList(list)
               order = -1
           }
        }

        Helper.getGreetings(binding.root)

        //load dropdown itens
//        val list2 = arrayOf("Tipo 1","Tipo 2", "Tipo 3")
        val list2 = arrayOf("Urgente","Importante", "Normal")


        val typeAdapter = ArrayAdapter(requireContext(), androidx.transition.R.layout.support_simple_spinner_dropdown_item,list2)

        binding.nomeInput2.adapter = typeAdapter

        binding.nomeInput2.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
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
        viewModel = ViewModelProvider(this).get(DespesasViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onClick(despesa: Tuition, view: View) {
        val popupMenu = PopupMenu(requireContext(),view)
        popupMenu.inflate(R.menu.despesa_dropdown_context_menu)
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.d1 -> {
                    //set finished
                   if(despesa.state == 0){
                       val r = viewModel.updateTuition(despesa.id,Tuition("",despesa.price,"",1,User(id = despesa.user.id)))
                       val list = viewModel.listTuition(despesa.user.id)
                       adapter.updateList(list)
                       Toast.makeText(requireContext(),r,Toast.LENGTH_SHORT).show()
                   }else{
                       Toast.makeText(requireContext(),"Esta despesa ja foi marcada como concluida",Toast.LENGTH_SHORT).show()
                   }
                }

                R.id.d2 -> {
                    //delete
                    viewModel.deleteOne(despesa.id)
                    val list = viewModel.listTuition(despesa.user.id)
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