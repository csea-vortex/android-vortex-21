package edu.nitt.vortex2021.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import edu.nitt.vortex2021.BaseApplication
import edu.nitt.vortex2021.R
import edu.nitt.vortex2021.adapters.WorkshopAdapter
import edu.nitt.vortex2021.databinding.FragmentWorkshopsBinding
import edu.nitt.vortex2021.helpers.*
import edu.nitt.vortex2021.model.Workshop
import edu.nitt.vortex2021.viewmodel.DataViewModel
import java.util.*
import kotlin.collections.ArrayList


class WorkshopsFragment : Fragment() {

    private var binding by viewLifecycle<FragmentWorkshopsBinding>()

    private lateinit var dataViewModel: DataViewModel
    private val workshops = ArrayList<Workshop>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkshopsBinding.inflate(inflater, container, false)
        initViewModels()
        initGradientBackgroundAnimation(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setTitle(R.string.workshops)

        initRecyclerViews()
        dataViewModel.fetchWorkshops()
    }

    private fun initRecyclerViews() {
        val userStore = UserSharedPrefStore(requireContext())
        binding.workshopsList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = WorkshopAdapter(userStore.email, workshops) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.link))
                requireContext().startActivity(intent)
            }
        }
    }


    private fun initViewModels() {
        val factory = (requireActivity().application as BaseApplication)
            .applicationComponent
            .getViewModelProviderFactory()

        dataViewModel = ViewModelProvider(this, factory).get(DataViewModel::class.java)
        observeLiveData()
    }


    private fun observeLiveData() {
        dataViewModel.workshopsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    val data = response.data!!.workshops
                    workshops.clear()
                    workshops.addAll(data)
                    binding.workshopsList.adapter?.notifyDataSetChanged()
                }
                is Resource.Error -> {
                    showToastMessage(requireContext(), response.message)
                }
            }
        }
    }
}