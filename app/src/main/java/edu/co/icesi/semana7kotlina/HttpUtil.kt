package edu.co.icesi.semana7kotlina

import androidx.lifecycle.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class HttpUtil : ViewModel(){

    //Las variables MutableLiveData son variables que entran dentro de los LiveData y permiten cambiar el valor que contienen
    private val responseMutable = MutableLiveData("")
    //Las variables live data es un tipo de dato wrapper que implementa observer, permite ser observador
    val responseLive: LiveData<String> get() = responseMutable

    fun getRequest(url:String) {
        viewModelScope.launch(Dispatchers.IO) {
            val url = URL(url)
            val https = url.openConnection() as HttpsURLConnection
            https.requestMethod = "GET"
            var mensaje = ""
            https.inputStream.bufferedReader().lines().forEach{
                mensaje+=it
            }
            withContext(Dispatchers.Main){
                responseMutable.value = mensaje
            }
        }
    }

}