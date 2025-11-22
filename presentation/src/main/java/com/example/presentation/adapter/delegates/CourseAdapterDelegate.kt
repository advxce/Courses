package com.example.presentation.adapter.delegates

import android.util.Log
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.presentation.MainActivity
import com.example.presentation.R
import com.example.presentation.data.CourseUI
import com.example.presentation.databinding.ItemCourseBinding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import eightbitlab.com.blurview.BlurView

fun courseAdapterDelegate(
    onDetailsClick: (CourseUI) -> Unit,
    onBookmarkedClick: (CourseUI) -> Unit
) = adapterDelegateViewBinding<CourseUI, CourseUI, ItemCourseBinding>({ inflater, parent ->
    ItemCourseBinding.inflate(
        inflater,
        parent,
        false
    )
}) {






        fun BlurView.initBlur(rootView: ViewGroup) {
            val windowBackground = rootView.background
            setupWith(rootView)

                .setFrameClearDrawable(windowBackground)
                .setBlurRadius(18f)
                .setBlurAutoUpdate(true)
        }



    bind {

        val rootView = itemView.findViewById<ViewGroup>(R.id.itemCourseBox)

        binding.itemHeader.blurViewRating.initBlur(rootView)
        binding.itemHeader.blurViewBookmark.initBlur(rootView)
        binding.itemHeader.blurViewContent.initBlur(rootView)



        binding.root.setOnClickListener {
            onDetailsClick(item)
        }
        binding.itemHeader.apply {
            courseImage.setImageResource(item.image)
            dateTextView.text = item.date
            ratingTextView.text = item.rating
            bookmarkCard.setOnClickListener {
                val newItem = item.copy(isBookmarked = !item.isBookmarked)
                bookmark.setColorFilter(
                    if (newItem.isBookmarked)
                        ContextCompat.getColor(context, R.color.app_green)
                    else
                        ContextCompat.getColor(context, R.color.app_textview_color)
                )
                onBookmarkedClick(newItem)
            }

            bookmark.setColorFilter(
                if (item.isBookmarked)
                    ContextCompat.getColor(context, R.color.app_green)
                else
                    ContextCompat.getColor(context, R.color.app_textview_color)
            )

        }

        binding.itemContent.apply {
            this.priceTextView.text = "${item.price}"
            this.titleTextView.text = item.title
            this.descriptionTextView.text = item.description
        }
    }
}

