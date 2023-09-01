package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    companion object {
        const val score_key = "score_key"
        const val diceValue_key = "diceValue_key"
        const val activateRollButton_key = "activateRollButton_key"
    }

    var score: Int = 0
    var diceValue: Int = 0
    var activateRollButton: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(resources.configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.constraintlayout)
            Log.d("This app is running on ", "Potrait mode")
        }
        else {
            setContentView(R.layout.linearlayout)
            Log.d("This app is running on ", "Horizontal mode")
        }

        val scoreView: TextView = findViewById(R.id.score)
        val rollButton: Button = findViewById(R.id.rollbutton)
        val addButton: Button = findViewById(R.id.addbutton)
        val substractButton: Button = findViewById(R.id.substractbutton)
        val resetButton: Button = findViewById(R.id.resetbutton)
        val diceImage: ImageView = findViewById(R.id.diceImage)


        resetButton.setOnClickListener() {

            score = 0
            diceValue = 0
            diceImage.setImageResource(R.drawable.dice_1)
            changeScore(scoreView, score);
        }


        rollButton.setOnClickListener() {
            if (activateRollButton== true) {
                diceValue = rollDice()
                when (diceValue) {
                    1 -> diceImage.setImageResource(R.drawable.dice_1)
                    2 -> diceImage.setImageResource(R.drawable.dice_2)
                    3 -> diceImage.setImageResource(R.drawable.dice_3)
                    4 -> diceImage.setImageResource(R.drawable.dice_4)
                    5 -> diceImage.setImageResource(R.drawable.dice_5)
                    else -> diceImage.setImageResource(R.drawable.dice_6)
                }
                activateRollButton = false
            }
        }

        addButton.setOnClickListener() {
            score += diceValue
            changeScore(scoreView, score)
            diceValue = 0
            activateRollButton = true
        }

        substractButton.setOnClickListener(){
            score = maxOf(0, score-diceValue)
            changeScore(scoreView,score)
            diceValue=0
            activateRollButton=true
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(score_key,score)
        outState.putInt(diceValue_key,diceValue)
        outState.putBoolean(activateRollButton_key,activateRollButton)
        Log.i("OnSave" , score.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        score = savedInstanceState.getInt(score_key)
        changeScore(findViewById(R.id.score),score)
        diceValue = savedInstanceState.getInt(diceValue_key)
        activateRollButton = savedInstanceState.getBoolean(activateRollButton_key)
        Log.i("OnRestore", score.toString())
    }

    fun changeScore(scoreView: TextView, value: Int) {
        if (value < 20) {
            scoreView.setTextColor(ContextCompat.getColor(this, R.color.black))
        } else if (value > 20) {
            scoreView.setTextColor(ContextCompat.getColor(this, R.color.red))
        } else {
            scoreView.setTextColor(ContextCompat.getColor(this, R.color.green))
            val message = "Congratulations! You Won!"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
        scoreView.text = value.toString()

    }

    fun rollDice(): Int {
        return (1..6).random()
    }
    S
}



