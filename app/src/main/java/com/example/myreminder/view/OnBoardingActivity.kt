package com.example.myreminder.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.example.myreminder.databinding.ActivityOnBoardingBinding
import com.example.myreminder.utils.FragmentCommunicator
import androidx.navigation.findNavController
import androidx.navigation.ui.navigateUp
import android.view.View
import com.example.myreminder.R

class OnBoardingActivity : AppCompatActivity(), FragmentCommunicator {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun showLoader(value: Boolean) {
        binding.loaderContainerView.visibility = if (value) View.VISIBLE else View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}