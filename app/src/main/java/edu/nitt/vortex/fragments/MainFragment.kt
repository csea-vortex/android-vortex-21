package edu.nitt.vortex.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.nitt.vortex.databinding.FragmentMainBinding
import edu.nitt.vortex.helpers.viewLifecycle

class MainFragment : Fragment() {

    private var binding by viewLifecycle<FragmentMainBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

}