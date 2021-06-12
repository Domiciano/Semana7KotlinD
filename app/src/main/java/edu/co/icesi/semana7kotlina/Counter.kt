package edu.co.icesi.semana7kotlina

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*


class Counter : ViewModel() {

    //Las variables MutableLiveData son variables que entran dentro de los LiveData y permiten cambiar el valor que contienen
    private val counterMutable = MutableLiveData(0)

    //Las variables live data es un tipo de dato wrapper que implementa observer, permite ser observador
    val counterLive: LiveData<Int> get() = counterMutable


    fun start() {
        //Iniciamos Worker Thread
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                delay(1000)
                //Cambiamos el dato, esto lo va a observar el observer LiveData
                withContext(Dispatchers.Main) {
                    counterMutable.value = counterMutable.value!!+1
                }
            }


        }
    }

}


