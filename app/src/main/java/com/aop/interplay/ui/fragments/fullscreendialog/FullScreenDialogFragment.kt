package com.aop.interplay.ui.fragments.fullscreendialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.navArgs
import com.aop.interplay.NavigationManager
import com.aop.interplay.data.FullScreenDialogType
import com.aop.interplay.databinding.FragmentFullScreenDialogBinding
import com.aop.interplay.extensions.makeVisible
import com.aop.interplay.ui.fragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FullScreenDialogFragment : BaseFragment() {

    @Inject lateinit var navigationManager: NavigationManager

    private var binding: FragmentFullScreenDialogBinding? = null
    private val navArgs: FullScreenDialogFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFullScreenDialogBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback {
            navigationManager.navigateFullScreenDialogToDashboardFragment()
        }
        initViews()
    }

    private fun initViews() {
        binding?.tvTitle?.text = navArgs.title
        binding?.tvMessage?.text = navArgs.message
        binding?.btnCta1?.text = navArgs.ctaText1
        binding?.btnCta2?.text = navArgs.ctaText2

        if (navArgs.type == FullScreenDialogType.ACCOUNT_CREATED) {
            binding?.btnCta1?.let {
                it.makeVisible()
                it.setOnClickListener {
                    navigationManager.navigateFullScreenDialogToDashboardFragment()
                }
            }

            binding?.btnCta2?.let {
                it.makeVisible()
                it.setOnClickListener {
                    // TODO: Implement navigation here
                }
            }
        }
    }
}
