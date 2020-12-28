package edu.nitt.vortex2021.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import edu.nitt.vortex2021.model.Hint
import com.squareup.picasso.Picasso
import edu.nitt.vortex2021.R
import edu.nitt.vortex2021.databinding.HintItemViewBinding
import edu.nitt.vortex2021.helpers.Constants

class HintAdapter(private val hints: List<Hint>): RecyclerView.Adapter<HintAdapter.HintViewHolder>() {
    inner class HintViewHolder(val binding: HintItemViewBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HintViewHolder {
        return HintViewHolder(
            HintItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: HintViewHolder, position: Int) {
        val hint = hints[position]
        val layoutParams = holder.binding.hintCardView.layoutParams as ConstraintLayout.LayoutParams
        if (hint.text == null) {
            layoutParams.dimensionRatio = "2:1"
            holder.binding.hintText.visibility = View.GONE
            holder.binding.hintImage.visibility = View.VISIBLE
            holder.binding.hintImageContainer.visibility = View.VISIBLE
            val picasso = Picasso.get()
            picasso.setIndicatorsEnabled(false)
            picasso.load(Constants.BACKEND_BASE_URL + hint.image)
                    .into(holder.binding.hintImage)
        } else if (hint.image == null) {
            layoutParams.dimensionRatio = "2:1"
            holder.binding.hintImage.visibility = View.GONE
            holder.binding.hintImageContainer.visibility = View.GONE
            holder.binding.hintText.visibility = View.VISIBLE
            holder.binding.hintText.text = hint.text
        } else {
            layoutParams.dimensionRatio = "1:1"
            holder.binding.hintImage.visibility = View.VISIBLE
            holder.binding.hintImageContainer.visibility = View.VISIBLE
            holder.binding.hintText.visibility = View.VISIBLE
            holder.binding.hintText.text = hint.text
            val picasso = Picasso.get()
            picasso.setIndicatorsEnabled(false)
            picasso.load(Constants.BACKEND_BASE_URL + hint.image)
                    .into(holder.binding.hintImage)
        }
    }

    override fun getItemCount(): Int = hints.size
}
