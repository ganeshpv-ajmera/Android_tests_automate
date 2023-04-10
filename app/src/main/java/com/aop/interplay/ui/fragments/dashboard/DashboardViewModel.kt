package com.aop.interplay.ui.fragments.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aop.interplay.network.remote.NetworkRepository
import com.aop.interplay.ui.fragments.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private var networkRepository: NetworkRepository
) : BaseViewModel(networkRepository) {

    private val _fullScreenView: MutableLiveData<Boolean> = MutableLiveData()
    val fullScreenView: LiveData<Boolean> = _fullScreenView

    fun enableFullScreen() {
        _fullScreenView.value = true
    }

    fun disableFullScreen() {
        _fullScreenView.value = false
    }
}