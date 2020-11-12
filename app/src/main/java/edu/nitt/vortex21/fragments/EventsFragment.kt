package edu.nitt.vortex21.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

import edu.nitt.vortex21.R
import edu.nitt.vortex21.databinding.FragmentEventsBinding
import edu.nitt.vortex21.helpers.viewLifecycle

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import edu.nitt.vortex21.adapters.StoryAdapter
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
        val linearLayoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerViewStory.layoutManager = linearLayoutManager
        storyList = ArrayList()

        // dummy data.
        populateData()

        storyAdapter = StoryAdapter(storyList as ArrayList<Story>) {
                selectedStoryIndex: Int ->
                listItemClicked(selectedStoryIndex)
        }

        binding.recyclerViewStory.adapter = storyAdapter

    }

    private fun populateData() {
        for(i in 1..15){
            when {
                i%3==0 -> storyList!!.add(Story(
                    listOf("https://domaingang.com/wp-content/uploads/2010/06/bitly.png",
                        "https://cdn0.tnwcdn.com/wp-content/blogs.dir/1/files/2012/12/technics-q-c-1108-622-4-730x409.jpeg"),
                    "$i", "Story #$i"))
                i%3==1 -> storyList!!.add(Story(
                    listOf("https://www.formdesigncenter.com/uploads/2013/10/lorempixel-3.jpg",
                        "https://i.pinimg.com/originals/a0/fa/64/a0fa64fcb7ae2af757bd5667f223764b.jpg"),
                    "$i", "Story #$i"))
                else -> storyList!!.add(Story(
                    listOf("https://www.finditshareit.com/wp-content/uploads/2015/07/cats-q-g-960-600-4.jpg",
                        "https://i.pinimg.com/originals/a0/fa/64/a0fa64fcb7ae2af757bd5667f223764b.jpg"),
                    "$i", "Story #$i"))
            }
        }
    }

    private fun listItemClicked(index: Int){
        val bundle = bundleOf(
            "story" to storyList,
            "position" to index
        )
        findNavController().navigate(R.id.action_fragmentEvents_to_fragmentStoryViewPager,bundle)
    }
}
