package com.aop.interplay.ui.fragments

import androidx.lifecycle.ViewModel
import com.aop.interplay.network.remote.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(
    private val networkRepository: NetworkRepository
) : ViewModel()