package edu.nitt.vortex21.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.android.material.chip.Chip
import edu.nitt.vortex21.MainActivity
import edu.nitt.vortex21.R
import edu.nitt.vortex21.databinding.FragmentMoreBinding
import edu.nitt.vortex21.helpers.Constants
import edu.nitt.vortex21.helpers.viewLifecycle


class MoreFragment : Fragment() {

    private var binding by viewLifecycle<FragmentMoreBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoreBinding.inflate(inflater, container, false)
        initSocialMediaChips()
        initLogoutButton()
        return binding.root
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

    private fun initLogoutButton() {
        binding.logoutButton.setOnClickListener {
            val context = requireActivity().applicationContext
            val masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            EncryptedSharedPreferences.create(
                context,
                Constants.encryptedSharedPreferencesName,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            ).edit {
                putString("token", "")
                putString("username", "")
                commit()
            }

            requireActivity().apply {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

}