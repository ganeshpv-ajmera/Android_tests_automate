package com.aop.interplay.ui.fragments.dashboard.newpost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.aop.interplay.databinding.FragmentNewpostBinding
import dagger.hilt.android.AndroidEntryPoint
import com.aop.interplay.ui.fragments.BaseFragment
import com.aop.interplay.ui.fragments.dashboard.DashboardViewModel
import com.otaliastudios.cameraview.controls.Audio
import com.otaliastudios.cameraview.controls.Facing
import com.otaliastudios.cameraview.controls.Mode

@AndroidEntryPoint
class NewPostFragment : BaseFragment() {

    private val dashboardViewModel: DashboardViewModel by activityViewModels()
    private var binding: FragmentNewpostBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewpostBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    override fun onResume() {
        super.onResume()
        dashboardViewModel.enableFullScreen()
    }

    override fun onPause() {
        super.onPause()
        dashboardViewModel.disableFullScreen()
    }

    private fun initViews() {
        initCameraView()
    }

    private fun initCameraView() {
        binding?.cameraView?.apply {
            facing = Facing.FRONT
            audio = Audio.ON
            mode = Mode.VIDEO

            open()
        }
    }
}