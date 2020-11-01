package edu.nitt.vortex21.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import edu.nitt.vortex21.databinding.FragmentMainBinding
import edu.nitt.vortex21.helpers.Constants
import edu.nitt.vortex21.helpers.viewLifecycle

class MainFragment : Fragment() {
    private var binding by viewLifecycle<FragmentMainBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // This Fragment acts a bridge to home or login
        // We land on this fragment from the Splash Activity
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
        ).apply {
            val username = getString("username", "")!!
            if (username.isNotEmpty()) {
                Log.i("MainFragment", username)
                findNavController().navigate(MainFragmentDirections.actionFragmentMainToFragmentHome())
            } else {
                findNavController().navigate(MainFragmentDirections.actionFragmentMainToFragmentLogin())
            }
        }
    }
}