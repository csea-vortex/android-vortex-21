package edu.nitt.vortex2021

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import edu.nitt.vortex2021.databinding.ActivityMainBinding
import edu.nitt.vortex2021.helpers.viewLifecycle

class MainActivity : AppCompatActivity() {

    private val binding by viewLifecycle(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}