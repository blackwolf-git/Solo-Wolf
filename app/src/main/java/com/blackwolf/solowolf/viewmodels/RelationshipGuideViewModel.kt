package com.blackwolf.solowolf.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blackwolf.solowolf.data.DataRepository
import com.blackwolf.solowolf.models.WomanProfile
import kotlinx.coroutines.launch

class RelationshipGuideViewModel(private val repository: DataRepository) : ViewModel() {

    private val _womanProfile = MutableLiveData<WomanProfile?>()
    val womanProfile: LiveData<WomanProfile?> = _womanProfile

    fun loadWomanProfile(type: String) {
        viewModelScope.launch {
            val profile = repository.getWomanProfileByType(type)
            _womanProfile.postValue(profile)
        }
    }
}
