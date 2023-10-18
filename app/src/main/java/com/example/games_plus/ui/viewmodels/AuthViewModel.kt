package com.example.games_plus.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.games_plus.data.models.profile.Profile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore


class AuthViewModel: ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseStore = FirebaseFirestore.getInstance()


    private var _currentUser = MutableLiveData<FirebaseUser?>(firebaseAuth.currentUser)
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser

    fun signUp(email: String, password: String) {

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { authResult ->

            if (authResult.isSuccessful) {

                login(email, password)
                newProfile()

            } else {

                Log.e("REGISTER", "${authResult.exception}")
            }

        }
    }

    fun login(email: String, password: String) {


        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { loginResult ->

            if (loginResult.isSuccessful) {

                _currentUser.value = firebaseAuth.currentUser

            } else {

                Log.e("LOGIN", "${loginResult.exception}")
            }

        }


    }

    fun logout() {

        firebaseAuth.signOut()
        _currentUser.value = firebaseAuth.currentUser

    }


    private fun newProfile() {

        firebaseStore.collection("profile").document(firebaseAuth.currentUser?.uid!!).set(Profile())

    }


}