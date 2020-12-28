package edu.nitt.vortex2021.adapters

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.nitt.vortex2021.databinding.InstructionItemBinding

class InstructionAdapter(val items: List<String>) :
    RecyclerView.Adapter<InstructionAdapter.InstructionViewHolder>() {
    inner class InstructionViewHolder(val binding: InstructionItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructionViewHolder {
        return InstructionViewHolder(
            InstructionItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: InstructionViewHolder, position: Int) {
        holder.binding.instructionText.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // FROM_HTML_MODE_LEGACY is the behaviour that was used for versions below android N
            // we are using this flag to give a consistent behaviour
            Html.fromHtml(items[position], Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(items[position])
        }

    }
}