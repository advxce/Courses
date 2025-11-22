package com.example.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.presentation.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel

class MainViewModel : ViewModel() {

    private val _selectedTab = MutableLiveData(MainActivity.Tab.HOME)
    val selectedTab: LiveData<MainActivity.Tab> = _selectedTab

    fun selectTab(tab: MainActivity.Tab) {
        _selectedTab.value = tab
    }
}