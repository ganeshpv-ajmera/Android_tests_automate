package com.aop.interplay

import android.app.Activity
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.aop.interplay.data.FullScreenDialogType
import javax.inject.Inject

class NavigationManager @Inject constructor(
    private val activity: Activity
) {
    fun navigateToWebFragment(title: String, url: String) {
        getNavController().navigate(
            R.id.toWebFragment, bundleOf(
                "title" to title, "url" to url
            )
        )
    }

    fun navigateToDashboardFragment() {
        getNavController().navigate(
            R.id.toDashboardFragment
        )
    }

    fun navigateFullScreenDialogToDashboardFragment() {
        getNavController().navigate(
            R.id.full_screen_dialog_fragment_to_dashBoard_fragment
        )
    }

    fun navigateToFullScreenDialogFragment(
        title: String,
        message: String,
        ctaText1: String? = null,
        ctaText2: String? = null,
        fullScreenDialogType: FullScreenDialogType
    ) {
        getNavController().navigate(
            R.id.toFullScreenDialogFragment, bundleOf(
                "title" to title,
                "message" to message,
                "ctaText1" to ctaText1,
                "ctaText2" to ctaText2,
                "type" to fullScreenDialogType
            )
        )
    }

    private fun getNavController() =
        activity.findNavController(R.id.nav_host_fragment_activity_main)
}