package edu.nitt.vortex21.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import edu.nitt.vortex21.R
import edu.nitt.vortex21.databinding.FragmentLoginBinding
import edu.nitt.vortex21.helpers.Constants
import edu.nitt.vortex21.helpers.Validators
import edu.nitt.vortex21.helpers.viewLifecycle
import edu.nitt.vortex21.helpers.Resource
import edu.nitt.vortex21.model.LoginRequest
import edu.nitt.vortex21.viewmodel.AuthViewModel

class LoginFragment : Fragment() {

    private var binding by viewLifecycle<FragmentLoginBinding>()
    private val viewModel: AuthViewModel by lazy {
        ViewModelProvider(this).get(AuthViewModel::class.java)
    }
    private lateinit var loginRequest: LoginRequest

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        observeLiveData()
        return binding.root
    }

    private fun observeLiveData() {
        viewModel.loginResponse.observe(viewLifecycleOwner) {response ->
            when(response) {
                is Resource.Success -> {
                    saveToken(response.data!!.token)
                    hideProgressBar()
                    Toast.makeText(requireContext(), "Logged in User", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(LoginFragmentDirections.actionFragmentLoginToFragmentMain())
                }
                is Resource.Error -> {
                    hideProgressBar()
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun saveToken(token: String) {
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
            putString("token", token)
            putString("username", loginRequest.username)
            commit()
        }
    }

    private fun hideProgressBar() {
        binding.progressBarLogin.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressBarLogin.visibility = View.VISIBLE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setTitle(R.string.login)

        mapOf(
            binding.editTextUsername to binding.containerUsername,
            binding.editTextPassword to binding.containerPassword,
        ).forEach {
            it.key.addTextChangedListener { text ->
                if (text.toString().isNotEmpty()) {
                    it.value.error = null
                }
            }
        }

        binding.buttonLogin.setOnClickListener {
            var allOk = true

            val username = binding.editTextUsername.text.toString()
            if (username.isEmpty() or !Validators.isAlphaNumeric(username)) {
                allOk = false
                binding.containerUsername.error = "Vortex Username should be alphanumeric with no spaces"
            }

            val password = binding.editTextPassword.text.toString()
            if (password.length < 8) {
                allOk = false
                binding.containerPassword.error = "Password should have at least 8 characters"
            }

            if (allOk) {
                loginRequest = LoginRequest(username, password)
                showProgressBar()
                viewModel.sendLoginRequest(loginRequest)
            }
        }

        binding.buttonRegister.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionFragmentLoginToFragmentRegister())
        }
    }

}