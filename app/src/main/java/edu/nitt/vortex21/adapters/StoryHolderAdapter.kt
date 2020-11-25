package edu.nitt.vortex21.adapters

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import edu.nitt.vortex21.fragments.StorySlideFragment
import edu.nitt.vortex21.model.Story

class StoryHolderAdapter(
    fm: FragmentManager,
    private val storyList: List<Story>,
    private val onChangeStory: (Int) -> Unit
) : FragmentStatePagerAdapter(
    fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    override fun getCount(): Int = storyList.size
    override fun getItem(position: Int): StorySlideFragment {
        return StorySlideFragment(storyList[position]) {
            onChangeStory(position)
        }
    }
}