package com.aop.interplay.data

import com.aop.interplay.data.network.HomePost
import com.google.android.exoplayer2.ExoPlayer

data class HomePostListItem(
    val homePost: HomePost,
    var view: ExoPlayer? = null
)
