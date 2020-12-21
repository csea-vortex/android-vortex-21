package edu.nitt.vortex2021.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import edu.nitt.vortex2021.BaseApplication
import edu.nitt.vortex2021.R
import edu.nitt.vortex2021.databinding.FragmentRegisterBinding
import edu.nitt.vortex2021.helpers.*
import edu.nitt.vortex2021.model.RegisterRequest
import edu.nitt.vortex2021.viewmodel.AuthViewModel
import edu.nitt.vortex2021.viewmodel.DataViewModel

class RegisterFragment : Fragment() {

    private var binding by viewLifecycle<FragmentRegisterBinding>()
    private lateinit var authViewModel: AuthViewModel
    private lateinit var dataViewModel: DataViewModel

    private val colleges = ArrayList<String>()

    private lateinit var collegeAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        initViewModels()
        initGradientBackgroundAnimation(binding.root)
        return binding.root
    }

    private fun initViewModels() {
        val factory = (requireActivity().application as BaseApplication)
            .applicationComponent
            .getViewModelProviderFactory()

        authViewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        dataViewModel = ViewModelProvider(this, factory).get(DataViewModel::class.java)
        observeLiveData()
    }

    private fun observeLiveData() {
        authViewModel.registerResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    Toast.makeText(requireContext(), "Registered User", Toast.LENGTH_SHORT).show()
                    requireActivity().onBackPressed()
                }
                is Resource.Error -> {
                    hideProgressBar()
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        dataViewModel.collegesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    // ToDo: Populate the college text field with list of colleges
                    collegeAdapter.clear()
                    collegeAdapter.addAll(response.data!!.collegeList.map { college -> college.name })
                    collegeAdapter.notifyDataSetChanged()
                }
            }
        }

    }

    private fun hideProgressBar() {
        binding.progressBarRegister.visibility = View.INVISIBLE
        binding.buttonRegister.isEnabled = true
    }

    private fun showProgressBar() {
        binding.progressBarRegister.visibility = View.VISIBLE
        binding.buttonRegister.isEnabled = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setTitle(R.string.register)

        dataViewModel.fetchCollegeList()
        setupAutoCompleteDropdownViews()

        mapOf(
            binding.editTextName to binding.containerName,
            binding.editTextUsername to binding.containerUsername,
            binding.editTextPassword to binding.containerPassword,
            binding.editTextConfirmPassword to binding.containerConfirmPassword,
            binding.editTextNumber to binding.containerNumber,
            binding.editTextEmail to binding.containerEmail,
            binding.autocompleteTextCollege to binding.containerCollege,
            binding.autocompleteTextDepartment to binding.containerDepartment,
        ).forEach {
            it.key.addTextChangedListener { text ->
                if (text.toString().isNotEmpty()) {
                    it.value.error = null
                }
            }
        }

        binding.buttonRegister.setOnClickListener {
            // Get all the details
            var allOk = true

            val name = binding.editTextName.text.toString()
            if (name.isEmpty() or !Validators.containsAlphabets(name)) {
                allOk = false
                binding.containerName.error = "Name should contain only alphabets"
            }

            val username = binding.editTextUsername.text.toString()
            if (username.isEmpty() or !Validators.isAlphaNumeric(username)) {
                allOk = false
                binding.containerUsername.error = "Vortex Username should be alphanumeric with no spaces"
            }

            val password = binding.editTextPassword.text.toString()
            val confirmPassword = binding.editTextConfirmPassword.text.toString()
            if (password.isEmpty() or (password != confirmPassword)) {
                allOk = false
                val error = "Both password fields should be the same and non-empty"
                binding.containerPassword.error = error
                binding.containerConfirmPassword.error = error
            } else if (!Validators.isStrongPassword(password)) {
                allOk = false
                binding.containerPassword.error =
                    "Password must contain at least 8 characters in total, at least 1 number, 1 small and capital alphabet and a special character"
            }

            val number = binding.editTextNumber.text.toString()
            if (number.isEmpty() or !Validators.isPhoneNumber(number)) {
                allOk = false
                binding.containerNumber.error = "Invalid phone number"
            }

            val email = binding.editTextEmail.text.toString()
            if (email.isEmpty() or !Validators.isEmailValid(email)) {
                allOk = false
                binding.containerEmail.error = "Invalid email"
            }

            val college = binding.autocompleteTextCollege.text.toString()
            if (college.isEmpty()) {
                allOk = false
                binding.containerCollege.error = "College field cannot be empty"
            }

            val department = binding.autocompleteTextDepartment.text.toString()
            if (department.isEmpty()) {
                allOk = false
                binding.containerDepartment.error = "Enter your department"
            }

            if (allOk) {
                val registerRequest = RegisterRequest(
                    fullName = name,
                    username = username,
                    password = password,
                    mobileNumber = number,
                    email = email,
                    department = department,
                    college = college
                )
                showProgressBar()
                try {
                    authViewModel.sendRegisterRequest(registerRequest)
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_SHORT).show()
                    hideProgressBar()
                }
            }
        }
    }

    private fun setupAutoCompleteDropdownViews() {
        val departmentAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            Constants.departments
        )
        collegeAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            colleges
        )
        binding.autocompleteTextDepartment.setAdapter(departmentAdapter)
        binding.autocompleteTextCollege.setAdapter(collegeAdapter)
    }

}