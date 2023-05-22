package com.perfect.prodsuit.Helper

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

class SlideInItemAnimator : DefaultItemAnimator() {
    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        if (holder != null) {
            holder.itemView.translationY = holder.itemView.height.toFloat()
            holder.itemView.alpha = 1f
            holder.itemView.animate()
                .translationY(20f)
                .alpha(1f)
                .setDuration(addDuration)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        dispatchAddFinished(holder)
                    }
                })
                .start()
            return true
        }
        return super.animateAdd(holder)
    }
}