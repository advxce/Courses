package com.example.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

import com.example.presentation.MainActivity

import com.example.presentation.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null



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

        binding?.let { bind ->
            bind.enterBtn.setOnClickListener {
                val mainActivityIntent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(mainActivityIntent)
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        fun newInstance(): LoginFragment {
            val loginFragment = LoginFragment().apply {
                arguments = Bundle().apply {

                }
            }

            return loginFragment
        }
    }

}