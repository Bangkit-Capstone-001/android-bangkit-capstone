package com.example.capstoneapp.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.capstoneapp.R

class PasswordField @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {
    private var lockIconDrawable: Drawable
    private var visibilityIconDrawable: Drawable
    private var isPasswordVisible = false

    init {
        lockIconDrawable = ContextCompat.getDrawable(context, R.drawable.ic_baseline_lock_24)!!
        visibilityIconDrawable = ContextCompat.getDrawable(context, R.drawable.ic_baseline_visibility_24)!!

        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        transformationMethod = PasswordTransformationMethod.getInstance()
        compoundDrawablePadding = 16

        setCompoundDrawablesWithIntrinsicBounds(
            lockIconDrawable,
            null,
            visibilityIconDrawable,
            null
        )

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length ?: 0 < 8) {
                    error = context.getString(R.string.password_error)
                } else {
                    error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = if (layoutDirection == LAYOUT_DIRECTION_RTL) 0 else 2

                if (compoundDrawables[drawableEnd] != null) {
                    val drawableEndBounds = compoundDrawables[drawableEnd]?.bounds

                    val isDrawableClicked = when (layoutDirection) {
                        LAYOUT_DIRECTION_RTL -> event.x <= (drawableEndBounds?.width() ?: 0)
                        else -> event.x >= (width - (drawableEndBounds?.width() ?: 0))
                    }

                    if (isDrawableClicked) {
                        showPassword()
                        return true
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    private fun showPassword() {
        if (isPasswordVisible) {
            transformationMethod = PasswordTransformationMethod.getInstance()
            visibilityIconDrawable =
                ContextCompat.getDrawable(context, R.drawable.ic_baseline_visibility_24)!!
        } else {
            transformationMethod = HideReturnsTransformationMethod.getInstance()
            visibilityIconDrawable =
                ContextCompat.getDrawable(context, R.drawable.ic_baseline_visibility_off_24)!!
        }
        isPasswordVisible = !isPasswordVisible
        setCompoundDrawablesWithIntrinsicBounds(
            lockIconDrawable,
            null,
            visibilityIconDrawable,
            null
        )
        setSelection(text?.length ?: 0)
    }

    override fun setError(error: CharSequence?) {
        super.setError(error, null)
    }
}