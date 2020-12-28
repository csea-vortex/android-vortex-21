package edu.nitt.vortex2021.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import edu.nitt.vortex2021.BaseApplication
import edu.nitt.vortex2021.R
import edu.nitt.vortex2021.adapters.EventAdapter
import edu.nitt.vortex2021.adapters.StoryTrayAdapter
import edu.nitt.vortex2021.databinding.FragmentEventsBinding
import edu.nitt.vortex2021.helpers.Resource
import edu.nitt.vortex2021.helpers.initGradientBackgroundAnimation
import edu.nitt.vortex2021.helpers.showToastMessage
import edu.nitt.vortex2021.helpers.viewLifecycle
import edu.nitt.vortex2021.model.Events
import edu.nitt.vortex2021.model.Stories
import edu.nitt.vortex2021.viewmodel.EventViewModel
import edu.nitt.vortex2021.viewmodel.StoryViewModel


class EventsFragment : Fragment() {

    private var binding by viewLifecycle<FragmentEventsBinding>()
    private lateinit var storyViewModel: StoryViewModel
    private lateinit var eventViewModel: EventViewModel

    private val mEvents = Events()
    private val mStories = Stories()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventsBinding.inflate(inflater, container, false)
        initViewModel()
        initGradientBackgroundAnimation(binding.root)
        return binding.root
    }

    private fun initViewModel() {
        val factory = (requireActivity().application as BaseApplication)
            .applicationComponent
            .getViewModelProviderFactory()

        storyViewModel = ViewModelProvider(this, factory).get(StoryViewModel::class.java)
        eventViewModel = ViewModelProvider(this, factory).get(EventViewModel::class.java)

        observeLiveData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setTitle(R.string.events)

        initRecyclerViews()
        storyViewModel.fetchStoriesOfCategory("techie-tuesdays")
        eventViewModel.fetchEventList()
    }

    private fun observeLiveData() {
        storyViewModel.storyResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    mStories.clear()
                    var stories = response.data!!.data
                    stories = stories.sortedByDescending { it.visibleAt }
                    mStories.addAll(stories)
                    binding.recyclerViewStory.apply {
                        visibility = View.VISIBLE
                        adapter?.notifyDataSetChanged()
                        scheduleLayoutAnimation()
                    }
                }
                is Resource.Error -> {
                    showToastMessage(requireContext(), response.message)
                }
            }
        }


        eventViewModel.apply {
            eventListResponse.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        binding.eventRecyclerView.adapter?.apply {
                            mEvents.clear()
                            mEvents.addAll(response.data!!.data.events)
                            notifyDataSetChanged()
                        }
                    }
                    is Resource.Error -> {
                        showToastMessage(requireContext(), response.message)
                    }
                }
            }

            eventRegisterResponse.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        showToastMessage(requireContext(), response.data!!.message)
                        eventViewModel.fetchEventList()
                    }
                    is Resource.Error -> {
                        showToastMessage(requireContext(), response.message)
                    }
                }
            }
        }

    }

    private fun initRecyclerViews() {
        binding.recyclerViewStory.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = StoryTrayAdapter(mStories) { storyIdx ->
                findNavController().navigate(
                    EventsFragmentDirections
                        .actionFragmentEventsToFragmentStoryHolder(storyIdx, mStories)
                )
            }
        }

        binding.eventRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = EventAdapter(
                mEvents,
                onPlayButtonClickListener = {
                    findNavController().navigate(
                        EventsFragmentDirections.actionFragmentEventsToInstructionFragment(it)
                    )
                },
                onRegisterEventButtonClickListener = {
                    eventViewModel.sendEventRegistrationRequest(
                        it.eventData.id
                    )
                },
                onLeaderboardButtonClickListener = {

                }
            )
        }
    }
}
