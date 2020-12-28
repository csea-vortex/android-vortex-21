package edu.nitt.vortex2021.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
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
        // ToDo: Handle Android N case
        holder.binding.instructionText.text =
            HtmlCompat.fromHtml(items[position], HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}