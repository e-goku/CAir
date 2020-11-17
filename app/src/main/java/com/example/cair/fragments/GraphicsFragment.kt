package com.example.cair.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cair.databinding.FragmentGraphicsBinding

class GraphicsFragment : Fragment() {
    private var _binding: FragmentGraphicsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGraphicsBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.textView.text = "hihihihihihiihihihihihihi"
        return view
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}