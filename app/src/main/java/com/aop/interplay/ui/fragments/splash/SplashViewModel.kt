package com.aop.interplay.ui.fragments.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aop.interplay.data.HomePostListItem
import com.aop.interplay.network.remote.NetworkRepository
import com.aop.interplay.ui.fragments.BaseViewModel
import com.aop.interplay.ui.fragments.dashboard.home.HomeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val networkRepository: NetworkRepository
) : BaseViewModel(networkRepository) {

    private val _content: MutableLiveData<List<HomePostListItem>> = MutableLiveData()
    val content: LiveData<List<HomePostListItem>> = _content

    fun getPosts() {
        viewModelScope.launch {
            networkRepository.getPosts(page = 1, limit = HomeViewModel.PAGE_SIZE)
                .collectLatest { values ->
                    values.data?.let {
                        _content.value = it.homeScreenPosts.posts.map { HomePostListItem(it) }
                    }
                }
        }
    }
}
