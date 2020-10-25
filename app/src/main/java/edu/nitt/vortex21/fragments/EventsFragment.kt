package edu.nitt.vortex21.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import edu.nitt.vortex21.R
import edu.nitt.vortex21.adapters.StoryAdapter
import edu.nitt.vortex21.databinding.FragmentEventsBinding
import edu.nitt.vortex21.helpers.viewLifecycle
import edu.nitt.vortex21.model.Story


class EventsFragment : Fragment() {

    private var binding by viewLifecycle<FragmentEventsBinding>()
    private var storyList : MutableList<Story>? = null
    private var storyAdapter : StoryAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setTitle(R.string.events)
        val linearLayoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,true)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        binding.recyclerViewStory.layoutManager = linearLayoutManager

        storyList = ArrayList()
        storyAdapter = requireContext().let {
            StoryAdapter(
                it,
                storyList as ArrayList<Story>,
                { selectedStoryItem: Story ->
                    listItemClicked(selectedStoryItem)
                })
        }
        binding.recyclerViewStory.adapter = storyAdapter

    }

    private fun listItemClicked(story: Story){
        val bundle = bundleOf(
            "storyImage" to story.imageurl,
            "storyName" to story.storyName,
            "storyId" to story.storyid
        )
        findNavController().navigate(R.id.action_fragmentEvents_to_storyFragment,bundle)
    }
}

