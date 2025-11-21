package com.example.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.presentation.databinding.FragmentAccountBinding
import com.example.presentation.databinding.FragmentFavoritesBinding
import com.example.presentation.databinding.FragmentLoginBinding

class FavoritesFragment: Fragment() {

    private var binding: FragmentFavoritesBinding?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object{
        fun newInstance(): FavoritesFragment {
            val loginFragment = FavoritesFragment().apply {
                arguments = Bundle().apply {

                }
            }

            return loginFragment
        }
    }

}