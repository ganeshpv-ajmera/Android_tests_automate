package com.aop.interplay.adapters.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aop.interplay.adapters.BaseViewHolder
import com.aop.interplay.data.HomePostListItem
import com.aop.interplay.data.VideoType
import com.aop.interplay.databinding.*
import com.aop.interplay.listeners.VideoInteractionListener
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource

class VideoViewListAdapter(
    private val videosList: List<HomePostListItem>,
    private val videoInteractionListener: VideoInteractionListener
) : RecyclerView.Adapter<BaseViewHolder<Any>>() {

    override fun getItemViewType(position: Int): Int {
        // TODO: testing logic. refine conditions once all the layouts are ready
        return when (videosList[position].homePost.challengeType) {
            "assignment" -> {
                VideoType.ASSIGNMENT.value
            }
            "behindTheScenes" -> {
                VideoType.BEHIND_THE_SCENES.value
            }
            "challenge" -> {
                VideoType.CHALLENGE.value
            }
            "classPreview" -> {
                VideoType.CLASS_PREVIEW.value
            }
            else -> {
                VideoType.NONE.value
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Any> {
        val inflater = LayoutInflater.from(parent.context)
        return (when (viewType) {
            1 -> AssignmentVideoContentViewHolder(
                VideoViewAssignmentItemBinding.inflate(
                    inflater, parent, false
                ), videoInteractionListener
            )
            2 -> BehindTheScenesVideoContentViewHolder(
                VideoViewBehindTheScenesItemBinding.inflate(
                    inflater, parent, false
                ), videoInteractionListener
            )
            3 -> ChallengeVideoContentViewHolder(
                VideoViewChallengeItemBinding.inflate(
                    inflater, parent, false
                ), videoInteractionListener
            )
            4 -> ClassPreviewVideoContentViewHolder(
                VideoViewClassPreviewItemBinding.inflate(
                    inflater, parent, false
                ), videoInteractionListener
            )
            5 -> VideoContentViewHolder(
                VideoViewItemBinding.inflate(
                    inflater, parent, false
                ), videoInteractionListener
            )
            else -> throw IllegalAccessException("Unknown layout type")
        }) as BaseViewHolder<Any>
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Any>, position: Int) {
        holder.bind(videosList[position].homePost, position)
    }

    override fun onViewAttachedToWindow(holder: BaseViewHolder<Any>) {
        super.onViewAttachedToWindow(holder)
        with(holder) {
            val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
            val hlsMediaSource = HlsMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(videosList[absoluteAdapterPosition].homePost.videoInfo.url))

            getNewPlayerInstance().apply {
                setMediaSource(hlsMediaSource)
                prepare()
                playerView?.player = this
                videosList[absoluteAdapterPosition].view = this
            }
        }
    }

    override fun onViewDetachedFromWindow(holder: BaseViewHolder<Any>) {
        super.onViewDetachedFromWindow(holder)
        holder.playerView?.player?.pause()
        holder.playerView?.player?.release()
    }

    override fun getItemCount() = videosList.size
}