package com.aop.interplay.ui.fragments.dashboard.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aop.interplay.data.HomePostListItem
import com.aop.interplay.network.remote.NetworkRepository
import com.aop.interplay.ui.fragments.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val networkRepository: NetworkRepository
) : BaseViewModel(networkRepository) {

    private var currentPage: Int = 2

    private val _content: MutableLiveData<List<HomePostListItem>> = MutableLiveData()
    val content: LiveData<List<HomePostListItem>> = _content

    fun getPosts(loadFromStart: Boolean = false) {
        if (loadFromStart) {
            currentPage = 1
        }
        viewModelScope.launch {
            networkRepository.getPosts(page = currentPage, limit = PAGE_SIZE)
                .collectLatest { values ->
                    values.data?.let {
                        _content.value = it.homeScreenPosts.posts.map { HomePostListItem(it) }
                        currentPage++
                    }
                }
        }
    }

    companion object {
        const val PAGE_SIZE = 10
    }
}