package edu.nitt.vortex2021.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import edu.nitt.vortex2021.BaseApplication
import edu.nitt.vortex2021.adapters.LeaderboardAdapter
import edu.nitt.vortex2021.databinding.FragmentLeaderboardBinding
import edu.nitt.vortex2021.helpers.Constants
import edu.nitt.vortex2021.helpers.Resource
import edu.nitt.vortex2021.helpers.viewLifecycle
import edu.nitt.vortex2021.model.LeaderboardRow
import edu.nitt.vortex2021.viewmodel.LeaderboardViewModel
import kotlin.math.max

class LeaderboardFragment : Fragment() {

    private var binding by viewLifecycle<FragmentLeaderboardBinding>()
    var leaderboardData = ArrayList<LeaderboardRow>()
    private lateinit var eventName: String
    private lateinit var leaderboardViewModel: LeaderboardViewModel

    private var currentPageIndex: Int = 0
    private var lastPageIndex: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLeaderboardBinding.inflate(inflater, container, false)
        initViewModel()
        observeLiveData()

        // ToDo: Get the name via the calling fragment
        eventName = "Linked"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFabOnClickListeners()
        initLeaderboardRecyclerView()
//        setPageIndex(0)
    }

    private fun initViewModel() {
        val factory = (requireActivity().application as BaseApplication)
            .applicationComponent
            .getViewModelProviderFactory()
        leaderboardViewModel = ViewModelProvider(this, factory).get(LeaderboardViewModel::class.java)
    }

    private fun observeLiveData() {
        leaderboardViewModel.leaderboardRowsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    lastPageIndex = (max(0, response.data!!.totalUsers - 1)) / Constants.LEADERBOARD_PAGE_SIZE

                    binding.currentPageButton.text = (currentPageIndex + 1).toString()

                    updateFabVisibility()

                    leaderboardData.clear()
                    leaderboardData.addAll(response.data.data)
                    binding.leaderboardList.adapter?.notifyDataSetChanged()
                }
            }
        }
    }

    private fun initFabOnClickListeners() {
        binding.firstPageFab.setOnClickListener { setPageIndex(0) }
        binding.naviateBeforeFab.setOnClickListener { setPageIndex(currentPageIndex - 1) }
        binding.navigateNextFab.setOnClickListener { setPageIndex(currentPageIndex + 1) }
        binding.lastPageFab.setOnClickListener { setPageIndex(lastPageIndex) }
        updateFabVisibility()
    }

    private fun initLeaderboardRecyclerView() {
        val arr = ArrayList<LeaderboardRow>()
        for (i: Int in 1..100) {
            arr.add(LeaderboardRow(i, "adityaa30", i * 1000, "National institute of Technology Tiruchirappalli"))
        }

        binding.leaderboardList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = LeaderboardAdapter(arr) {}
        }
    }


    private fun setPageIndex(newPageIndex: Int = 0) {
        if (newPageIndex == currentPageIndex || newPageIndex < 0 || newPageIndex > lastPageIndex) {
            return
        }
        currentPageIndex = newPageIndex
        leaderboardViewModel.fetchLeaderboardRowsOf(currentPageIndex, eventName)
        updateFabVisibility()
    }

    private fun updateFabVisibility() {
        binding.firstPageFab.isEnabled = currentPageIndex != 0
        binding.naviateBeforeFab.isEnabled = currentPageIndex != 0

        binding.navigateNextFab.isEnabled = (currentPageIndex != lastPageIndex)
        binding.lastPageFab.isEnabled = (currentPageIndex != lastPageIndex)
    }
}