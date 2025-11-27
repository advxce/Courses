package com.example.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.presentation.MainActivity


class MainViewModel : ViewModel() {

    private val _selectedTab = MutableLiveData(MainActivity.Tab.HOME)
    val selectedTab: LiveData<MainActivity.Tab> = _selectedTab

    fun selectTab(tab: MainActivity.Tab) {
        _selectedTab.value = tab
    }
}