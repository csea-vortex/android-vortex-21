package edu.nitt.vortex21.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import edu.nitt.vortex21.adapters.StoryHolderAdapter
import edu.nitt.vortex21.databinding.FragmentStoryHolderBinding
import edu.nitt.vortex21.helpers.CubeTransformer
import edu.nitt.vortex21.helpers.viewLifecycle
import edu.nitt.vortex21.model.Stories

class StoryHolderFragment : Fragment() {

    private var binding by viewLifecycle<FragmentStoryHolderBinding>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentStoryHolderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val storyIdx = requireArguments().getInt("storyIdx")
        val stories = requireArguments().getParcelable<Stories>("stories")!!
        val navHostFragment = this.parentFragment as NavHostFragment
        val parent = navHostFragment.parentFragment as HomeFragment
        parent.binding.bottomNavigation.visibility = View.INVISIBLE

        binding.storyViewPager.apply {
            adapter = StoryHolderAdapter(
                childFragmentManager,
                stories
            ) { position ->
                val nextPosition = position + 1
                if (nextPosition == stories.size) {
                    findNavController().popBackStack()
                } else {
                    setCurrentItem(nextPosition, true)
                }
            }
            setCurrentItem(storyIdx, false)
            setPageTransformer(true, CubeTransformer())
        }
    }

}