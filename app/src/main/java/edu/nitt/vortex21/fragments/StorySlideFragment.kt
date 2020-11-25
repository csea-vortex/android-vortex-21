package edu.nitt.vortex21.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import edu.nitt.vortex21.databinding.FragmentStorySlideBinding
import edu.nitt.vortex21.helpers.Constants
import edu.nitt.vortex21.helpers.viewLifecycle
import edu.nitt.vortex21.model.Story
import edu.nitt.vortex21.storieslibrary.StoriesProgressView

private const val SLIDE_DURATION = 2000L

interface OnSlideChangeListener {
    fun onChange(position: Int)
}

class StorySlideFragment(
    private val story: Story,
    private val onSlideShowComplete: () -> Unit
) : Fragment() {
    private var binding by viewLifecycle<FragmentStorySlideBinding>()

    private var mCurrentSlideIdx = -1
    private val mStoriesListener = object : StoriesProgressView.StoriesListener {
        override fun onNext() {
            updateSlide(1)
        }

        override fun onPrev() {
            updateSlide(-1)
        }

        override fun onComplete() {
            onSlideShowComplete()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentStorySlideBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initStorySlides()

        binding.skip.setOnClickListener {
            binding.storyProgress.skip()
        }

        binding.reverse.setOnClickListener {
            binding.storyProgress.reverse()
        }
    }

    private fun initStorySlides() {
        binding.storyTitle.text = story.title
        Picasso.get()
            .load(Constants.BACKEND_BASE_URL + story.slides[0].imageUrl)
            .into(binding.profileImage)
        binding.storyProgress.apply {
            setStoriesCount(story.slides.size)
            setStoryDuration(SLIDE_DURATION)
            setStoriesListener(mStoriesListener)
        }
    }

    private fun updateSlide(delta: Int) {
        mCurrentSlideIdx += delta
        // ToDO: Change story slide in ViewPager
        if (mCurrentSlideIdx < 0 || mCurrentSlideIdx >= story.slides.size) return;
        Picasso.get()
            .load(Constants.BACKEND_BASE_URL + story.slides[mCurrentSlideIdx].imageUrl)
            .into(binding.image)
    }

    override fun onResume() {
        super.onResume()
        if (mCurrentSlideIdx < 0) {
            // The slide has yet not started
            mCurrentSlideIdx = 0
            updateSlide(0)
            binding.storyProgress.startStories()
        } else {
            binding.storyProgress.resume()
        }
    }

    override fun onPause() {
        super.onPause()
        binding.storyProgress.pause()
    }
}