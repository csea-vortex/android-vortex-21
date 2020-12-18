package edu.nitt.vortex21.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import edu.nitt.vortex21.R
import edu.nitt.vortex21.adapters.StoryTrayAdapter
import edu.nitt.vortex21.databinding.FragmentEventsBinding
import edu.nitt.vortex21.helpers.Resource
import edu.nitt.vortex21.helpers.initGradientBackgroundAnimation
import edu.nitt.vortex21.helpers.viewLifecycle
import edu.nitt.vortex21.model.Stories
import edu.nitt.vortex21.viewmodel.StoryViewModel


class EventsFragment : Fragment() {

    private var binding by viewLifecycle<FragmentEventsBinding>()
    private val storyViewModel: StoryViewModel by lazy {
        ViewModelProvider(this).get(StoryViewModel::class.java)
    }
    private val mStories = Stories()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventsBinding.inflate(inflater, container, false)
        initGradientBackgroundAnimation(binding.root)
        observeLiveData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setTitle(R.string.events)


        storyViewModel.fetchStoriesOfCategory("techie-tuesdays")


        val navHostFragment = this.parentFragment as NavHostFragment
        val parent = navHostFragment.parentFragment as HomeFragment
        parent.binding.bottomNavigation.visibility = View.VISIBLE
        // Stories tray
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
    }

    private fun observeLiveData() {
        storyViewModel.storyResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    mStories.clear()
                    mStories.addAll(response.data!!.data)
                    binding.recyclerViewStory.apply {
                        adapter?.notifyDataSetChanged()
                        scheduleLayoutAnimation()
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(
                        requireContext(),
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}
