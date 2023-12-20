package com.example.jobguardian.ui.main.view.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.jobguardian.databinding.FragmentProfileBinding
import com.example.jobguardian.ui.authenticaion.signIn.SignInActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        profileViewModel.getUserProfile("6YECw2JbtcEWJ21baS2Z")
        profileViewModel.userProfile.observe(viewLifecycleOwner, Observer { userProfile ->
            profileViewModel.userProfile.observe(viewLifecycleOwner, Observer { userProfile ->
                binding.editTextName.setText(userProfile?.userProfile?.fullName ?: "")
                binding.editTextBirthday.setText(userProfile?.userProfile?.birthDate ?: "")
                binding.editTextContactPerson.setText(userProfile?.userProfile?.contact ?: "")
            })

        })
        profileViewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        })

        auth = Firebase.auth

        val firebaseUser = auth.currentUser
        if (firebaseUser == null) {
            // Not signed in, launch the Login activity
            startSignInActivity()
        }

        binding.btnLogout.setOnClickListener {
            signOut()
        }

        return root
    }

    private fun startSignInActivity() {
        val intent = Intent(requireContext(), SignInActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun signOut() {
        auth.signOut()
        val intent = Intent(requireContext(), SignInActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}