package com.aop.interplay.adapters.home

import android.graphics.Color
import com.aop.interplay.R
import com.aop.interplay.adapters.BaseViewHolder
import com.aop.interplay.data.network.HomePost
import com.aop.interplay.databinding.VideoViewAssignmentItemBinding
import com.aop.interplay.extensions.loadCircularImage
import com.aop.interplay.listeners.VideoInteractionListener
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.ui.StyledPlayerView

class AssignmentVideoContentViewHolder(
    binding: VideoViewAssignmentItemBinding,
    videoInteractionListener: VideoInteractionListener
) : BaseViewHolder<HomePost>(binding, videoInteractionListener) {

    override fun bind(dataModel: HomePost, position: Int) {
        super.bind(dataModel, position)
        with(binding as VideoViewAssignmentItemBinding) {
            videoInfoLayout.tvUserName.text = context.getString(R.string.username, dataModel.user.id)
            videoInfoLayout.tvVideoDescription.text = dataModel.getDescriptionWithTags()
            videoCtaLayout.ivProfile.loadCircularImage(
                dataModel.user.bioImage, 8f, Color.WHITE
            )
            Glide.with(root.context).load(R.drawable.badge_assignment_default).into(videoInfoLayout.ivChallengeType)

            initListeners(
                playerViewFrameLayout = playerViewFrameLayout,
                buttonStar = videoCtaLayout.buttonStar,
                buttonBookmark = videoCtaLayout.buttonBookmark,
                buttonShare = videoCtaLayout.buttonShare,
                ivProfile = videoCtaLayout.ivProfile,
                buttonLearn = buttonLearn,
                buttonTry = buttonTry,
                tvVideoDescription = videoInfoLayout.tvVideoDescription,
                position = position
            )
        }
    }

    override var playerView: StyledPlayerView? = binding.playerView
}