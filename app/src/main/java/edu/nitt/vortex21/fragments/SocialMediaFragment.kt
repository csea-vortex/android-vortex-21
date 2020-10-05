package edu.nitt.vortex21.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import edu.nitt.vortex21.R
import edu.nitt.vortex21.adapters.SocialMediaLinksAdapter
import edu.nitt.vortex21.databinding.FragmentSocialMediaBinding
import edu.nitt.vortex21.helpers.viewLifecycle
import edu.nitt.vortex21.model.SocialMediaLink


class SocialMediaFragment : Fragment() {

    private var binding by viewLifecycle<FragmentSocialMediaBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSocialMediaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setTitle(R.string.social_media)

        binding.recyclerViewLinks.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = SocialMediaLinksAdapter(
                requireContext(),
                arrayOf(
                    SocialMediaLink(
                        "Vortex Website",
                        R.drawable.vortex_logo,
                        "https://vortex.nitt.edu/"
                    ),
                    SocialMediaLink(
                        "Facebook",
                        R.drawable.facebook,
                        "https://www.facebook.com/vortex.nitt/"
                    ),
                    SocialMediaLink(
                        "Instagram",
                        R.drawable.instagram,
                        "https://instagram.com/vortex_nitt?igshid=7cduw1t8j1k"
                    ),
                    SocialMediaLink("Twitter", R.drawable.twitter, ""),
                    SocialMediaLink("LinkedIn", R.drawable.linkedin, ""),
                )
            )
        }
    }
}