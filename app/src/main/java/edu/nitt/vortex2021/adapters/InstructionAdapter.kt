package edu.nitt.vortex2021.adapters

import android.text.Html
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.nitt.vortex2021.databinding.FragmentInstructionBinding
import edu.nitt.vortex2021.databinding.InstructionItemBinding
import kotlinx.android.synthetic.main.instruction_item.view.*

class InstructionAdapter(val resources:List<String>) :
    RecyclerView.Adapter<InstructionAdapter.InstructionViewHolder>() {
    inner class InstructionViewHolder(binding: InstructionItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructionViewHolder {
        return InstructionViewHolder(InstructionItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return resources.size
    }

    override fun onBindViewHolder(holder: InstructionViewHolder, position: Int) {
      var text = "Given a problem, you can think of a solution. But given an answer, can you think of a solution? Vortex welcomes you to participate in a one of a kind event with Unravel the Code! Twist your brain to generate solutions for a given answer and optimise a given pseudo code! Oh and did we mention? Prizes galore for winners! See you there!\", rules:'<div id = \"rules_style\">    <ul>        <li>It is an individual event.</li>        <li>Any act of plagiarism that results in an unfair advantage will lead to disqualification.</li>        <li>The solutions should not be made public during the contest. Similarities between the submissions will be checked and may lead to disqualification.</li>    </ul>    <h3 class=\"my-4\">Judgment Criteria:</h3>    <ul class=\"pl-4\">        <li>In both sections, the points will be given based on the number of test cases passed for the respective problems and the complexity of the passed test cases. In case of a tie in the scores, the time of submission will be considered.</li>    </ul></div>',format:'<div id = \"format_style\">    <h3>There is only one round divided into two sections :</h3>    <ul>        <li>            <b>Section 1:</b>            </br>The participants have to write the code for given executable files. The executable files will be shared via Google Colab and can be downloaded and run in the browser itself.        </li>        </br>        <li>            <b>Section 2:</b>            </br>The participants have to write optimized code for a given pseudo code with an unoptimized solution(with or without logical errors).        </li>      </ul>   </div>',resources:'<div id = \"resources_style\">    <h3>Contacts:</h3>    <ol>        <li>Shivang (9131425989)</li>        <li>Ashish (7891000479)</li>        <li>Shrey (7338749375)</li>    </ol>       </div>"
        holder.itemView.instructionText.movementMethod = ScrollingMovementMethod()
        holder.itemView.instructionText.text = Html.fromHtml(resources[position])
    }
}