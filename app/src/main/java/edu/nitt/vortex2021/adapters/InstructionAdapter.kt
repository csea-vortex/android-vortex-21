package edu.nitt.vortex2021.adapters

import android.text.Html
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import edu.nitt.vortex2021.databinding.FragmentInstructionBinding
import edu.nitt.vortex2021.databinding.InstructionItemBinding
import kotlinx.android.synthetic.main.instruction_item.view.*

class InstructionAdapter(val resources:List<String>) :
    RecyclerView.Adapter<InstructionAdapter.InstructionViewHolder>() {
    inner class InstructionViewHolder(val binding: InstructionItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructionViewHolder {
        return InstructionViewHolder(InstructionItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return resources.size
    }

    override fun onBindViewHolder(holder: InstructionViewHolder, position: Int) {
        holder.binding.instructionText.movementMethod = ScrollingMovementMethod()
        holder.binding.instructionText.text = HtmlCompat.fromHtml(resources[position],HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}