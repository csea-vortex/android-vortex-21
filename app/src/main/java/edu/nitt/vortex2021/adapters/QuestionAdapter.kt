package edu.nitt.vortex2021.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.nitt.vortex2021.databinding.QuestionItemViewBinding
import edu.nitt.vortex2021.model.Hint
import edu.nitt.vortex2021.model.LatestLinkedQuestion

class QuestionAdapter(private val context: Context,
                      private val onButtonPressListener: OnButtonPressListener):
        RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    private lateinit var latestLinkedQuestion: LatestLinkedQuestion

    inner class QuestionViewHolder(val binding: QuestionItemViewBinding): RecyclerView.ViewHolder(binding.root)

    fun setLatestQuestion(question: LatestLinkedQuestion) {
        latestLinkedQuestion = question
        notifyDataSetChanged()
    }

    fun addAdditionalHint(additionalHint: Hint) {
        latestLinkedQuestion.isAdditionalHintTaken = true
        latestLinkedQuestion.additionalHint = additionalHint
        notifyItemChanged(itemCount-1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        return QuestionViewHolder(
            QuestionItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.binding.submitAnswerButton.setOnClickListener {
            val givenAnswer = holder.binding.answerEditText.text.toString().toLowerCase()
            onButtonPressListener.onAnswer(givenAnswer)
            holder.binding.answerEditText.setText("")
        }

        holder.binding.additionalHintButton.setOnClickListener {
            onButtonPressListener.onHintRequest()
        }

        if (position == latestLinkedQuestion.qno!! - 1) {
            val hints = if (latestLinkedQuestion.isAdditionalHintTaken!!) {
                latestLinkedQuestion.hints!! + latestLinkedQuestion.additionalHint!!
            } else {
                latestLinkedQuestion.hints
            }
            holder.binding.hintsRecyclerView.adapter = HintAdapter(hints!!)
            holder.binding.hintsRecyclerView.layoutManager = LinearLayoutManager(context)
            holder.binding.hintsRecyclerView.visibility = View.VISIBLE
            holder.binding.answerEditText.visibility = View.VISIBLE
            holder.binding.answerTextView.visibility = View.GONE
            holder.binding.answerEditText.setText("")
            holder.binding.answerEditText.isEnabled = true
            holder.binding.submitAnswerButton.visibility = View.VISIBLE
            holder.binding.additionalHintButton.visibility = if (!latestLinkedQuestion.isAdditionalHintTaken!!) View.VISIBLE else View.GONE
        } else {
            holder.binding.hintsRecyclerView.visibility = View.INVISIBLE
            holder.binding.answerTextView.visibility = View.VISIBLE
            holder.binding.answerTextView.text = latestLinkedQuestion.prevAnswers!![position]
            holder.binding.answerEditText.visibility = View.GONE
            holder.binding.submitAnswerButton.visibility = View.GONE
            holder.binding.additionalHintButton.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int  {
        if (this::latestLinkedQuestion.isInitialized) {
            return latestLinkedQuestion.qno!!
        }
        return 0
    }

    interface OnButtonPressListener {
        fun onAnswer(givenAnswer: String)
        fun onHintRequest()
    }
}