package com.example.marketmind.screens.dashboard

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.marketmind.databinding.FragmentDashboardBinding
import java.util.Locale

class DashboardFragment : Fragment(), TuitionClickListener {

    companion object {
        fun newInstance() = DashboardFragment()
    }

    private lateinit var adapter:TuitionListAdapter
    private lateinit var storage:SharedPreferences
    private lateinit var viewModel:DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        val binding:FragmentDashboardBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard,container, false)
        storage = requireActivity().getSharedPreferences("user",Context.MODE_PRIVATE)
        val userId = storage.getInt("id",0)
        val recyclerView = binding.despesasView

        val list = viewModel.listRecentTuition(userId)

        adapter = TuitionListAdapter(list,this)

        recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        recyclerView.adapter = adapter


        //==============add dynamic data to the UI===========

        val money = binding.textView10
        val customName = binding.root.findViewById<TextView>(R.id.custom_name)
        val profile = binding.root.findViewById<Button>(R.id.profile_pic)
//        val churchName = binding.root.findViewById<TextView>(R.id.textView9)
        val result = viewModel.getChurchByUser(storage.getInt("id",0))

        customName.setText(result.user.name.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        })
        profile.setText(result.user.name.substring(0,1)
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
        money.setText(result.money.toString()+"MT")
//        churchName.setText("Igreja "+result.name)
        binding.textView12.setText(viewModel.getCountTuition(userId).toString())
        binding.textView14.setText(viewModel.getCountContribution(userId).toString())

        //
        Helper.getGreetings(binding.root)

        return binding.root
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

    override fun onClick(despesa: Tuition, view:View) {
        val popupMenu = PopupMenu(requireContext(),view)
        popupMenu.inflate(R.menu.despesa_dropdown_context_menu)
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.d1 -> {
                    //set finished
                    if(despesa.state == 0){
                        val r = viewModel.updateTuition(despesa.id,Tuition("",despesa.price,"",1,
                            User(id = despesa.user.id)
                        ))
                        val list = viewModel.listRecentTuition(despesa.user.id)
                        adapter.updateList(list)
                        Toast.makeText(requireContext(),r, Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(requireContext(),"Esta despesa ja foi marcada como concluida",
                            Toast.LENGTH_SHORT).show()
                    }
                }

                R.id.d2 -> {
                    //delete
                    viewModel.deleteOne(despesa.id)
                    val list = viewModel.listRecentTuition(despesa.user.id)
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