package edu.nitt.vortex21.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import edu.nitt.vortex21.databinding.FragmentStorySlideBinding
import edu.nitt.vortex21.helpers.Constants
import edu.nitt.vortex21.helpers.viewLifecycle
import edu.nitt.vortex21.model.Story
import edu.nitt.vortex21.views.StoriesProgressView
import java.lang.Exception

private const val SLIDE_DURATION = 3000L

class StorySlideFragment(
    private val story: Story,
    private val onSlideShowComplete: () -> Unit
) : Fragment() {
    private var binding by viewLifecycle<FragmentStorySlideBinding>()
    private var pressTime = 0L
    private var limit = 500L
    private val onTouchListener = View.OnTouchListener {view, motionEvent ->
        when(motionEvent.action){
            MotionEvent.ACTION_DOWN->
            {
                pressTime = System.currentTimeMillis()
                binding.storyProgress.pause()
                return@OnTouchListener false
            }
            MotionEvent.ACTION_UP->
            {
                val now  = System.currentTimeMillis()
                binding.storyProgress.resume()
                return@OnTouchListener limit < now - pressTime
            }
        }
        false
    }


    private var mCurrentSlideIdx = -1
    private val mStoriesListener = object : StoriesProgressView.StoriesListener {
        override fun onNext() {
            updateSlide(mCurrentSlideIdx + 1)
        }

        override fun onPrev() {
            updateSlide(mCurrentSlideIdx - 1)
        }

        override fun onComplete() {
            mCurrentSlideIdx = story.slides.size
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
        binding.skip.setOnTouchListener(onTouchListener)

        binding.reverse.setOnClickListener {
            binding.storyProgress.reverse()
        }
        binding.reverse.setOnTouchListener(onTouchListener)
    }

    private fun initStorySlides() {
        binding.storyTitle.text = story.title

        try {
            Picasso.get()
                .load(Constants.BACKEND_BASE_URL + story.slides[0].imageUrl)
                .into(binding.profileImage, object : Callback {
                    override fun onSuccess() {

                    }

                    override fun onError(e: Exception?) {
                        e?.stackTrace
                    }

                })
        }catch (e:Exception){
            e.stackTrace
        }

        binding.storyProgress.apply {
            setStoriesCount(story.slides.size)
            setStoryDuration(SLIDE_DURATION)
            setStoriesListener(mStoriesListener)
        }
    }

    private fun updateSlide(updatedSlideIndex: Int) {
        mCurrentSlideIdx = updatedSlideIndex
        // ToDO: Change story slide in ViewPager
        if (mCurrentSlideIdx < 0 || mCurrentSlideIdx >= story.slides.size) return;

        try {
            Picasso.get()
                .load(Constants.BACKEND_BASE_URL + story.slides[mCurrentSlideIdx].imageUrl)
                .into(binding.image, object : Callback {
                    override fun onSuccess() {

                    }

                    override fun onError(e: Exception?) {
                        e?.stackTrace
                    }

                })
        }catch (e:Exception){
            e.stackTrace
        }

    }

    override fun onResume() {
        super.onResume()
        if (mCurrentSlideIdx < 0) {
            // The slide has yet not started
            binding.storyProgress.startStories(0)
            updateSlide(0)
        } else {
            binding.storyProgress.resume()
        }
    }

    override fun onPause() {
        super.onPause()
        if (mCurrentSlideIdx == story.slides.size) {
            binding.storyProgress.resetProgressBars()
            updateSlide(0)
            mCurrentSlideIdx = -1 // Reset the position
        } else {
            binding.storyProgress.pause()
        }
    }
}