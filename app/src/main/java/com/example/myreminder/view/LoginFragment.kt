package com.example.myreminder.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.myreminder.ListActivity
import com.example.myreminder.R
import com.example.myreminder.databinding.FragmentLoginBinding
import com.example.myreminder.viewModel.LoginViewModel
import com.example.myreminder.utils.FragmentCommunicator
import com.example.myreminder.view.OnBoardingActivity


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!
    var isValid: Boolean = false
    private lateinit var communicator: FragmentCommunicator
    private val viewModel by viewModels<LoginViewModel>()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        communicator = requireActivity() as OnBoardingActivity

        setupView()
        setupObservers()
        return binding.root
    }


    private fun setupView() {
        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btLogIn.setOnClickListener {
            if (isValid) {
                requestLogin()
            } else {
                Toast.makeText(activity, "Ingrese todos los datos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.etEmail.addTextChangedListener {
            if (binding.etEmail.text.toString().isEmpty()) {
                binding.tfEmail.error = "Por favor introduce un correo"
                isValid = false
            } else {
                isValid = true
            }
        }
        binding.etPassword.addTextChangedListener {
            if (binding.etPassword.text.toString().isEmpty()) {
                binding.tfPassword.error = "Por favor introduce una contraseña"
                isValid = false
            } else {
                isValid = true
            }
        }
    }

    private fun setupObservers() {
        viewModel.loaderState.observe(viewLifecycleOwner) { loaderState ->
            communicator.showLoader(loaderState)
        }
        viewModel.sessionValid.observe(viewLifecycleOwner) { validSession ->
            if (validSession) {
                val intent = Intent(activity, ListActivity::class.java)
                startActivity(intent)
                activity?.finish()
            } else {
                Toast.makeText(activity, "Ingreso invalido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun requestLogin() {
        viewModel.requestSignIn(binding.etEmail.text.toString(),
            binding.etPassword.text.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}