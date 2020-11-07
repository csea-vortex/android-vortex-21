package edu.nitt.vortex21.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.squareup.picasso.Picasso
import edu.nitt.vortex21.R
import edu.nitt.vortex21.databinding.FragmentStoryBinding
import edu.nitt.vortex21.helpers.OnSwipeTouchListener
import edu.nitt.vortex21.helpers.viewLifecycle
import edu.nitt.vortex21.storieslibrary.StoriesProgressView
import edu.nitt.vortex21.model.Story as Story


class StoryFragment : Fragment(), StoriesProgressView.StoriesListener {


    private var binding by viewLifecycle<FragmentStoryBinding>()

    private var imagesList: List<String>? = null
    private var storyids: List<String>? = null
    private var counter = 0
    private var pressTime = 0L
    private var limit = 500L
    private var story: Story? = null
    private var stories: List<Story>? = null
    private var currentIndex = 0
    private lateinit var navHostFragment:NavHostFragment

    private val onTouchListener = View.OnTouchListener { view, motionEvent ->
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                pressTime = System.currentTimeMillis()
                binding.storiesProgress.pause()
                return@OnTouchListener false
            }
            MotionEvent.ACTION_UP -> {
                val now = System.currentTimeMillis()
                binding.storiesProgress.resume()
                return@OnTouchListener limit < now - pressTime
            }
        }
        false
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStoryBinding.inflate(inflater, container, false)
        stories = arguments?.getParcelableArrayList<Story>("story")!!
        currentIndex = arguments?.getInt("position")!!
        story = stories!![currentIndex]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //requireActivity().setTitle("Story")

        navHostFragment = this.parentFragment as NavHostFragment
        val parent = navHostFragment.parentFragment as HomeFragment
        parent.binding.bottomNavigation.visibility = View.INVISIBLE
        getStories()
        //dummy()

        binding.apply {
            reverse.setOnClickListener {
                Log.i("Touched", "Reverse")
                binding.storiesProgress.reverse()
                Log.i("reverse", counter.toString())
            }
            reverse.setOnTouchListener(onTouchListener)

            skip.setOnClickListener {
                Log.i("Touched", "Skip")
                Log.i("skip", counter.toString())

                binding.storiesProgress.skip()

            }
            skip.setOnTouchListener(onTouchListener)
        }

    }

    private fun dummy() {
        binding.skip.setOnTouchListener(object : OnSwipeTouchListener(requireActivity()) {
            override fun onSwipeRight() {
                super.onSwipeRight()
                Log.i("Swiped", "right")
                binding.imageStory.setImageResource(R.drawable.vortex_logo)
                counter = 0
            }

            override fun onSwipeLeft() {
                super.onSwipeLeft()
                Log.i("Swiped", "left")
                binding.imageStory.setImageResource(R.drawable.vortex_logo)
                counter = 0
            }

            override fun onSwipeDown() {
                super.onSwipeDown()
                Log.i("Swiped", "down")
                requireActivity()
                    .onBackPressedDispatcher
                    .addCallback {
                        if (!navHostFragment.navController.navigateUp()) {
                            // If there is no fragment then the user is trying to
                            // quit the app.
                            requireActivity().finish()
                        }
                    }
            }
        })
    }


    private fun getStories() {
        imagesList = ArrayList()
        storyids = ArrayList()

        imagesList = story?.imageurl

        binding.storiesProgress.setStoriesCount(imagesList!!.size)
        binding.storiesProgress.setStoryDuration(5000L)
        binding.storiesProgress.setStoriesListener(this)
        binding.storiesProgress.startStories(counter)
        binding.tvStory.text = story!!.storyName
        Picasso.get().load(imagesList!![counter]).into(binding.imageStory)


    }

    override fun onComplete() {

        println("Completed index: $currentIndex")

        if(currentIndex == stories!!.size-1) {
            val navHostFragment = this.parentFragment as NavHostFragment
            val parent = navHostFragment.parentFragment as HomeFragment
            parent.binding.bottomNavigation.visibility = View.VISIBLE
            navHostFragment.navController.popBackStack(R.id.storyFragment, true)
        } else {
            currentIndex++
            story = stories!![currentIndex]
            imagesList = story!!.imageurl
            counter = 0

            binding.storiesProgress.clear()

            binding.storiesProgress.setStoriesCount(imagesList!!.size)
            binding.storiesProgress.setStoryDuration(5000L)
            binding.storiesProgress.setStoriesListener(this)
            binding.storiesProgress.startStories(counter)
            binding.tvStory.text = story!!.storyName
            Picasso.get().load(imagesList!![counter]).into(binding.imageStory)
        }

    }

    override fun onPrev() {
        if (counter > 0)
            Picasso.get().load(imagesList!![--counter]).placeholder(R.drawable.vortex_logo)
                .into(binding.imageStory)
        if (counter == 0 && currentIndex!=0) {
            currentIndex--
            story = stories!![currentIndex]
            imagesList = story!!.imageurl
            counter = 0

            binding.storiesProgress.clear()

            binding.storiesProgress.setStoriesCount(imagesList!!.size)
            binding.storiesProgress.setStoryDuration(5000L)
            binding.storiesProgress.setStoriesListener(this)
            binding.storiesProgress.startStories(counter)
            binding.tvStory.text = story!!.storyName
            Picasso.get().load(imagesList!![counter]).into(binding.imageStory)
        }
    }

    override fun onNext() {
        if (counter < imagesList!!.size)
            Picasso.get().load(imagesList!![++counter]).placeholder(R.drawable.vortex_logo)
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
