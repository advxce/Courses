package com.example.presentation.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle

import com.example.presentation.MainActivity
import com.example.presentation.R

import com.example.presentation.databinding.FragmentLoginBinding
import com.example.presentation.viewModels.LoginViewModel
import kotlinx.coroutines.launch
import androidx.core.net.toUri

class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null
    private val loginViewModel: LoginViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        setupEditTexts()
        setupObservers()
        setupButtons()

    }

    fun setupEditTexts(){
        withBinding {
            passwordEditTxt.addTextChangedListener(object: TextWatcher{
                override fun afterTextChanged(p: Editable?) {

                    loginViewModel.onPasswordChanged(p.toString())
                }

                override fun beforeTextChanged(
                    p0: CharSequence?,
                    p1: Int,
                    p2: Int,
                    p3: Int
                ) {

                }

                override fun onTextChanged(
                    p0: CharSequence?,
                    p1: Int,
                    p2: Int,
                    p3: Int
                ) {

                }

            })

            emailEditTxt.addTextChangedListener(object: TextWatcher{
                override fun afterTextChanged(p: Editable?) {

                    loginViewModel.onEmailChanged(p.toString())
                }

                override fun beforeTextChanged(
                    p0: CharSequence?,
                    p1: Int,
                    p2: Int,
                    p3: Int
                ) {

                }

                override fun onTextChanged(
                    p0: CharSequence?,
                    p1: Int,
                    p2: Int,
                    p3: Int
                ) {

                }

            })
        }
    }

    fun setupObservers(){
        withBinding {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED){
                    loginViewModel.loginState.collect { loginState->

                        if(emailEditTxt.text.isNullOrEmpty()){
                            emailEditTxt.setText(loginState.email)
                        }


                        if(passwordEditTxt.text.isNullOrEmpty()){
                            passwordEditTxt.setText(loginState.password)
                        }

                        enterBtn.isEnabled = loginState.isActive

                        val colorRes = if (loginState.isActive) {
                            R.color.app_green
                        } else {
                            R.color.text_color
                        }

                        enterBtn.backgroundTintList =
                            ContextCompat.getColorStateList(requireContext(), colorRes)

                    }
                }
            }
        }
    }

    fun setupButtons(){
        withBinding {
            enterBtn.setOnClickListener {
                val mainActivityIntent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(mainActivityIntent)
            }

            classmatesButton.setOnClickListener {
                try {
                    val classmatesIntent = Intent(Intent.ACTION_VIEW, "https://ok.ru/".toUri())
                    startActivity(classmatesIntent)
                }
                catch (e: Exception){
                    Toast.makeText(requireActivity(), "Cant find this website", Toast.LENGTH_SHORT).show()
                }
            }

            vkButton.setOnClickListener {
                try {
                    val vkIntent = Intent(Intent.ACTION_VIEW, "https://vk.com/".toUri())
                    startActivity(vkIntent)
                }
                catch (e: Exception){
                    Toast.makeText(requireActivity(), "Cant find this website", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun withBinding(block: FragmentLoginBinding.()->Unit){
        binding?.let {
            block.invoke(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}