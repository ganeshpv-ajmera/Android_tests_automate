package com.aop.interplay.ui.fragments.dashboard.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.aop.interplay.adapters.home.VideoViewListAdapter
import com.aop.interplay.data.HomePostListItem
import com.aop.interplay.databinding.FragmentHomeBinding
import com.aop.interplay.listeners.VideoInteractionListener
import com.aop.interplay.ui.fragments.BaseFragment
import com.aop.interplay.ui.fragments.dashboard.home.HomeViewModel.Companion.PAGE_SIZE
import com.aop.interplay.ui.fragments.splash.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.max

@AndroidEntryPoint
class HomeFragment : BaseFragment(), VideoInteractionListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private val splashViewModel: SplashViewModel by activityViewModels()

    private val snapHelper = PagerSnapHelper()
    private var adapter: VideoViewListAdapter? = null
    private var layoutManager: LinearLayoutManager? = null
    private var listData: MutableList<HomePostListItem> = mutableListOf()
    private var maxAdapterPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        initListeners()
        splashViewModel.content.value?.let {
            loadVideos(it)
        } ?: run {
            viewModel.getPosts(true)
        }
    }

    override fun onResume() {
        super.onResume()
        val position =
            (binding.rvVideos.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
        listData.getOrNull(position)?.view?.play()
    }

    override fun onStop() {
        super.onStop()
        listData.forEach {
            it.view?.pause()
        }
    }

    override fun onDestroyView() {
        _binding = null
        listData.forEach {
            it.view?.release()
        }
        super.onDestroyView()
    }

    override fun onStarClicked(isEnabled: Boolean) {
        // TODO: call API call
    }

    override fun onBookmarkClicked(isEnabled: Boolean) {
        // TODO: call API call
    }

    override fun onShareClicked() {
        // TODO: call API call
    }

    override fun onProfileClicked() {
        // TODO: open user profile
    }

    override fun onVideoClicked(position: Int) {
        val playerView = listData.getOrNull(position)?.view
        if (playerView?.isPlaying == true) {
            playerView.pause()
        } else {
            playerView?.play()
        }
    }

    override fun onLearnClicked() {
        // TODO: open screen
    }

    override fun onTryClicked() {
        // TODO: open screen
    }

    override fun onDescriptionExpanded() {
        // TODO: add analytics
    }

    override fun onDescriptionCollapsed() {
        // TODO: add analytics
    }

    private fun initViews() {
        adapter = VideoViewListAdapter(listData, this)
        layoutManager = LinearLayoutManager(requireContext())
        binding.rvVideos.layoutManager = layoutManager
        binding.rvVideos.adapter = adapter
        snapHelper.attachToRecyclerView(binding.rvVideos)
    }

    private fun initObservers() {
        viewModel.content.observe(viewLifecycleOwner) {
            it?.let {
                loadVideos(it)
            }
        }
    }

    private fun loadVideos(videos: List<HomePostListItem>) {
        listData.addAll(videos)
        adapter?.notifyDataSetChanged()
    }

    private fun initListeners() {
        binding.rvVideos.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val currentPosition = layoutManager?.findLastCompletelyVisibleItemPosition() ?: 0
                if (currentPosition > maxAdapterPosition) {
                    maxAdapterPosition = max(currentPosition, maxAdapterPosition)
                    if (currentPosition % (PAGE_SIZE / 2) == 0) {
                        viewModel.getPosts()
                    }
                }
            }
        })
    }
}