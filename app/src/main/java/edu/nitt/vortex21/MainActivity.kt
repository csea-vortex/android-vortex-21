package edu.nitt.vortex21

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import edu.nitt.vortex21.databinding.ActivityMainBinding
import edu.nitt.vortex21.helpers.viewLifecycle

class MainActivity : AppCompatActivity() {

    private val binding by viewLifecycle(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}