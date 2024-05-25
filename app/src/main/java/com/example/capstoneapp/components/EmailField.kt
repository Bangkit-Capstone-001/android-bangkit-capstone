package com.example.capstoneapp.components

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.capstoneapp.R

class EmailField @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {
    private var emailIconDrawable: Drawable

    init {
        emailIconDrawable = ContextCompat.getDrawable(context, R.drawable.ic_baseline_email_24) as Drawable

        inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        compoundDrawablePadding = 16

        setCompoundDrawablesWithIntrinsicBounds(emailIconDrawable, null, null, null)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                    error = context.getString(R.string.email_error)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}