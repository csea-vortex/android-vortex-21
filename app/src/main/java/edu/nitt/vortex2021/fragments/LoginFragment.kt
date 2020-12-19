package edu.nitt.vortex2021.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import edu.nitt.vortex2021.BaseApplication
import edu.nitt.vortex2021.databinding.FragmentLoginBinding
import edu.nitt.vortex2021.helpers.Resource
import edu.nitt.vortex2021.helpers.Validators
import edu.nitt.vortex2021.helpers.initGradientBackgroundAnimation
import edu.nitt.vortex2021.helpers.viewLifecycle
import edu.nitt.vortex2021.model.LoginRequest
import edu.nitt.vortex2021.viewmodel.AuthViewModel

class LoginFragment : Fragment() {

    private var binding by viewLifecycle<FragmentLoginBinding>()

    private lateinit var viewModel: AuthViewModel
    private lateinit var loginRequest: LoginRequest

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        initViewModel()
        initGradientBackgroundAnimation(binding.root)
        return binding.root
    }

    private fun initViewModel() {
        val factory = (requireActivity().application as BaseApplication)
            .applicationComponent
            .getViewModelProviderFactory()

        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.loginResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    findNavController().navigate(LoginFragmentDirections.actionFragmentLoginToFragmentMain())
                }
                is Resource.Error -> {
                    hideProgressBar()
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun hideProgressBar() {
        binding.progressBarLogin.visibility = View.INVISIBLE
        binding.buttonLogin.isEnabled = true
    }

    private fun showProgressBar() {
        binding.progressBarLogin.visibility = View.VISIBLE
        binding.buttonLogin.isEnabled = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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
            if (password.isEmpty()) {
                allOk = false
                binding.containerPassword.error = "Required Field"
            }

            if (allOk) {
                loginRequest = LoginRequest(username, password)
                showProgressBar()
                try {
                    viewModel.sendLoginRequest(loginRequest)
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_SHORT).show()
                    hideProgressBar()
                }
            }
        }

        binding.buttonRegister.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionFragmentLoginToFragmentRegister())
        }
    }

}