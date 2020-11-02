package edu.nitt.vortex21.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.squareup.picasso.Picasso
import edu.nitt.vortex21.R
import edu.nitt.vortex21.databinding.FragmentStoryBinding
import edu.nitt.vortex21.helpers.viewLifecycle
import jp.shts.android.storiesprogressview.StoriesProgressView.StoriesListener


class StoryFragment(val storyViewListener: ViewPagerFragment.storyViewListener) : Fragment(), StoriesListener {




    private var binding by viewLifecycle<FragmentStoryBinding>()

    private var imagesList:List<String>? = null
    private var storyids:List<String>? = null
    private var counter=0
    private var pressTime = 0L
    private var limit = 500L
    private val onTouchListener = View.OnTouchListener {view, motionEvent ->
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
        (activity as AppCompatActivity).supportActionBar?.hide()
        Log.i("MyTagPagerItemCreate",requireArguments().getInt("position").toString())
         return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //requireActivity().setTitle("Story")

     val navHostFragment = this.parentFragment?.parentFragment as NavHostFragment
       val parent = navHostFragment.parentFragment as HomeFragment
       parent.binding.bottomNavigation.visibility = View.INVISIBLE
        getStories(requireArguments().getInt("position"))
       binding.apply {
           reverse.setOnClickListener{
               Log.i("Touched","Reverse")
               binding.storiesProgress.reverse()
               Log.i("reverse",counter.toString())
           }
           reverse.setOnTouchListener(onTouchListener)

           skip.setOnClickListener{
               Log.i("Touched","Skip")
               Log.i("skip",counter.toString())

               binding.storiesProgress.skip()

           }
           skip.setOnTouchListener(onTouchListener)
       }

    }


    public fun getStories(position: Int){
        imagesList = ArrayList()
        storyids = ArrayList()
        (imagesList as ArrayList<String>).add("https://picsum.photos/id/1/600/700")
        (imagesList as ArrayList<String>).add("https://picsum.photos/id/2/600/700")
        (imagesList as ArrayList<String>).add("https://picsum.photos/id/3/600/700")
        binding.storiesProgress.setStoriesCount(imagesList!!.size)
        binding.storiesProgress.setStoryDuration(4000L)
        binding.storiesProgress.setStoriesListener(this)
        binding.storiesProgress.startStories(counter)
        Picasso.get().load(imagesList!![counter]).into(binding.imageStory)


    }

    override fun onComplete() {
       /* val navHostFragment = this.parentFragment?.parentFragment as NavHostFragment
        val parent = navHostFragment.parentFragment as HomeFragment
        parent.binding.bottomNavigation.visibility = View.VISIBLE
        (activity as AppCompatActivity).supportActionBar?.show()
       navHostFragment.navController.popBackStack(R.id.viewPagerFragment,true)*/
        Log.i("MyTagComplete",counter.toString())
        counter = 0
        storyViewListener.OnEndStory()

    }

    override fun onPrev() {
        if(counter>0) {
            Picasso.get().load(imagesList!![--counter]).placeholder(R.drawable.vortex_logo)
                .into(binding.imageStory)
            Log.i("MyTagPrev",counter.toString())
        }
        else{

            Log.i("MyTagPrev",counter.toString())
            counter = 0
            storyViewListener.onPrevStory()
        }
    }

    override fun onNext() {
        if(counter<imagesList!!.size) {
            Picasso.get().load(imagesList!![++counter]).placeholder(R.drawable.vortex_logo)
                .into(binding.imageStory)
            Log.i("MyTagNext",counter.toString())
        }

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



