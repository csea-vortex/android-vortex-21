package edu.nitt.vortex.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import edu.nitt.vortex.databinding.FragmentRegisterBinding
import edu.nitt.vortex.helpers.Constants
import edu.nitt.vortex.helpers.viewLifecycle

class RegisterFragment : Fragment() {

    private var binding by viewLifecycle<FragmentRegisterBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val departmentAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            Constants.departments
        )
        binding.autocompleteTextDepartment.setAdapter(departmentAdapter)

        val yearOfStudyAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            Constants.yearOfStudy
        )
        binding.autocompleteTextYearOfStudy.setAdapter(yearOfStudyAdapter)

    }

}