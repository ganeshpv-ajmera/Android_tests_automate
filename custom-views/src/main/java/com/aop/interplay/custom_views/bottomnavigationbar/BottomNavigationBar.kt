package com.aop.interplay.custom_views.bottomnavigationbar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.aop.interplay.custom_views.R

class BottomNavigationBar(
    context: Context?, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val fragments: MutableList<Fragment> = mutableListOf()
    private var fragmentManager: FragmentManager? = null
    private var parentFragmentManager: FragmentManager? = null
    private var containerFragment: Int? = null
    private var parentContainerFragment:Int? = null
    private var currentSelectedPosition = -1

    init {
        inflate(context, R.layout.bottom_navigation_bar_layout, this)

        findViewById<View>(R.id.nav_home_clickable).setOnClickListener {
            loadHomeFragment()
        }

        findViewById<View>(R.id.nav_discover_clickable).setOnClickListener {
            //loadDiscoverFragment()
        }

        findViewById<View>(R.id.nav_new_post_clickable).setOnClickListener {
            // loadNewPostFragment()
        }

        findViewById<View>(R.id.nav_course_clickable).setOnClickListener {
            //loadCourseFragment()
        }

        findViewById<View>(R.id.nav_profile_clickable).setOnClickListener {
            loadProfileFragment()
        }

        postDelayed({
            loadFirstFragment()
        }, 500)
    }

    private fun loadHomeFragment() {
        currentSelectedPosition = 1
        containerFragment?.let {
            val tran = fragmentManager?.beginTransaction()
            tran?.replace(it, fragments[HOME_INDEX])
            tran?.commitNow()
        }
    }

    private fun loadDiscoverFragment() {
        currentSelectedPosition = 2
        containerFragment?.let {
            val tran = fragmentManager?.beginTransaction()
            tran?.replace(it, fragments[DISCOVER_INDEX])
            tran?.commitNow()
        }
    }

    private fun loadNewPostFragment() {
        currentSelectedPosition = 3
        containerFragment?.let {
            val tran = fragmentManager?.beginTransaction()
            tran?.replace(it, fragments[NEW_POST_INDEX])
            tran?.commitNow()
        }
    }

    private fun loadCourseFragment() {
        currentSelectedPosition = 4
        containerFragment?.let {
            val tran = fragmentManager?.beginTransaction()
            tran?.replace(it, fragments[COURSE_INDEX])
            tran?.commitNow()
        }
    }

    private fun loadProfileFragment() {
        currentSelectedPosition = 5
        parentContainerFragment?.let {
            val tran = parentFragmentManager?.beginTransaction()
            tran?.replace(it, fragments[PROFILE_INDEX])
            tran?.commitNow()
        }
    }

    fun setupFragments(
        fragmentManager: FragmentManager,
        container: Int,
        parentContainer:Int,
        newFragments: List<Fragment>,
        parentFragmentManager: FragmentManager
    ) {
        fragments.clear()
        fragments.addAll(newFragments)
        containerFragment = container
        parentContainerFragment = parentContainer
        this@BottomNavigationBar.fragmentManager = fragmentManager
        this@BottomNavigationBar.parentFragmentManager = parentFragmentManager
    }

    fun getCurrentChildPosition() = currentSelectedPosition

    fun loadFirstFragment() {
        loadHomeFragment()
    }

    companion object {
        private const val HOME_INDEX = 0
        private const val DISCOVER_INDEX = 1
        private const val NEW_POST_INDEX = 2
        private const val COURSE_INDEX = 3
        private const val PROFILE_INDEX = 4
    }
}
