package com.aop.interplay.adapters.home

import android.graphics.Color
import com.aop.interplay.R
import com.aop.interplay.adapters.BaseViewHolder
import com.aop.interplay.data.network.HomePost
import com.aop.interplay.databinding.VideoViewItemBinding
import com.aop.interplay.extensions.loadCircularImage
import com.aop.interplay.listeners.VideoInteractionListener
import com.google.android.exoplayer2.ui.StyledPlayerView

class VideoContentViewHolder(
    binding: VideoViewItemBinding,
    videoInteractionListener: VideoInteractionListener
) : BaseViewHolder<HomePost>(binding, videoInteractionListener) {

    override fun bind(dataModel: HomePost, position: Int) {
        super.bind(dataModel, position)
        with(binding as VideoViewItemBinding) {
            videoInfoLayout.tvUserName.text = context.getString(R.string.username, dataModel.user.id)
            videoInfoLayout.tvVideoDescription.text = dataModel.getDescriptionWithTags()
            videoCtaLayout.ivProfile.loadCircularImage(
                dataModel.user.bioImage, 8f, Color.WHITE
            )

            initListeners(
                playerViewFrameLayout = playerViewFrameLayout,
                buttonStar = videoCtaLayout.buttonStar,
                buttonBookmark = videoCtaLayout.buttonBookmark,
                buttonShare = videoCtaLayout.buttonShare,
                ivProfile = videoCtaLayout.ivProfile,
                tvVideoDescription = videoInfoLayout.tvVideoDescription,
                position = position
            )
        }
    }

    override var playerView: StyledPlayerView? = binding.playerView
}