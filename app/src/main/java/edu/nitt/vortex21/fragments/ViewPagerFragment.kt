package edu.nitt.vortex21.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import edu.nitt.vortex21.databinding.FragmentViewPagerBinding
import edu.nitt.vortex21.helpers.viewLifecycle
import edu.nitt.vortex21.model.Story

class ViewPagerFragment: Fragment(), StoryFragment.SetFragmentInterface {

    private lateinit var viewPager: ViewPager
    private var binding by viewLifecycle<FragmentViewPagerBinding>()
    private var STORIES_COUNT = 0
    private lateinit var stories: List<Story>
    private var currentIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewPagerBinding.inflate(inflater, container, false)

        stories = arguments?.getParcelableArrayList<Story>("story")!!
        currentIndex = arguments?.getInt("position")!!
        STORIES_COUNT = stories.size

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = binding.storyViewPager
        viewPager.adapter = ScreenSlidePagerAdapter(parentFragmentManager)
        viewPager.currentItem = currentIndex
    }

    private inner class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(
        fm,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
        override fun getCount(): Int = STORIES_COUNT

        override fun getItem(position: Int): Fragment {
            val frag = StoryFragment()
            val currentStory = stories[position]

            val bundle = bundleOf(
                "story" to currentStory,
                "position" to position,
                "size" to STORIES_COUNT
            )
            frag.arguments = bundle
            frag.setInterface(this@ViewPagerFragment)

            return frag
        }
    }

    override fun setFragmentIndex(index: Int) {
        viewPager.currentItem = index
    }
}
