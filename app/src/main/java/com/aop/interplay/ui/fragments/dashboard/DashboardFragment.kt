package com.aop.interplay.ui.fragments.dashboard

import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.aop.interplay.R
import com.aop.interplay.databinding.FragmentDashboardBinding
import com.aop.interplay.extensions.makeGone
import com.aop.interplay.extensions.makeVisible
import com.aop.interplay.ui.fragments.BaseFragment
import com.aop.interplay.ui.fragments.dashboard.discover.DiscoverFragment
import com.aop.interplay.ui.fragments.dashboard.home.HomeFragment
import com.aop.interplay.ui.fragments.dashboard.learn.LearnFragment
import com.aop.interplay.ui.fragments.dashboard.newpost.NewPostFragment
import com.aop.interplay.ui.fragments.signup.SignUpFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseFragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DashboardViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.navView.getCurrentChildPosition() != 1) {
                        binding.navView.loadFirstFragment()
                    } else {
                        requireActivity().finish()
                    }
                }
            })

        setupToolbar()
        initViews()
        initObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setupToolbar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.mainToolbar.toolbar)
        setHasOptionsMenu(true)
    }

    private fun initViews() {
        binding.navView.setupFragments(
            childFragmentManager, R.id.nav_host_fragment, R.id.nav_host_fragment_activity_main, listOf(
                HomeFragment(),
                DiscoverFragment(),
                NewPostFragment(),
                LearnFragment(),
                SignUpFragment()
            ), parentFragmentManager
        )
    }

    private fun initObservers() {
        viewModel.fullScreenView.observe(viewLifecycleOwner) {
            if (it) {
                hideToolbar()
                hideNavBar()
            } else {
                showToolbar()
                showNavBar()
            }
        }
    }

    private fun hideNavBar() {
        binding.navView.makeGone()
    }

    private fun showNavBar() {
        binding.navView.makeVisible()
    }

    private fun showToolbar() {
        binding.mainToolbar.root.makeVisible()
    }

    private fun hideToolbar() {
        binding.mainToolbar.root.makeGone()
    }
}