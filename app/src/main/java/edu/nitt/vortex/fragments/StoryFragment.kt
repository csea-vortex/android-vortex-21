package edu.nitt.vortex.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import edu.nitt.vortex.R
import edu.nitt.vortex.databinding.FragmentEventsBinding
import edu.nitt.vortex.databinding.FragmentStoryBinding
import edu.nitt.vortex.helpers.viewLifecycle
import jp.shts.android.storiesprogressview.StoriesProgressView
import jp.shts.android.storiesprogressview.StoriesProgressView.StoriesListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_story.*


class StoryFragment : Fragment(), StoriesListener {



    private var binding by viewLifecycle<FragmentStoryBinding>()

    private var imagesList:List<String>? = null
    private var storyids:List<String>? = null
    private var counter=0
    private var pressTime = 0L
    private var limit = 500L
    private val onTouchListener = View.OnTouchListener { view, motionEvent ->
        when(motionEvent.action){
            MotionEvent.ACTION_DOWN->
            {
                pressTime = System.currentTimeMillis()
                binding.storiesProgress.pause()
                return@OnTouchListener false
            }
            MotionEvent.ACTION_UP->
            {
                val now  = System.currentTimeMillis()
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //requireActivity().setTitle("Story")

        val navHostFragment = this.parentFragment as NavHostFragment
        val parent = navHostFragment.parentFragment as HomeFragment
        parent.binding.bottomNavigation.visibility = View.INVISIBLE
       binding.apply {
           reverse.setOnClickListener{
               Log.i("Touched","Reverse")
               binding.storiesProgress.reverse()
               if(counter==1) {
                   binding.imageStory.setImageResource(R.drawable.vortex_logo)
                   counter--;
               }
               else if(counter==2) {
                  counter--;
                   binding.imageStory.setImageResource(R.drawable.facebook)
               }

               Log.i("reverse",counter.toString())
           }
           reverse.setOnTouchListener(onTouchListener)

           skip.setOnClickListener{
               Log.i("Touched","Skip")
               Log.i("skip",counter.toString())
               counter++;
               binding.storiesProgress.skip()
               if(counter==1)
               binding.imageStory.setImageResource(R.drawable.facebook)
               if(counter==2)
                   binding.imageStory.setImageResource(R.drawable.twitter)
           }
           skip.setOnTouchListener(onTouchListener)
       }

    }


    private fun getStories(){
        imagesList = ArrayList()
        storyids = ArrayList()
        binding.storiesProgress.setStoriesCount(5)
        binding.storiesProgress.setStoryDuration(5000)
        binding.storiesProgress.setStoriesListener(this@StoryFragment)
        binding.storiesProgress.startStories(counter)
    }





    override fun onResume() {
        super.onResume()
        binding.storiesProgress.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.storiesProgress.pause()
    }

    override fun onComplete() {
      counter++
        binding.imageStory.setImageResource(R.drawable.twitter)
    }

    override fun onPrev() {

    }

    override fun onNext() {

    }




}



