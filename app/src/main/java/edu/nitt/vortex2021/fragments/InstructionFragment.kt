package edu.nitt.vortex2021.fragments

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import edu.nitt.vortex2021.R
import edu.nitt.vortex2021.databinding.FragmentEventsBinding
import edu.nitt.vortex2021.databinding.FragmentInstructionBinding
import edu.nitt.vortex2021.helpers.initGradientBackgroundAnimation
import edu.nitt.vortex2021.helpers.viewLifecycle


class InstructionFragment : Fragment() {

    private var binding by viewLifecycle<FragmentInstructionBinding>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInstructionBinding.inflate(inflater, container, false)
        initGradientBackgroundAnimation(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textView.movementMethod = ScrollingMovementMethod()
        binding.continueButton.setOnClickListener {
            findNavController().navigate(InstructionFragmentDirections.actionInstructionFragmentToLinkedFragment())
        }
    }



}