package edu.nitt.vortex2021.fragments

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import edu.nitt.vortex2021.adapters.LeaderboardAdapter
import edu.nitt.vortex2021.databinding.FragmentLeaderboardBinding
import edu.nitt.vortex2021.helpers.viewLifecycle
import edu.nitt.vortex2021.model.LeaderboardRow

class LeaderboardFragment : Fragment() {

    private var binding by viewLifecycle<FragmentLeaderboardBinding>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLeaderboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLeaderboardRecyclerView()
    }


    fun initLeaderboardRecyclerView() {
        binding.leaderboardList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            // ToDo: Get data from the server
            // adapter = LeaderboardAdapter(dummy) {}
        }
    }
}