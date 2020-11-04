package edu.nitt.vortex21.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import edu.nitt.vortex21.R
import edu.nitt.vortex21.databinding.FragmentViewPagerBinding
import edu.nitt.vortex21.helpers.viewLifecycle


class ViewPagerFragment : Fragment() {

    var binding by viewLifecycle<FragmentViewPagerBinding>()
    private var stories:Int = 5
    private var pagerAdapter:FragmentStateAdapter? = null

    interface storyViewListener{
        abstract fun OnEndStory(position: Int)
        abstract fun onPrevStory()
    }

    val storyChangeListener = object : storyViewListener{
        override fun OnEndStory(position: Int) {
            onComplete(position)
        }

        override fun onPrevStory() {
            onPrev()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagerAdapter = ScreenSlidePagerAdapter(this)
        binding.pager.adapter = pagerAdapter

    }

    private inner class ScreenSlidePagerAdapter(fa: Fragment) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = stories
        override fun createFragment(position: Int): Fragment {
            val fragment = StoryFragment(storyChangeListener)
            fragment.arguments = Bundle().apply {
                // Our object is just an integer :-P
                putInt("position", position)
            }
            return fragment
        }
    }

    private fun onComplete(position: Int){
        if(binding.pager.currentItem < pagerAdapter!!.itemCount-1) {
            binding.pager.currentItem++
            Log.i("MyTagPagerCurrentItem", binding.pager.currentItem.toString())

        }
        else{
            val navHostFragment = this.parentFragment as NavHostFragment
            val parent = navHostFragment.parentFragment as HomeFragment
            parent.binding.bottomNavigation.visibility = View.VISIBLE
            (activity as AppCompatActivity).supportActionBar?.show()
            navHostFragment.navController.popBackStack(R.id.viewPagerFragment,true)
        }
    }
    private fun onPrev(){
        if(binding.pager.currentItem > 0){
            binding.pager.currentItem = binding.pager.currentItem -1
            Log.i("MyTagPagerCurrentItem", binding.pager.currentItem.toString())
           // counter = 0
        }
    }


}