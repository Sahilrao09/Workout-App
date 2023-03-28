package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.example.a7minutesworkout.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {
    private var binding:ActivityExerciseBinding ?=null

    private var restTimer:CountDownTimer ?= null
    private var restProgress = 0

    private var exerciseTimer:CountDownTimer ?= null
    private var exerciseProgress = 0

    private var exerciseTimerDuration: Long =30
    private var exerciseList: ArrayList<ExerciseModel>?=null
    private var currentExercisePosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)

        if (supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarExercise?.setNavigationOnClickListener{
            onBackPressed()
        }

        exerciseList = Constants.defaultExerciseList()

        setupRestView()
    }

    private fun setupRestView(){
        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }

        setRestProgressBar()
    }

    private fun setRestProgressBar(){
        binding?.progressBar?.progress = restProgress

        restTimer = object : CountDownTimer(10000,1000){
            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBar?.progress = 10-restProgress
                binding?.tvTimer?.text = (10-restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                exerciseRestView()
            }
        }.start()
    }

    private fun exerciseRestView(){
        binding?.flProgressBar?.visibility=View.INVISIBLE
        binding?.tvTitle?.text="Exercise name"
        binding?.flExerciseView?.visibility=View.VISIBLE

        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }

        exerciseProgressBar()
    }

    private fun exerciseProgressBar(){
        binding?.progressBar?.progress = exerciseProgress

        restTimer = object : CountDownTimer(30000,1000){
            override fun onTick(p0: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = 30-exerciseProgress
                binding?.tvTimerExercise?.text = (30-exerciseProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(
                    this@ExerciseActivity,
                    "This is 30 seconds completed so now we will add all the exercises.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.start()
    }

    override fun onDestroy() {
        if(restTimer!=null){
            restTimer?.cancel()
            restProgress=0
        }

        super.onDestroy()
        binding=null
    }
}