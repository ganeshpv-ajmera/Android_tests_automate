package com.aop.interplay.adapters

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.aop.interplay.custom_views.truncatingtextview.OnTruncatingTextViewListener
import com.aop.interplay.custom_views.truncatingtextview.TruncatingTextView
import com.aop.interplay.listeners.VideoInteractionListener
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView

open class BaseViewHolder<T>(
    val binding: ViewBinding, private val videoInteractionListener: VideoInteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    open fun bind(dataModel: T, position: Int) {

    }

    open var playerView: StyledPlayerView? = null

    val context: Context
        get() = binding.root.context

    fun getNewPlayerInstance() =
        ExoPlayer.Builder(context).setUseLazyPreparation(true).build().apply {
            playWhenReady = true
            repeatMode = Player.REPEAT_MODE_ALL
        }

    fun initListeners(
        playerViewFrameLayout: View,
        buttonStar: ToggleButton,
        buttonBookmark: ToggleButton,
        buttonShare: ImageView,
        ivProfile: ImageView,
        buttonLearn: Button? = null,
        buttonTry: Button? = null,
        tvVideoDescription: TruncatingTextView,
        position: Int
    ) {
        playerViewFrameLayout.setOnClickListener {
            videoInteractionListener.onVideoClicked(position)
        }

        buttonStar.setOnCheckedChangeListener { _, isChecked ->
            videoInteractionListener.onStarClicked(isChecked)
        }

        buttonBookmark.setOnCheckedChangeListener { _, isChecked ->
            videoInteractionListener.onBookmarkClicked(isChecked)
        }

        buttonShare.setOnClickListener {
            videoInteractionListener.onShareClicked()
        }

        ivProfile.setOnClickListener {
            videoInteractionListener.onProfileClicked()
        }

        buttonLearn?.setOnClickListener {
            videoInteractionListener.onLearnClicked()
        }

        buttonTry?.setOnClickListener {
            videoInteractionListener.onTryClicked()
        }

        tvVideoDescription.setListener(object : OnTruncatingTextViewListener {
            override fun onExpandClicked() {
                videoInteractionListener.onDescriptionExpanded()
            }

            override fun onCollapseClicked() {
                videoInteractionListener.onDescriptionCollapsed()
            }
        })
    }
}