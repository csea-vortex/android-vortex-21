package edu.nitt.vortex2021.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import edu.nitt.vortex2021.BaseApplication
import edu.nitt.vortex2021.adapters.InstructionAdapter
import edu.nitt.vortex2021.databinding.FragmentInstructionBinding
import edu.nitt.vortex2021.helpers.Resource
import edu.nitt.vortex2021.helpers.initGradientBackgroundAnimation
import edu.nitt.vortex2021.helpers.viewLifecycle
import edu.nitt.vortex2021.model.EventData
import edu.nitt.vortex2021.model.EventListResponse
import edu.nitt.vortex2021.viewmodel.EventViewModel


class InstructionFragment : Fragment() {

    private var binding by viewLifecycle<FragmentInstructionBinding>()
    private lateinit var viewModel: EventViewModel

    val args:InstructionFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInstructionBinding.inflate(inflater, container, false)
        initViewModel()
        initGradientBackgroundAnimation(binding.root)
        return binding.root
    }

    private fun initViewModel() {
        val factory = (requireActivity().application as BaseApplication)
            .applicationComponent
            .getViewModelProviderFactory()

        viewModel = ViewModelProvider(this, factory).get(EventViewModel::class.java)
        observeLiveData()
        viewModel.fetchEventList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.continueButton.setOnClickListener {
            findNavController().navigate(InstructionFragmentDirections.actionInstructionFragmentToLinkedFragment())
        }
        binding.instuctionViewPager.isUserInputEnabled = false

    }

    fun observeLiveData() {
        viewModel.eventListResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    Log.i("info", response.data.toString())
                    var responseData: EventData? = null
                    if (response.data != null) {
                        // eventList position can be obtained from the event card positon in events fragment
                        // which can be sent as argument
                        responseData = response.data.data.eventList[args.position].eventData
                        var resources = listOf<String>(responseData.description,responseData.rules,responseData.format,responseData.resources)
                        setUpAdapter(resources)
                    }

                    var resources = listOf<String>(
                        responseData!!.description,
                        responseData!!.rules,
                        responseData!!.format,
                        responseData!!.resources
                    )
                    setUpAdapter(resources)

                }
                is Resource.Error -> {
                    Log.i("info", response.data.toString())
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    fun setUpAdapter(resources: List<String>) {
        val adapter: InstructionAdapter = InstructionAdapter(resources)
        binding.instuctionViewPager.adapter = adapter
        val resource: List<String> = listOf("Description", "Rules", "Format", "Resources")
        TabLayoutMediator(
            binding.instructionTabLayout,
            binding.instuctionViewPager
        ) { tab, position ->
            tab.text = resource[position]
        }.attach()
    }

}