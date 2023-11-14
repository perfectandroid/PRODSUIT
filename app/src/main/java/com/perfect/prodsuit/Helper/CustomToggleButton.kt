package com.perfect.prodsuit.Helper

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatToggleButton
import androidx.core.content.ContextCompat
import com.perfect.prodsuit.R

class CustomToggleButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatToggleButton(context, attrs, defStyleAttr) {

    init {
        // Customize the appearance and behavior here

        // Example: Set a custom background for the button
        val customBackground: Drawable? = ContextCompat.getDrawable(context, R.drawable.custom_toggle_button_background)
        background = customBackground

        // Example: Set text color for different states
        val textColorStateList = ContextCompat.getColorStateList(context, R.color.colorPrimary)
        setTextColor(textColorStateList)

        // Example: Set initial state
        isChecked = false

        // Example: Add an OnCheckedChangeListener
        setOnCheckedChangeListener { _, isChecked ->
            // Do something when the state changes
            if (isChecked) {
                // Handle when the button is checked
            } else {
                // Handle when the button is unchecked
            }
        }
    }
}