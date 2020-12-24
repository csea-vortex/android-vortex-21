package edu.nitt.vortex2021.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import edu.nitt.vortex2021.MainActivity
import edu.nitt.vortex2021.R
import edu.nitt.vortex2021.model.Question
import edu.nitt.vortex2021.databinding.QuestionItemViewBinding
import edu.nitt.vortex2021.fragments.LinkedFragment

class QuestionAdapter(private val context: Context, private val onButtonPressListener: OnButtonPressListener): RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    private var questions = mutableListOf<Question>()

    inner class QuestionViewHolder(val binding: QuestionItemViewBinding): RecyclerView.ViewHolder(binding.root)

    fun addQuestion(newQuestion: Question) {
        questions.add(newQuestion)
        notifyItemChanged(questions.size-2)
        notifyItemInserted(questions.size-1)
    }

    fun clearQuestions() {
        questions.clear()
        notifyDataSetChanged()
    }

    fun addExtraHint(questionNo: Int) {
        questions[questionNo-1].isGivenAddHint = true
        notifyItemChanged(questionNo-1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        return QuestionViewHolder(
            QuestionItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questions[position]
        Log.i("Question Adapter", "isGivenAddHint: ${question.isGivenAddHint}")
        val hints = if (question.isGivenAddHint) {
            question.hints + question.additionalHint
        } else {
            question.hints
        }
        holder.binding.hintsRecyclerView.adapter = HintAdapter(hints)
        holder.binding.hintsRecyclerView.layoutManager = LinearLayoutManager(context)
        holder.binding.submitAnswerButton.setOnClickListener {
            val givenAnswer = holder.binding.answerEditText.text.toString()
            if (question.answer == givenAnswer) {
                onButtonPressListener.onCorrectAnswer(position+1)
            } else {
                onButtonPressListener.onWrongAnswer(position+1, givenAnswer)
            }
            holder.binding.answerEditText.setText("")
        }
        holder.binding.additionalHintButton.setOnClickListener {
            onButtonPressListener.onHintRequest(position+1)
        }

        if (position < itemCount - 1) {
            Log.i("QuestionAdapter", "DISABLING: ${itemCount - 1}")
            holder.binding.answerEditText.setText(question.answer)
            holder.binding.answerEditText.isEnabled = false
            holder.binding.submitAnswerButton.visibility = View.GONE
            holder.binding.additionalHintButton.visibility = View.GONE
        } else {
            holder.binding.answerEditText.setText("")
            holder.binding.answerEditText.isEnabled = true
            holder.binding.submitAnswerButton.visibility = View.VISIBLE
            holder.binding.additionalHintButton.visibility = if (!question.isGivenAddHint) View.VISIBLE else View.GONE
        }
    }

    override fun getItemCount(): Int = questions.size

    interface OnButtonPressListener {
        fun onCorrectAnswer(questionNo: Int)

        fun onWrongAnswer(questionNo: Int, givenAnswer: String)

        fun onHintRequest(questionNumber: Int)
    }
}