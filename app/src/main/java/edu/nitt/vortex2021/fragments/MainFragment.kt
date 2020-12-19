package edu.nitt.vortex2021.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.nitt.vortex2021.databinding.FragmentMainBinding
import edu.nitt.vortex2021.helpers.UserTokenStore
import edu.nitt.vortex2021.helpers.viewLifecycle


/* This Fragment acts a bridge to home or login
We land on this fragment from the Splash Activity */
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
        val userTokenStore = UserTokenStore(requireContext())
        if (userTokenStore.token.isNotEmpty()) {
            findNavController().navigate(MainFragmentDirections.actionFragmentMainToFragmentHome())
        } else {
            findNavController().navigate(MainFragmentDirections.actionFragmentMainToFragmentLogin())
        }
    }
}