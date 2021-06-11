package edu.co.icesi.semana7kotlina

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var counterTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        counterTV = findViewById(R.id.counterTV)

        runCounterThatIsGood()
    }

    var i: Int = 0

    private fun runCounterThatFails() {
        lifecycleScope.launch(Dispatchers.IO) {
            while (true) {
                i++
                delay(1000)
                counterTV.text = "cuenta: $i"
            }
        }
    }

    private fun runCounterThatIsGood() {
        lifecycleScope.launch(Dispatchers.IO) {
            while (true) {
                i++
                delay(1000)
                withContext(Dispatchers.Main) {
                    counterTV.text = "cuenta: $i"
                    Log.e(">>>","Alfa: $i")
                }
            }
        }
    }
}