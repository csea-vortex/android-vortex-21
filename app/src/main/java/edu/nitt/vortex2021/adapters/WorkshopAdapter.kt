package edu.nitt.vortex2021.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import edu.nitt.vortex2021.databinding.ListItemWorkshopBinding
import edu.nitt.vortex2021.helpers.Constants
import edu.nitt.vortex2021.helpers.getFormatted
import edu.nitt.vortex2021.model.Workshop

class WorkshopAdapter(
    private val currentUserEmail: String,
    private val workshops: List<Workshop>,
    private val onRegisterButtonClickListener: (workshop: Workshop) -> Unit
) : RecyclerView.Adapter<WorkshopAdapter.WorkshopViewHolder>() {

    private var selectedWorkshopIdx: Int = -1

    inner class WorkshopViewHolder(val binding: ListItemWorkshopBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(index: Int, workshop: Workshop) {
            binding.workshopName.text = workshop.title
            binding.workshopFrom.text = "\uD83D\uDCC5 Starts ${workshop.eventFrom.getFormatted()}"
            binding.workshopTo.text = "\uD83D\uDCC5 Ends ${workshop.eventTo.getFormatted()}"

            if (workshop.isFree || (currentUserEmail.endsWith("@nitt.edu") && workshop.isNITTFree)) {
                binding.workshopCost.text = "\uD83D\uDCB0 Free"
            } else {
                binding.workshopCost.text = "\uD83D\uDCB0 ₹ ${workshop.cost}"
            }

            var description = workshop.description + "\n\n"
            description += "Instructions: \n"
            for (instructionPoint in workshop.instructions) {
                description += "• $instructionPoint\n"
            }

            binding.description.text = description

            try {
                Picasso.get().load(Constants.BACKEND_BASE_URL + workshop.smallImage)
                    .into(binding.workshopLogo, object : Callback {
                        override fun onSuccess() = Unit
                        override fun onError(e: Exception?) {
                            e?.stackTrace
                        }
                    })
            } catch (e: Exception) {
                e.stackTrace
            }

            binding.registerButton.apply {
                isEnabled = System.currentTimeMillis() < workshop.eventTo.time
                setOnClickListener { onRegisterButtonClickListener(workshop) }
            }

            binding.detailsButton.setOnClickListener {
                selectedWorkshopIdx = index
                notifyDataSetChanged()
            }

            binding.description.apply {
                visibility = if (index == selectedWorkshopIdx) {
                    if (visibility == View.VISIBLE) View.GONE
                    else View.VISIBLE
                } else {
                    View.GONE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkshopViewHolder {
        val binding = ListItemWorkshopBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WorkshopViewHolder(binding)
    }

    override fun getItemCount() = workshops.size

    override fun onBindViewHolder(holder: WorkshopViewHolder, position: Int) {
        holder.bind(position, workshops[position])
    }

}