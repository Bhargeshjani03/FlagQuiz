package com.example.flagquiz.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.flagquiz.R
import com.example.flagquiz.database.DatabaseCopyHelper
import com.example.flagquiz.database.FlagsDao
import com.example.flagquiz.databinding.FragmentQuizBinding
import com.example.flagquiz.model.FlagsModel
// Second Fragment to be displayed
class FragmentQuiz : Fragment() {
    private lateinit var fragmentQuizBinding: FragmentQuizBinding
    private var flagsList=ArrayList<FlagsModel>()
    private var correctNumber = 0
    private var wrongNumber = 0
    var emptyNumber = 0
    private var questionNumber=0
    private val dao=FlagsDao()
    var optionControl =false
private lateinit var correctFlag:FlagsModel
private var wrongFlags= ArrayList<FlagsModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentQuizBinding=FragmentQuizBinding.inflate(inflater,container,false)
        //Calling the object of FlagsDao Class
      flagsList=  dao.getRandomRecords(DatabaseCopyHelper(requireActivity()))
        for (flag in flagsList)
        {
            Log.d("flags",flag.id.toString())
            Log.d("flags",flag.countryName)
            Log.d("flags",flag.flagName)
            Log.d("flags","*********")
        }
        showData()
        fragmentQuizBinding.buttonA.setOnClickListener {
            answerControl(fragmentQuizBinding.buttonA)


        }
        fragmentQuizBinding.buttonB.setOnClickListener {
            answerControl(fragmentQuizBinding.buttonB)

        }
        fragmentQuizBinding.buttonC.setOnClickListener {
            answerControl(fragmentQuizBinding.buttonC)
        }
        fragmentQuizBinding.buttonD.setOnClickListener {
            answerControl(fragmentQuizBinding.buttonD)

        }
        fragmentQuizBinding.buttonNext.setOnClickListener {
        questionNumber++
             if(questionNumber>9)
             {
                 if(!optionControl)
                 {
                     emptyNumber++
                 }
                 val direction= FragmentQuizDirections.actionFragmentQuizToFragmentResult().apply {
                     setCorrect(correctNumber)
                     wrong= wrongNumber
                     empty=emptyNumber
                 }
                 this.findNavController().navigate(direction)
                 this.findNavController().popBackStack(R.id.fragmentResult,false)
                 Toast.makeText(requireActivity(),"The Quiz is finished",Toast.LENGTH_LONG).show()
             }
            else
             {
                 showData()
                 if(!optionControl)
                 {
                     emptyNumber++
                     fragmentQuizBinding.textViewEmpty.text=emptyNumber.toString()
                 }
                 else
                 {
                     setButtonToInitialProperties()
                 }
             }

        optionControl=false

        }
        return fragmentQuizBinding.root
    }
    private fun answerControl(button:Button)
    {
        val clickedOption= button.text.toString()
         val correctAnswer=correctFlag.countryName

            if(clickedOption == correctAnswer)
            {
                correctNumber++
                fragmentQuizBinding.textViewCorrect.text=correctNumber.toString()
                button.setBackgroundColor(Color.GREEN)
            }
        else
            {
                wrongNumber++
                fragmentQuizBinding.textViewWrong.text=wrongNumber.toString()
                button.setBackgroundColor(Color.RED)
                button.setTextColor(Color.WHITE)
                when(correctAnswer)
                {
                    fragmentQuizBinding.buttonA.text-> fragmentQuizBinding.buttonA.setBackgroundColor(Color.GREEN)
                    fragmentQuizBinding.buttonB.text-> fragmentQuizBinding.buttonB.setBackgroundColor(Color.GREEN)
                    fragmentQuizBinding.buttonC.text-> fragmentQuizBinding.buttonC.setBackgroundColor(Color.GREEN)
                    fragmentQuizBinding.buttonD.text-> fragmentQuizBinding.buttonD.setBackgroundColor(Color.GREEN)

                }
            }
        fragmentQuizBinding.buttonA.isClickable=false
        fragmentQuizBinding.buttonB.isClickable=false
        fragmentQuizBinding.buttonC.isClickable=false
        fragmentQuizBinding.buttonD.isClickable=false
        optionControl=true

    }
    private fun setButtonToInitialProperties()
    {
        fragmentQuizBinding.buttonA.apply {
            setBackgroundColor(Color.WHITE)
          setTextColor(resources.getColor(R.color.pink,requireActivity().theme))
            isClickable=true
        }
        fragmentQuizBinding.buttonB.apply {
            setBackgroundColor(Color.WHITE)
            setTextColor(resources.getColor(R.color.pink,requireActivity().theme))
            isClickable=true
        }
        fragmentQuizBinding.buttonC.apply {
            setBackgroundColor(Color.WHITE)
            setTextColor(resources.getColor(R.color.pink,requireActivity().theme))
            isClickable=true
        }
        fragmentQuizBinding.buttonD.apply {
            setBackgroundColor(Color.WHITE)
            setTextColor(resources.getColor(R.color.pink,requireActivity().theme))
            isClickable=true
        }
    }
    private fun showData()
    {
        fragmentQuizBinding.textViewQuestion.text=resources.getString(R.string.question_number).plus(questionNumber+1)
        correctFlag=flagsList[questionNumber]

        fragmentQuizBinding.imageViewFlag.setImageResource(resources.getIdentifier(correctFlag.flagName,"drawable",requireActivity().packageName))
        wrongFlags=dao.getRandomThreeRecords(DatabaseCopyHelper(requireActivity()),correctFlag.id)
        val mixOptions=HashSet<FlagsModel>()
        mixOptions.clear()
        mixOptions.add(correctFlag)
        mixOptions.add(wrongFlags[0])
        mixOptions.add(wrongFlags[1])
        mixOptions.add(wrongFlags[2])
        val options = ArrayList<FlagsModel>()
        options.clear()
        for(eachFlag in mixOptions)
        {
            options.add(eachFlag)
        }
        fragmentQuizBinding.buttonA.text=options[0].countryName
        fragmentQuizBinding.buttonB.text=options[1].countryName
        fragmentQuizBinding.buttonC.text=options[2].countryName
        fragmentQuizBinding.buttonD.text=options[3].countryName
    }
}