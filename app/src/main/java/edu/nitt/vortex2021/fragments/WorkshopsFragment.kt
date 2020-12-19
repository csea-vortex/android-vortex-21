package edu.nitt.vortex2021.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.nitt.vortex2021.R
import edu.nitt.vortex2021.databinding.FragmentWorkshopsBinding
import edu.nitt.vortex2021.helpers.viewLifecycle


class WorkshopsFragment : Fragment() {

    private var binding by viewLifecycle<FragmentWorkshopsBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkshopsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setTitle(R.string.workshops)
    }
}