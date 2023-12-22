package com.example.jobguardian.ui.authenticaion.signUp

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.jobguardian.R
import com.example.jobguardian.ui.authenticaion.WelcomeActivity
import com.example.jobguardian.ui.main.view.MainActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.firestore

class SignUpActivity : AppCompatActivity() {
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var btnRegister: Button

    private lateinit var auth: FirebaseAuth
private var db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        nameEditText = findViewById(R.id.usernameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        btnRegister = findViewById(R.id.button_regis)

        auth = FirebaseAuth.getInstance()

        supportActionBar?.hide()

        btnRegister.setOnClickListener {
            if (nameEditText.text.isNotEmpty() && emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {
                processRegister()
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun processRegister() {
        val name = nameEditText.text.toString()
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userUpdateProfile = userProfileChangeRequest {
                        displayName = name
                        val name=nameEditText.text.toString().trim()
                        val email=emailEditText.text.toString().trim()
                        val pass=passwordEditText.text.toString().trim()
                        val userMap= hashMapOf(
                            "username" to name,
                            "email" to email,
                            "password" to pass
                        )
                        val userId=FirebaseAuth.getInstance().currentUser!!.uid
                        db.collection("usersCollection").document(userId).set(userMap)
                            .addOnSuccessListener {

                            }
                    }
                    val user = task.result?.user
                    user?.updateProfile(userUpdateProfile)
                        ?.addOnCompleteListener {
                            showAlert(true,"Good","Your Account has been Registered","Continue")
                        }
                        ?.addOnFailureListener { error2 ->
                            Toast.makeText(this, error2.localizedMessage, Toast.LENGTH_SHORT).show()
                        }

                } else {
                    Toast.makeText(this, "Register Failed", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { error ->
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }
    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    companion object {
        private const val TAG = "SignUp"
    }

    private fun showAlert(condition: Boolean, tittle: String, message: String, buttonText: String) {
        AlertDialog.Builder(this).apply {
            setTitle(tittle)
            setMessage(message)
            setPositiveButton(buttonText) { _, _ ->
                Intent(this@SignUpActivity, WelcomeActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
            create()
            show()
        }
    }
}
