package edu.nitt.vortex.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.nitt.vortex.databinding.FragmentLoginBinding
import edu.nitt.vortex.helpers.Validators
import edu.nitt.vortex.helpers.viewLifecycle

class LoginFragment : Fragment() {

    private var binding by viewLifecycle<FragmentLoginBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
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
            if (password.length < 8) {
                allOk = false
                binding.containerPassword.error = "Password should have at least 8 characters"
            }

            if (allOk) {
                // ToDo: Check if credentials are ok (send network-request)
                //  if yes then login else show appropriate error message

                Toast.makeText(
                    requireContext(),
                    "You may get logged in :)",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.buttonRegister.setOnClickListener {
            // ToDo: Add fragment change transition/animation
            findNavController().navigate(LoginFragmentDirections.actionFragmentLoginToFragmentRegister())
        }
    }

}