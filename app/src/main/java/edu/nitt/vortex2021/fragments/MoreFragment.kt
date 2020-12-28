package edu.nitt.vortex2021.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.google.android.material.chip.Chip
import edu.nitt.vortex2021.BaseApplication
import edu.nitt.vortex2021.MainActivity
import edu.nitt.vortex2021.R
import edu.nitt.vortex2021.databinding.FragmentMoreBinding
import edu.nitt.vortex2021.helpers.*
import edu.nitt.vortex2021.viewmodel.AuthViewModel
import edu.nitt.vortex2021.viewmodel.UserViewModel


class MoreFragment : Fragment() {

    private var binding by viewLifecycle<FragmentMoreBinding>()

    private lateinit var userViewModel: UserViewModel
    private lateinit var authViewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoreBinding.inflate(inflater, container, false)
        initGradientBackgroundAnimation(binding.root)
        initViewModels()
        initSocialMediaChips()
        initButtons()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel.sendUserDetailsRequest()
    }

    private fun initViewModels() {
        val factory = (requireActivity().application as BaseApplication)
            .applicationComponent
            .getViewModelProviderFactory()

        userViewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
        authViewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        observeLiveData()
    }

    private fun observeLiveData() {
        userViewModel.userDetailsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    val user = response.data!!.data
                    binding.name.text = user.name
                    binding.email.text = user.email
                    binding.mobileNumber.text = user.mobile.toString()
                    binding.containerUserDetails.visibility = View.VISIBLE
                    binding.resendTokenButton.visibility = if (user.isVerified) View.GONE else View.VISIBLE
                }
                is Resource.Error -> {
                    showToastMessage(requireContext(), response.message)
                }
            }
        }

        authViewModel.resendVerificationTokenResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    showToastMessage(requireContext(), response.data!!.message)
                    userViewModel.sendUserDetailsRequest()
                }
                is Resource.Error -> {
                    showToastMessage(requireContext(), response.message)
                }
            }
        }
    }

    private fun initSocialMediaChips() {
        for (socialMediaLink in Constants.SOCIAL_MEDIA_LINKS) {
            val chip =
                layoutInflater.inflate(R.layout.list_item_social_media, binding.socialMediaChipGroup, false) as Chip
            chip.apply {
                text = socialMediaLink.name
                setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(socialMediaLink.url))
                    requireContext().startActivity(intent)
                }
                chipIcon = ResourcesCompat.getDrawable(resources, socialMediaLink.iconId, null)
                binding.socialMediaChipGroup.addView(this)
            }
        }
    }

    private fun initButtons() {
        binding.logoutButton.setOnClickListener {
            val userTokenStore = UserTokenStore(requireContext())
            userTokenStore.token = ""
            // ToDo: Send Logout Request

            requireActivity().apply {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.resendTokenButton.setOnClickListener {
            authViewModel.resendVerificationToken()
        }
    }

}