package com.example.summerlabproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import com.example.summerlabproject.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private var TimeStarded=false

    private val myNameA: TeamAName = TeamAName( "Team A")
    private val myNameB: TeamBName = TeamBName( "Team B")
    private var teamScA: ScoreTeamA = ScoreTeamA("Team Score",0)
    private var teamScB: ScoreTeamB = ScoreTeamB("Team Score",0)
    private var resumeFromMillis:Long = 0
    private var isPaused = false
    private var isCancelled = false


    private fun timer(millisInFuture: Long, countDownInterval: Long): CountDownTimer {
            return object: CountDownTimer(millisInFuture,countDownInterval){
                override fun onTick(millisUntilFinished: Long) {
                    val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                    if (isPaused) {
                        resumeFromMillis = millisUntilFinished
                        cancel()
                    } else if (isCancelled) {
                        cancel()
                    } else {
                        binding.textView.setText("" + minutes + ":" + millisUntilFinished / 1000 % 60)
                    }
                }

            override fun onFinish() {
                binding.textView.setText("Game Over!")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this , R.layout.activity_main)
        val millisInFuture:Long = 600000
        val countDownInterval:Long = 1000

        binding.teamNameA=myNameA
        binding.teamNameB=myNameB

        binding.scoreTa=teamScA
        binding.scoreTb=teamScB

        binding.buttonA.setOnClickListener{
            addTeamNameA(it)
        }
        binding.buttonB.setOnClickListener{
            addTeamNameB(it)
        }
        binding.threePointsTeamA.setOnClickListener{
            counterTeamA(3)
        }
        binding.threePointsTeamB.setOnClickListener{
            counterTeamB(3)
        }
        binding.twoPointsTeamA.setOnClickListener{
            counterTeamA(2)
        }
        binding.twoPointsTeamB.setOnClickListener{
            counterTeamB(2)
        }
        binding.freeThrowTeamA.setOnClickListener{
            counterTeamA(1)
        }
        binding.freeThrowsTeamB.setOnClickListener{
            counterTeamB(1)
        }
        binding.resetButton.setOnClickListener{
            reset()
            isCancelled = true
            isPaused = false
            it.isEnabled = true
            binding.timerStartStop.isEnabled = true
            binding.pauseButton.isEnabled = false

        }
        binding.timerStartStop.setOnClickListener{
            timer(millisInFuture,countDownInterval).start()
            isCancelled = false
            isPaused = false
            it.isEnabled = false
            binding.pauseButton.isEnabled = true
            binding.resumeButton.isEnabled= false

        }
        binding.pauseButton.setOnClickListener{
            isPaused = true
            isCancelled = false
            binding.timerStartStop.isEnabled = false
            binding.resumeButton.isEnabled = true

        }
        binding.resumeButton.setOnClickListener{
            timer(resumeFromMillis,countDownInterval).start()
            isPaused = false
            isCancelled = false
            it.isEnabled=false
            binding.pauseButton.isEnabled = true
            binding.timerStartStop.isEnabled = false
        }

    }

    private fun addTeamNameA(view : View){

        binding.apply {
            myNameA.nickname = team1.text.toString()
            // binding.nicknameText.text = binding.nicknameEdit.text
            invalidateAll()
            team1.visibility = View.GONE
            buttonA.visibility = View.GONE
            nicknameTeamA.visibility = View.VISIBLE

        }
            // Hide the keyboard.
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)

        }
        private fun addTeamNameB(view : View){

            binding.apply {
                myNameB.nickname = team2.text.toString()
                invalidateAll()
                team2.visibility = View.GONE
                buttonB.visibility = View.GONE
                nicknameTeamB.visibility = View.VISIBLE

            }
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
        private fun counterTeamA(int: Int){
            binding.apply {
                scoreTa!!.score+=int
                scoreTA.text=scoreTa?.score.toString()

            }
        }
        private fun counterTeamB(int: Int){
        binding.apply {
            teamScB.score+=int
            scoreTB.text=scoreTb?.score.toString()

            }
        }
        private fun reset() {
            binding.apply {
                teamScB.score=0
                scoreTa!!.score=0
                scoreTB.text=scoreTb?.score.toString()
                scoreTA.text=scoreTa?.score.toString()
                binding.textView.text="0:0"


            }
        }




}