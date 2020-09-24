package edu.nitt.vortex.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.nitt.vortex.databinding.FragmentEventsBinding
import edu.nitt.vortex.databinding.FragmentSocialMediaBinding
import edu.nitt.vortex.helpers.viewLifecycle


class SocialMediaFragment : Fragment() {

    private var binding by viewLifecycle<FragmentSocialMediaBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSocialMediaBinding.inflate(inflater, container, false)
        return binding.root
    }
}