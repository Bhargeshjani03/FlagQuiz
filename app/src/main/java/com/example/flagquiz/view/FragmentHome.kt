package com.example.flagquiz.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.flagquiz.database.DatabaseCopyHelper
import com.example.flagquiz.databinding.FragmentHomeBinding
// First Fragment to be displayed in Main activity
class FragmentHome : Fragment() {
    private lateinit var fragmentHomeBinding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentHomeBinding=FragmentHomeBinding.inflate(inflater,container,false)
        createAndOpenDatabase()
        fragmentHomeBinding.buttonStart.setOnClickListener {
            val direction= FragmentHomeDirections.actionFragmentHomeToFragmentQuiz()
            this.findNavController().navigate(direction)
        }
        return fragmentHomeBinding.root
    }
    // Opening the SQllite Database by clicking the start button
    private fun createAndOpenDatabase(){
        try {
            val helper=DatabaseCopyHelper(requireActivity())
            helper.createDataBase()
            helper.openDataBase()
        }catch (e:Exception){
        e.printStackTrace()
        }
    }
}