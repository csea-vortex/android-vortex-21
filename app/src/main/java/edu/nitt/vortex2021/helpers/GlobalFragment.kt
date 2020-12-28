package edu.nitt.vortex2021.helpers

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import edu.nitt.vortex2021.R

fun Fragment.initGradientBackgroundAnimation(rootLayout: ViewGroup) {
    rootLayout.background = ResourcesCompat.getDrawable(resources, R.drawable.background_gradient, null)
    (rootLayout.background as AnimationDrawable).apply {
        setEnterFadeDuration(10)
        setExitFadeDuration(resources.getInteger(R.integer.background_gradient_change_duration))
        start()
    }
}

fun Fragment.showToastMessage(context: Context, message: String?) {
    if (message == null) return
    Toast.makeText(
        context,
        message,
        Toast.LENGTH_SHORT
    ).show()
}