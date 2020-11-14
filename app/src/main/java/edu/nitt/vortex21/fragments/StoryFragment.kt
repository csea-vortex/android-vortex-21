package edu.nitt.vortex21.fragments

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.squareup.picasso.Picasso
import edu.nitt.vortex21.R
import edu.nitt.vortex21.databinding.FragmentStoryBinding
import edu.nitt.vortex21.helpers.viewLifecycle
import edu.nitt.vortex21.storieslibrary.StoriesProgressView
import edu.nitt.vortex21.model.Story as Story


class StoryFragment : Fragment(), StoriesProgressView.StoriesListener {

    private var binding by viewLifecycle<FragmentStoryBinding>()

    private var currentWeekImageURLs: List<String>? = null

    // counter to store the index of current story in the week
    private var counter = 0
    // All week's story.
    private var story: Story? = null
    private var STORIES_COUNT = 0
    // Current Week's Index
    private var currentIndex = 0

    private var STORY_PRESS_TIME = 0L
    private var STORY_LIMIT_TIME = 500L

    private val tagTouched = "Touched"
    private val tagClicked = "Clicked"
    private val tagCalled = "Called"
    private val tagInitialised = "Initialised"
    private val tagLaunched = "Launched"
    private val tagSet = "Set"


    // To pause or skip story
    private lateinit var onTouchListener: View.OnTouchListener

    interface SetFragmentInterface {
        fun setFragmentIndex(index:Int)
    }
    private lateinit var viewPagerInterface: SetFragmentInterface

    fun setInterface(context: ViewPagerFragment) {
        Log.i(tagInitialised,"Interface initialised")
        viewPagerInterface = context
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if(isVisible) {
            // TODO: Keep progress at 0 until this is hit.
            counter = 0
            story?.let { setStories(it) }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStoryBinding.inflate(inflater, container, false)

        // Receiving list of stories, and current index.
        story = arguments?.getParcelable<Story>("story")!!
        currentIndex = arguments?.getInt("position")!!
        STORIES_COUNT = arguments?.getInt("size")!!

        Log.i(tagLaunched, "Launched story for week: #$currentIndex")

        STORY_PRESS_TIME = 0L
        STORY_LIMIT_TIME = 500L

        onTouchListener = View.OnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    STORY_PRESS_TIME = System.currentTimeMillis()
                    binding.storiesProgress.pause()
                    Log.i("OnTouch","Story paused.")
                    return@OnTouchListener false
                }
                MotionEvent.ACTION_UP -> {
                    val now = System.currentTimeMillis()
                    binding.storiesProgress.resume()
                    Log.i("OnTouch","Story resumed/changed.")
                    return@OnTouchListener STORY_LIMIT_TIME < now - STORY_PRESS_TIME
                }
            }
            false
        }

        // Set onClickListener and onTouchListener
        binding.apply {
            reverse.setOnClickListener {
                binding.storiesProgress.reverse()
            }
            skip.setOnClickListener {
                binding.storiesProgress.skip()
            }

            reverse.setOnTouchListener(onTouchListener)
            skip.setOnTouchListener(onTouchListener)
        }

        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = this.parentFragment as NavHostFragment
        val parent = navHostFragment.parentFragment as HomeFragment
        parent.binding.bottomNavigation.visibility = View.INVISIBLE

    }

    private fun setStories(story: Story) {
        currentWeekImageURLs = story.imageurl

        binding.storiesProgress.setStoriesCount(currentWeekImageURLs!!.size)
        binding.storiesProgress.setStoryDuration(5000L)
        binding.storiesProgress.setStoriesListener(this)
        binding.storiesProgress.startStories(counter)
        binding.tvStory.text = story.storyName
        Picasso.get().load(currentWeekImageURLs!![counter]).into(binding.imageStory)
    }

    override fun onComplete() {

        //Log.i(tagCalled, "onComplete(): Completed index- $currentIndex")

        // If last story then go back to events fragment.
        if(currentIndex == STORIES_COUNT-1) {
            val navHostFragment = this.parentFragment as NavHostFragment
            val parent = navHostFragment.parentFragment as HomeFragment
            parent.binding.bottomNavigation.visibility = View.VISIBLE
            navHostFragment.navController.popBackStack(R.id.storyFragment, true)
        } else {
            currentIndex++
            counter = 0
            viewPagerInterface.setFragmentIndex(currentIndex)
        }

    }

    override fun onPrev() {

        Log.i(tagClicked, "onPrev")

        if (counter > 0)
            Picasso.get().load(currentWeekImageURLs!![--counter]).placeholder(R.drawable.vortex_logo)
                .into(binding.imageStory)
        else if (currentIndex != 0) {
            currentIndex--
            counter = 0
            viewPagerInterface.setFragmentIndex(currentIndex)
        }
    }

    override fun onNext() {

        Log.i(tagClicked, "onNext")

        if (counter < currentWeekImageURLs!!.size-1)
            Picasso.get().load(currentWeekImageURLs!![++counter]).placeholder(R.drawable.vortex_logo)
                .into(binding.imageStory)
        else if(currentIndex < STORIES_COUNT-1){
            currentIndex++
            counter = 0
            viewPagerInterface.setFragmentIndex(currentIndex)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.storiesProgress.resume()
        Log.i(tagCalled,"onResumed() is called of Week#$currentIndex")
        counter = 0
        story?.let { setStories(it) }
    }

    override fun onPause() {
        super.onPause()
        binding.storiesProgress.pause()
        Log.i(tagCalled,"onPause() is called of Week#$currentIndex")
    }
}
