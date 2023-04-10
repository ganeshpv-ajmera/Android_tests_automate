package com.aop.interplay.listeners

interface VideoInteractionListener {
    fun onStarClicked(isEnabled: Boolean)
    fun onBookmarkClicked(isEnabled: Boolean)
    fun onShareClicked()
    fun onProfileClicked()
    fun onVideoClicked(position: Int)
    fun onLearnClicked()
    fun onTryClicked()
    fun onDescriptionExpanded()
    fun onDescriptionCollapsed()
}