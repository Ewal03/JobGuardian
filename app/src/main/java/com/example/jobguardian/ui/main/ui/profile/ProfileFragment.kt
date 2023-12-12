package com.example.jobguardian.ui.main.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.jobguardian.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
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
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}