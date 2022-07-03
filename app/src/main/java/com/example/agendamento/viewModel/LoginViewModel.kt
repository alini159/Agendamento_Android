package com.example.agendamento.viewModel

import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.agendamento.model.UserResponse
import com.example.agendamento.model.User
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {

    private lateinit var auth: FirebaseAuth
    private val liveUser = MutableLiveData<UserResponse<User>>()
    var user: LiveData<UserResponse<User>> = liveUser
    private lateinit var currentUser: User

    fun authUser(context: Activity, login: String, password: String) {
        auth = FirebaseAuth.getInstance()

        if (!login.isNullOrBlank() && !password.isNullOrBlank()) {
            auth.signInWithEmailAndPassword(login, password)
                .addOnCompleteListener(context) { task ->
                    if (task.isSuccessful) {
                        currentUser = createUser(task.result.user?.displayName, login, password)
                        liveUser.value = UserResponse.Success(currentUser)
                        Toast.makeText(context,"Usuario autenticado", Toast.LENGTH_LONG).show()
                    } else {
                        liveUser.value = UserResponse.Error("Usuario não autenticado")
                    }
                }
        }
    }

    private fun createUser(
        name: String?,
        login: String,
        password: String
    ) = User(
        name = name,
        email = login,
        cpf = password,
        nextConsult = null
    )

    fun defineCurrentUser(current: User){
        currentUser = User(
            name = current.name,
            cpf = current.cpf,
            email = current.email,
            nextConsult = current.nextConsult,
            cardInformation = current.cardInformation
        )
    }

    fun getCurretUser(): User {
        return currentUser
    }
}

