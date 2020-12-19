package edu.nitt.vortex2021.helpers

import android.view.View
import androidx.viewpager.widget.ViewPager


class CubeTransformer : ViewPager.PageTransformer {
    override fun transformPage(view: View, position: Float) {
        //if position = 0 (current image) pivot on x axis is on the right, else if
        // position > 0, (next image) pivot on x axis is on the left (origin of the axis)
        view.pivotX = if (position <= 0) view.width.toFloat()  else 0.0f
        view.pivotY = view.height *0.5f

        //it rotates with 90 degrees multiplied by current position
        view.rotationY = 90f * position

    }
}
