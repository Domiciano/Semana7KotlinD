package edu.co.icesi.semana7kotlina

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import java.net.URLConnection
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {

    private lateinit var counter: Counter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        counter = ViewModelProvider(this).get(Counter::class.java)
        counter.counterLive.observe(this, Observer {
            counterTV.setText("$it")
        })

        counter.start()


        actionBtn.setOnClickListener {
            val url = urlET.text.toString()
            getRequestThatWorks(url)
        }

    }


    //Aquí violamos la regla porque estamos usando Networking en el Main Thread
    private fun getRequestThatFails(url: String) {
        val url = URL(url)
        val https = url.openConnection() as HttpsURLConnection
        https.requestMethod = "GET"
        var mensaje = ""
        https.inputStream.bufferedReader().lines().forEach{
            mensaje+=it
        }
        Log.e(">>>",mensaje)
    }

    //Aquí funciona porque se está invocando en un hilo worker
    private fun getRequestThatWorks(url: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val url = URL(url)
            val https = url.openConnection() as HttpsURLConnection
            https.requestMethod = "GET"
            var mensaje = ""
            https.inputStream.bufferedReader().lines().forEach{
                mensaje+=it
            }
            withContext(Dispatchers.Main){
                outputTV.text = mensaje
            }
        }

    }

}