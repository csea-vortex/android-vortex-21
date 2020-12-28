package edu.nitt.vortex2021.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import edu.nitt.vortex2021.BaseApplication
import edu.nitt.vortex2021.adapters.LeaderboardAdapter
import edu.nitt.vortex2021.databinding.FragmentLeaderboardBinding
import edu.nitt.vortex2021.helpers.*
import edu.nitt.vortex2021.model.LeaderboardRow
import edu.nitt.vortex2021.viewmodel.LeaderboardViewModel
import edu.nitt.vortex2021.viewmodel.LinkedViewModel
import kotlin.math.max

class LeaderboardFragment : Fragment() {

    private var binding by viewLifecycle<FragmentLeaderboardBinding>()

    private val leaderboardData = ArrayList<LeaderboardRow>()

    private lateinit var leaderboardViewModel: LeaderboardViewModel
    private lateinit var linkedViewModel: LinkedViewModel

    private val args: LeaderboardFragmentArgs by navArgs()
    private lateinit var eventType: AppSupportedEvents

    private var currentPageIndex: Int = -1
    private var lastPageIndex: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLeaderboardBinding.inflate(inflater, container, false)

        eventType = getEventFromTitle(args.event.eventData.title)

        initViewModel()
        observeLiveData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOnClickListeners()
        initLeaderboardRecyclerView()
        setPageIndex(0)
    }

    private fun initViewModel() {
        val factory = (requireActivity().application as BaseApplication)
            .applicationComponent
            .getViewModelProviderFactory()
        leaderboardViewModel = ViewModelProvider(this, factory).get(LeaderboardViewModel::class.java)
        linkedViewModel = ViewModelProvider(this, factory).get(LinkedViewModel::class.java)
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
        binding.currentPageButton.visibility = View.VISIBLE
        binding.refreshButton.isEnabled = true
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
        binding.currentPageButton.visibility = View.GONE
        binding.refreshButton.isEnabled = false
    }

    private fun observeLiveData() {
        leaderboardViewModel.leaderboardRowsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    lastPageIndex = (max(0, response.data!!.totalUsers - 1)) / Constants.LEADERBOARD_PAGE_SIZE
                    binding.currentPageButton.text = (currentPageIndex + 1).toString()
                    updateFabVisibility()

                    leaderboardData.clear()
                    leaderboardData.addAll(response.data.data)
                    binding.leaderboardList.adapter?.notifyDataSetChanged()
                }
                is Resource.Error -> {
                    hideProgressBar()
                    showToastMessage(requireContext(), response.message)
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }

        linkedViewModel.currentScoreRankResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    val data = response.data!!
                    addCurrentUserScoreRow(data.rank, data.totalScore)
                }
            }
        }
    }

    private fun addCurrentUserScoreRow(rank: Int, score: Int) {
        val userStore = UserSharedPrefStore(requireContext())
        val topRow = LeaderboardRow(
            rank,
            userStore.username,
            score,
            userStore.college
        )

        // Only show if user not already exists
        var found = false
        for (row in leaderboardData) {
            found = found || (row.username == topRow.username)
        }

        if (!found) {
            leaderboardData.add(0, topRow)
            binding.leaderboardList.adapter?.notifyDataSetChanged()
        }
    }

    private fun initOnClickListeners() {
        binding.firstPageFab.setOnClickListener { setPageIndex(0) }
        binding.naviateBeforeFab.setOnClickListener { setPageIndex(currentPageIndex - 1) }
        binding.navigateNextFab.setOnClickListener { setPageIndex(currentPageIndex + 1) }
        binding.lastPageFab.setOnClickListener { setPageIndex(lastPageIndex) }
        binding.refreshButton.setOnClickListener { setPageIndex(max(0, currentPageIndex), true) }
        updateFabVisibility()
    }

    private fun initLeaderboardRecyclerView() {
        binding.leaderboardList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = LeaderboardAdapter(leaderboardData) {}
        }
    }


    private fun setPageIndex(newPageIndex: Int = 0, forceRefresh: Boolean = false) {
        if (newPageIndex == currentPageIndex || newPageIndex < 0 || newPageIndex > lastPageIndex) {
            if (!forceRefresh) return
        }

        currentPageIndex = newPageIndex
        leaderboardViewModel.fetchLeaderboardRowsOf(currentPageIndex, eventType)

        if (eventType == AppSupportedEvents.LINKED) {
            linkedViewModel.getCurrentScoreRank()
        }

        updateFabVisibility()
    }

    private fun updateFabVisibility() {
        binding.firstPageFab.isEnabled = currentPageIndex != 0
        binding.naviateBeforeFab.isEnabled = currentPageIndex != 0

        binding.navigateNextFab.isEnabled = (currentPageIndex != lastPageIndex)
        binding.lastPageFab.isEnabled = (currentPageIndex != lastPageIndex)
    }
}