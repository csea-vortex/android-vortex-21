package edu.nitt.vortex2021.fragments


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
import edu.nitt.vortex2021.BaseApplication
import edu.nitt.vortex2021.R
import edu.nitt.vortex2021.adapters.StoryTrayAdapter
import edu.nitt.vortex2021.databinding.FragmentEventsBinding
import edu.nitt.vortex2021.helpers.Resource
import edu.nitt.vortex2021.helpers.initGradientBackgroundAnimation
import edu.nitt.vortex2021.helpers.viewLifecycle
import edu.nitt.vortex2021.model.Stories
import edu.nitt.vortex2021.viewmodel.StoryViewModel


class EventsFragment : Fragment() {

    private var binding by viewLifecycle<FragmentEventsBinding>()
    private lateinit var viewmodel: StoryViewModel
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

        viewmodel = ViewModelProvider(this, factory).get(StoryViewModel::class.java)
        observeLiveData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setTitle(R.string.events)

        binding.playButton.isEnabled = false

        viewmodel.fetchStoriesOfCategory("techie-tuesdays")

        binding.ratingBar.rating = 4f
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
        binding.registerButton.setOnClickListener{
            //TODO(): call register route
            binding.playButton.isEnabled = true
            Toast.makeText(requireContext(),"registered Successfully",Toast.LENGTH_SHORT).show()
            binding.registerButton.isEnabled = false
        }
        binding.playButton.setOnClickListener {
            findNavController().navigate(EventsFragmentDirections.actionFragmentEventsToInstructionFragment())
        }



    }

    private fun observeLiveData() {
        viewmodel.storyResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    mStories.clear()
                    var stories = response.data!!.data
                    stories = stories.sortedByDescending { it.visibleAt }
                    mStories.addAll(stories)
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
