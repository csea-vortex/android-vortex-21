package edu.nitt.vortex2021.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import edu.nitt.vortex2021.adapters.InstructionAdapter
import edu.nitt.vortex2021.databinding.FragmentInstructionBinding
import edu.nitt.vortex2021.helpers.initGradientBackgroundAnimation
import edu.nitt.vortex2021.helpers.viewLifecycle


class InstructionFragment : Fragment() {

    private var binding by viewLifecycle<FragmentInstructionBinding>()

    private val args: InstructionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInstructionBinding.inflate(inflater, container, false)
        initGradientBackgroundAnimation(binding.root)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        binding.continueButton.setOnClickListener {
            findNavController().navigate(InstructionFragmentDirections.actionFragmentInstructionToFragmentLinked())
        }
    }

    private fun initViewPager() {
        val headings = listOf("Description", "Rules", "Format", "Resources")

        val data = args.event.eventData
        val contents = listOf(
            data.description,
            data.rules,
            data.format,
            data.resources
        )

        binding.instuctionViewPager.apply {
            adapter = InstructionAdapter(contents)
            isUserInputEnabled = false
        }

        TabLayoutMediator(
            binding.instructionTabLayout,
            binding.instuctionViewPager
        ) { tab, position ->
            tab.text = headings[position]
        }.attach()
    }

}