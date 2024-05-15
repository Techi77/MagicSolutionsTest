package com.mvi.main_mvi

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.mvi.MviProcessor

class MainViewModel : MviProcessor<MainState, MainEvent, MainSingleEvent>() {
    private var manager: ReviewManager? = null
    private var reviewInfo: ReviewInfo? = null

    private val appLink: String = "https://play.google.com/store/apps/details?id="

    override fun initialState(): MainState {
        return MainState()
    }

    override fun reduce(event: MainEvent, state: MainState): MainState {
        return when (event) {
            is MainEvent.CreateReviewManager -> state.also { createReviewManager(event.context) }
            is MainEvent.RateUp -> state.also { launchReview(event.activity, event.isOnlyReview, event.onComplete) }
            is MainEvent.Share -> state.also { share(event.context, event.appName, event.share) }
        }
    }

    override suspend fun handleEvent(event: MainEvent, state: MainState): MainEvent? {
        return null
    }

    private fun createReviewManager(context: Context) {
        manager = ReviewManagerFactory.create(context)
        loadReviewInfo()
    }

    private fun loadReviewInfo() {
        val request = manager?.requestReviewFlow()

        request?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                reviewInfo = task.result
            }
        }
    }

    private fun launchReview(activity: Activity?, isOnlyReview: Boolean, onComplete: () -> Unit) {
        if (activity == null) return

        when {
            !isOnlyReview -> openMarket(activity)

            reviewInfo != null -> {
                val flow = reviewInfo?.let { info ->
                    manager?.launchReviewFlow(activity, info)
                }

                flow?.addOnCompleteListener {
                    reviewInfo = null
                    onComplete.invoke()
                }
            }

            else -> onComplete.invoke()
        }
    }

    private fun openMarket(activity: Activity) {
        val goToMarket = Intent().apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse("$appLink${activity.packageName}")
            setPackage("com.android.vending")
        }

        try {
            activity.startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            try {
                activity.startActivity(
                    Intent().apply {
                        action = Intent.ACTION_VIEW
                        data = Uri.parse("$appLink${activity.packageName}")
                    }
                )
            } catch (ignore: Exception) {
            }
        }
    }

    private fun share(context: Context, appName: String, share: String) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND

            putExtra(Intent.EXTRA_SUBJECT, appName)
            putExtra(Intent.EXTRA_TEXT, "$appLink${context.packageName}")

            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(intent, share)

        try {
            context.startActivity(shareIntent)
        } catch (ignore: Exception) {
        }
    }
}