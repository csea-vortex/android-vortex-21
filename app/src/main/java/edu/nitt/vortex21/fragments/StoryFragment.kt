package edu.nitt.vortex21.fragments

import android.annotation.SuppressLint
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
    private var stories: List<Story>? = null
    // Current Week's Index
    private var currentIndex = 0

    private var STORY_PRESS_TIME = 0L
    private var STORY_LIMIT_TIME = 500L

    // To pause or skip story
    private val onTouchListener = View.OnTouchListener { view, motionEvent ->
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                STORY_PRESS_TIME = System.currentTimeMillis()
                binding.storiesProgress.pause()
                return@OnTouchListener false
            }
            MotionEvent.ACTION_UP -> {
                val now = System.currentTimeMillis()
                binding.storiesProgress.resume()
                return@OnTouchListener STORY_LIMIT_TIME < now - STORY_PRESS_TIME
            }
        }
        false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStoryBinding.inflate(inflater, container, false)

        // Receiving list of stories, and current index.
        stories = arguments?.getParcelableArrayList<Story>("story")!!
        currentIndex = arguments?.getInt("position")!!

        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = this.parentFragment as NavHostFragment
        val parent = navHostFragment.parentFragment as HomeFragment
        parent.binding.bottomNavigation.visibility = View.INVISIBLE
        setStories(stories?.get(currentIndex)!!)
        //dummy()

        // Set onClickListener and onTouchListener
        binding.apply {
            root.setOnTouchListener(onTouchListener)
            reverse.setOnClickListener {
                binding.storiesProgress.reverse()
            }
            skip.setOnClickListener {
                binding.storiesProgress.skip()
            }
        }

    }

//    private fun dummy() {
//        binding.skip.setOnTouchListener(object : OnSwipeTouchListener(requireActivity()) {
//            override fun onSwipeRight() {
//                super.onSwipeRight()
//                Log.i("Swiped", "right")
//                binding.imageStory.setImageResource(R.drawable.vortex_logo)
//                counter = 0
//            }
//
//            override fun onSwipeLeft() {
//                super.onSwipeLeft()
//                Log.i("Swiped", "left")
//                binding.imageStory.setImageResource(R.drawable.vortex_logo)
//                counter = 0
//            }
//
//            override fun onSwipeDown() {
//                super.onSwipeDown()
//                Log.i("Swiped", "down")
//                requireActivity()
//                    .onBackPressedDispatcher
//                    .addCallback {
//                        if (!navHostFragment.navController.navigateUp()) {
//                            // If there is no fragment then the user is trying to
//                            // quit the app.
//                            requireActivity().finish()
//                        }
//                    }
//            }
//        })
//    }

    override fun onComplete() {

        Log.i("onComplete", "Completed index: $currentIndex")

        // If last story then go back to events fragment.
        if(currentIndex == stories!!.size-1) {
            val navHostFragment = this.parentFragment as NavHostFragment
            val parent = navHostFragment.parentFragment as HomeFragment
            parent.binding.bottomNavigation.visibility = View.VISIBLE
            navHostFragment.navController.popBackStack(R.id.storyFragment, true)
        } else {
            currentIndex++
            counter = 0

            binding.storiesProgress.clear()
            setStories(stories!![currentIndex])
        }

    }

    override fun onPrev() {
        if (counter > 0)
            Picasso.get().load(currentWeekImageURLs!![--counter]).placeholder(R.drawable.vortex_logo)
                .into(binding.imageStory)
        if (counter == 0 && currentIndex!=0) {
            currentIndex--
            counter = 0

            binding.storiesProgress.clear()
            setStories(stories!![currentIndex])
        }
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

    override fun onNext() {
        if (counter < currentWeekImageURLs!!.size)
            Picasso.get().load(currentWeekImageURLs!![++counter]).placeholder(R.drawable.vortex_logo)
                .into(binding.imageStory)
    }

    override fun onResume() {
        super.onResume()
        binding.storiesProgress.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.storiesProgress.pause()
    }
}
