package com.sistemaasistenciaycomunicados.fragment.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sistemaasistenciaycomunicados.databinding.FragmentProfileBinding

class FragmentProfile : Fragment() {
    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root : View = binding.root

        val tvProfile: TextView = binding.profileTv
        profileViewModel.text.observe(viewLifecycleOwner) {
            tvProfile.text = it
        }

        return root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}