package edu.nitt.vortex.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ToDo: If logged in then go to HomeFragment else LoginFragment
        // This Fragment acts a bridge to home or login
        // We land on this fragment from the Splash Activity

        // Dummy buttons
        binding.buttonLogout.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionFragmentMainToFragmentLogin())
        }

        binding.buttonHome.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionFragmentMainToFragmentHome())
        }
    }
}