package com.aop.interplay.ui.fragments.signup.signupmobileverifyotp

import com.aop.interplay.network.remote.NetworkRepository
import com.aop.interplay.ui.fragments.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpMobileVerifyOtpViewModel @Inject constructor(
    private val networkRepository: NetworkRepository
) : BaseViewModel(networkRepository)