package com.example.jobguardian.ui.authenticaion.signIn

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.jobguardian.R
import com.example.jobguardian.data.pref.UserModel
import com.example.jobguardian.databinding.ActivitySignInBinding
import com.example.jobguardian.factory.ViewModelFactory
import com.example.jobguardian.ui.main.SharedViewModel
import com.example.jobguardian.ui.main.view.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth

class SignInActivity : AppCompatActivity() {
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var binding: ActivitySignInBinding
    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var btnSignIn: Button
    private val viewModel by viewModels<SignInViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)


        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        btnSignIn = findViewById(R.id.button_login)

        btnSignIn.setOnClickListener {
            if(emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()){
                processLogin()
            }else{
                Toast.makeText(this,"Eror" , Toast.LENGTH_SHORT).show()
            }
        }

        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = Firebase.auth
        binding.btnGoogle.setOnClickListener {
            signIn()
        }

        supportActionBar?.hide()
    }

    private fun processLogin() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        auth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                val user = auth.currentUser
                updateUI(user)
            }
            .addOnFailureListener {error ->
                Toast.makeText(this, error.localizedMessage , Toast.LENGTH_SHORT).show()
            }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)    }

    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            val email=currentUser.email.toString()
            val userId = currentUser.uid
            Toast.makeText(this, email , Toast.LENGTH_SHORT).show()
            Log.d(TAG, "User is signed in: ${currentUser.displayName}")
            viewModel.saveSession(
                UserModel(email, currentUser.uid, true)
            )
            startActivity(Intent(this@SignInActivity, MainActivity::class.java).apply {
                putExtra("userId", userId)
            })
            finish()
        }
    }


    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    companion object {
        private const val TAG = "SignInActivity"
    }
}
