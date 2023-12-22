package com.example.jobguardian.ui.main.view.profile

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.jobguardian.R
import com.example.jobguardian.data.pref.UpdateBiodataRequest
import com.example.jobguardian.data.response.ProfilePictureResponse
import com.example.jobguardian.data.utils.reduceFileImage
import com.example.jobguardian.data.utils.uriToFile
import com.example.jobguardian.databinding.FragmentProfileBinding
import com.example.jobguardian.ui.authenticaion.signIn.SignInActivity
import com.example.jobguardian.ui.main.SharedViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProfileFragment : Fragment() {
    private lateinit var sharedViewModel: SharedViewModel
    private var _binding: FragmentProfileBinding? = null
    private var currentImageUri: Uri? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    private lateinit var profileViewModel: ProfileViewModel
    private var isEditMode = false
    var idUser : String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        sharedViewModel.userId.observe(viewLifecycleOwner) {
            val id = it
            idUser=id
            profileViewModel.getUserProfile("$idUser")
        }

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        profileViewModel.userProfile.observe(viewLifecycleOwner, Observer { userProfile ->
            profileViewModel.userProfile.observe(viewLifecycleOwner, Observer { userProfile ->
                Glide.with(binding.root.context)
                    .load(userProfile?.userProfile?.profilePicture)
                    .placeholder(R.drawable.pp)
                    .error(R.drawable.pp)
                    .into(binding.profileImage)
                binding.editTextName.setText(userProfile?.userProfile?.fullName ?: "")
                binding.editTextBirthday.setText(userProfile?.userProfile?.birthDate ?: "")
                binding.editTextContactPerson.setText(userProfile?.userProfile?.contact ?: "")
            })

        })
        profileViewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        })
        profileViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) {
               showLoading(true)
            } else {
           showLoading(false)
            }
        })

        auth = Firebase.auth

        val firebaseUser = auth.currentUser
        if (firebaseUser == null) {
            startSignInActivity()
        }
        binding.btnLogout.setOnClickListener {
            signOut()
        }

        binding.editButton.setOnClickListener {
            lifecycleScope.launch {
                if (isEditMode) {
                    val updateBiodataRequest = UpdateBiodataRequest(
                        fullName = binding.editTextName.text.toString(),
                        contact = binding.editTextContactPerson.text.toString(),
                        birthDate = binding.editTextBirthday.text.toString()
                    )


                    idUser?.let { it1 -> profileViewModel.updateBiodataUser(it1, updateBiodataRequest) }
                }
                toggleEditMode()
            }
        }

        binding.editTextName.isEnabled = false
        binding.editTextBirthday.isEnabled = false
        binding.editTextContactPerson.isEnabled = false

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

    private fun toggleEditMode() {
        isEditMode = !isEditMode
        updateUIEditMode()
    }

    private fun updateUIEditMode() {
        val buttonText = if (isEditMode) "Save" else "Edit"
        binding.editButton.text = buttonText

        binding.editTextName.isEnabled = isEditMode
        binding.editTextBirthday.isEnabled = isEditMode
        binding.editTextContactPerson.isEnabled = isEditMode

        binding.editTextName.isFocusable = isEditMode
        binding.editTextBirthday.isFocusable = isEditMode
        binding.editTextContactPerson.isFocusable = isEditMode

        binding.editTextName.isFocusableInTouchMode = isEditMode
        binding.editTextBirthday.isFocusableInTouchMode = isEditMode
        binding.editTextContactPerson.isFocusableInTouchMode = isEditMode
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.profileImage.setImageURI(it)
        }
    }
    private fun showLoading(isLoading:Boolean){
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }





}
