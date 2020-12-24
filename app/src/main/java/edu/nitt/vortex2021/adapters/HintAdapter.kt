package edu.nitt.vortex2021.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.nitt.vortex2021.model.Hint
import com.squareup.picasso.Picasso
import edu.nitt.vortex2021.databinding.HintItemViewBinding

class HintAdapter(private val hints: List<Hint>): RecyclerView.Adapter<HintAdapter.HintViewHolder>() {
    inner class HintViewHolder(val binding: HintItemViewBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HintViewHolder {
        return HintViewHolder(
            HintItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: HintViewHolder, position: Int) {
        val hint = hints[position]
        if (hint.type == "IMAGE") {
            holder.binding.hintText.visibility = View.GONE
            holder.binding.hintImage.visibility = View.VISIBLE
            Picasso.get().load(hint.link).into(holder.binding.hintImage)
        } else {
            holder.binding.hintImage.visibility = View.GONE
            holder.binding.hintText.visibility = View.VISIBLE
            holder.binding.hintText.text = hint.clue
        }
    }

    override fun getItemCount(): Int = hints.size
}