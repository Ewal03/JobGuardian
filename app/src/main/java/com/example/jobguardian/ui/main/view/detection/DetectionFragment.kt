package com.example.jobguardian.ui.main.view.detection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.jobguardian.R
import com.example.jobguardian.databinding.FragmentDetectionBinding
import com.example.jobguardian.factory.ViewModelFactory
import com.example.jobguardian.ui.main.SharedViewModel
import com.example.jobguardian.ui.main.view.home.HomeViewModel

class DetectionFragment : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel
    private var _binding: FragmentDetectionBinding? = null
    private val detectionViewModel by viewModels<DetectionViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        _binding = FragmentDetectionBinding.inflate(inflater, container, false)
        val root: View = binding.root
        detectionViewModel.detectionSuccess.observe(viewLifecycleOwner, Observer { success ->
            if (success) {
                detectionViewModel.jobOffer.observe(viewLifecycleOwner,Observer{ real->
                    if(real){
                        showAlert(true, "Congratulation", "Genuine Job Offer", "OK")
                    }else{
                        showAlert(true, "Becareful", "False Job Offer", "OK")
                    }
                })
            showLoading(false)
            }
        })
        sharedViewModel.userId.observe(viewLifecycleOwner) {
            detectionViewModel.getUserProfile(it)
            detectionViewModel.userProfile.observe(viewLifecycleOwner, Observer { userProfile ->
                detectionViewModel.userProfile.observe(viewLifecycleOwner, Observer { userProfile ->
                   val user=userProfile?.userProfile?.fullName
                    if(user.equals(null)){
                        binding.userHello.text="Hello, user"
                    }else{
                        binding.userHello.text="Hello, $user"
                    }
                    binding.userHello.text="Hello, $user"
                    Glide.with(binding.root.context)
                        .load(userProfile?.userProfile?.profilePicture)
                        .placeholder(R.drawable.pp)
                        .error(R.drawable.pp)
                        .into(binding.imageView2)
                })

            })
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cbLogo: CheckBox = view.findViewById(R.id.cb_logo)
        val cbTelecommuting: CheckBox = view.findViewById(R.id.cb_telecommuting)
        val etDeskripsi: EditText = view.findViewById(R.id.et_deskripsi)
        val btnSendDetect: Button = view.findViewById(R.id.btn_send_detect)
        btnSendDetect.setOnClickListener {
            val isLogoChecked = cbLogo.isChecked
            val isTelecommutingChecked = cbTelecommuting.isChecked
            val jobDescription = etDeskripsi.text.toString()
            showLoading(true)

            val detectionViewModel = ViewModelProvider(this).get(DetectionViewModel::class.java)
            detectionViewModel.sendDataToApi(
                isLogoChecked,
                isTelecommutingChecked,
                jobDescription,
                requireContext()
            )
        }
    }
    private fun showAlert(condition: Boolean, tittle: String, message: String, buttonText: String) {
        context?.let {
            AlertDialog.Builder(it).apply {
                setTitle(tittle)
                setMessage(message)
                setPositiveButton(buttonText) { _, _ ->
                binding.etDeskripsi.setText("")
                }
            }.create().show()
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

