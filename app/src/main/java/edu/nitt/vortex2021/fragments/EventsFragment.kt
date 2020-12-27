package edu.nitt.vortex2021.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import edu.nitt.vortex2021.BaseApplication
import edu.nitt.vortex2021.R
import edu.nitt.vortex2021.adapters.EventAdapter
import edu.nitt.vortex2021.adapters.StoryTrayAdapter
import edu.nitt.vortex2021.databinding.FragmentEventsBinding
import edu.nitt.vortex2021.helpers.Resource
import edu.nitt.vortex2021.helpers.initGradientBackgroundAnimation
import edu.nitt.vortex2021.helpers.viewLifecycle
import edu.nitt.vortex2021.model.EventList
import edu.nitt.vortex2021.model.Stories
import edu.nitt.vortex2021.viewmodel.EventViewModel
import edu.nitt.vortex2021.viewmodel.StoryViewModel


class EventsFragment : Fragment() {

    private var binding by viewLifecycle<FragmentEventsBinding>()
    private lateinit var viewmodel: StoryViewModel
    private lateinit var eventViewModel: EventViewModel
    private val mStories = Stories()
    private  var currentRound:Int = 0// mutableLiveData for showing the progress


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
        eventViewModel = ViewModelProvider(this,factory).get(EventViewModel::class.java)
        observeLiveData()
        eventViewModel.fetchEventList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setTitle(R.string.events)

        viewmodel.fetchStoriesOfCategory("techie-tuesdays")



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
      /*  binding.registerButton.setOnClickListener{
            //TODO(): call register route
            binding.playButton.isEnabled = true
            Toast.makeText(requireContext(),"Registered Successfully",Toast.LENGTH_SHORT).show()
            binding.statusTextView.text = "You are ready to go"
            binding.registerButton.isEnabled = false
        }
       */



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

        eventViewModel.eventListResponse.observe(viewLifecycleOwner){response->
            when(response){
                is Resource.Success->{
                    var responseList:List<EventList>
                    if(response.data!=null){
                        responseList = response.data.data.eventList
                        setUpAdapter(responseList)
                    }
                }
            }

        }

        //observe currentRoundStatus here
        //inside it... below piece of code and update the textview too ...
      /*  for(i in 1 .. currentRound){
            val image: ImageView = binding.ratingBar.findViewWithTag<ImageView>("$i")
            image.setImageResource(R.drawable.crownr)
        }*/
        //binding.statusTextView.text = "You are currently at round 4"
    }

    fun setUpAdapter(eventList: List<EventList>){
        val adapter:EventAdapter = EventAdapter(eventList)
        binding.eventRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.eventRecyclerView.adapter = adapter

    }

}
