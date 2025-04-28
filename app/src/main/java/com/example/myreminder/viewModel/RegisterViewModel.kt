package com.example.myreminder.viewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class RegisterViewModel: ViewModel() {
    private val _loaderState = MutableLiveData<Boolean>()
    val loaderState: LiveData<Boolean>
        get() = _loaderState

    private val _validRegister = MutableLiveData<Boolean>()
    val validRegister: LiveData<Boolean>
        get() = _validRegister

    private val firebase = FirebaseAuth.getInstance()

    fun requestSignUp(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            _loaderState.value = true
            viewModelScope.launch {
                val result = firebase.createUserWithEmailAndPassword(email, password).await()
                _loaderState.value = false
                result.user?.let {
                    Log.i("Firebase", "Se creó al usuario con éxito.")
                    _validRegister.value = true
                } ?: run {
                    Log.e("Firebase", "Ocurrio un problema al crear al usuario.")
                }
            }
        } else {
            _validRegister.value = false
        }

    }
}
