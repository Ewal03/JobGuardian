package com.example.jobguardian.ui.main.view.detection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.jobguardian.R
import com.example.jobguardian.databinding.FragmentDetectionBinding

class DetectionFragment : Fragment() {

    private var _binding: FragmentDetectionBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val detectionViewModel =
            ViewModelProvider(this).get(DetectionViewModel::class.java)

        _binding = FragmentDetectionBinding.inflate(inflater, container, false)
        val root: View = binding.root

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

        val isLogoChecked = cbLogo.isChecked
        val isTelecommutingChecked = cbTelecommuting.isChecked

        btnSendDetect.setOnClickListener {
            val jobDescription = etDeskripsi.text.toString()

            val detectionViewModel = ViewModelProvider(this).get(DetectionViewModel::class.java)
            detectionViewModel.sendDataToApi(
                isLogoChecked.toString(),
                isTelecommutingChecked.toString(),
                jobDescription
            )
        }
    }

}