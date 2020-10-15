package edu.nitt.vortex.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import edu.nitt.vortex.R
import edu.nitt.vortex.databinding.FragmentHomeBinding
import edu.nitt.vortex.helpers.viewLifecycle


class HomeFragment : Fragment() {

    public var binding by viewLifecycle<FragmentHomeBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment = childFragmentManager
            .findFragmentById(R.id.home_nav_host_fragment) as NavHostFragment

        binding.bottomNavigation.apply {
            // ToDo: If t-shirt not registered then enable
            //  the T-Shirt Registration Menu Item
            menu.getItem(3).isVisible = true

            setupWithNavController(navHostFragment.navController)
        }

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

    public fun hideNavBar() {
        binding.bottomNavigation.visibility = View.INVISIBLE
    }

}