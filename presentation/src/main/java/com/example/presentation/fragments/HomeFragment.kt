package com.example.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.presentation.BottomPaddingDecoration
import com.example.presentation.viewModels.CourseViewModel
import com.example.presentation.R
import com.example.presentation.adapter.CoursesAdapter
import com.example.presentation.data.CourseUI
import com.example.presentation.databinding.FragmentAccountBinding
import com.example.presentation.databinding.FragmentHomeBinding
import com.example.presentation.databinding.FragmentLoginBinding
import com.example.presentation.mapper.CourseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class HomeFragment: Fragment() {

    private var binding: FragmentHomeBinding?=null
    private val courseViewModel: CourseViewModel by viewModels()

    private val adapter by lazy {
        CoursesAdapter(
            onDetailsClick = { course ->
                Toast.makeText(
                    requireContext(),
                    "${course}",
                    Toast.LENGTH_SHORT
                ).show()            },
            onBookmarkClick = { course ->
                courseViewModel.updateCourses(course)
            },
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    fun Int.dpToPx(context: Context): Int = (this * context.resources.displayMetrics.density).toInt()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { bind->
            bind.recView.adapter = adapter
            bind.recView.layoutManager = LinearLayoutManager(requireContext())
            bind.recView.addItemDecoration(BottomPaddingDecoration(100.dpToPx(requireContext())))
        }

        courseViewModel.getAllCourses()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                courseViewModel.state.collect { state->
                    when(state){
                        is CourseState.Loading ->{
                            showLoading()
                        }
                        is CourseState.Success ->{
                            hideLoading()
                            showCourses(state.courses)
                        }
                        is CourseState.Error ->{
                            hideLoading()
                            showError(state.message)
                        }
                    }
                }
            }
        }


//        courseViewModel.coursesLiveData.observe(viewLifecycleOwner) {courseUiMapper->
//
//            adapter.items = courseUiMapper
//            adapter.notifyDataSetChanged()
//        }


    }

    private fun showCourses(courses: List<CourseUI>) {
        adapter.items = courses
        adapter.notifyDataSetChanged()
    }
    private fun showLoading() {
        binding?.progressBar?.visibility = View.VISIBLE
        binding?.recView?.visibility = View.GONE
    }

    private fun hideLoading() {
        binding?.progressBar?.visibility = View.GONE
        binding?.recView?.visibility = View.VISIBLE
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object{
        fun newInstance(): HomeFragment {
            val loginFragment = HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }

            return loginFragment
        }
    }

}