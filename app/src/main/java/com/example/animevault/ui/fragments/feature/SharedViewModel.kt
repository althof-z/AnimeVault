package com.example.animevault.ui.fragments.feature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _selectedNavItemId = MutableLiveData<Int>()
    val selectedNavItemId: LiveData<Int> get() = _selectedNavItemId

    fun setSelectedNavItemId(itemId: Int) {
        _selectedNavItemId.value = itemId
    }
}
