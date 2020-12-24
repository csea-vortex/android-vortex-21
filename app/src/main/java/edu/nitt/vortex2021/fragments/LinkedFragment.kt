package edu.nitt.vortex2021.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import edu.nitt.vortex2021.R
import edu.nitt.vortex2021.adapters.QuestionAdapter
import edu.nitt.vortex2021.api.getQuestions
import edu.nitt.vortex2021.api.getUserProgress
import edu.nitt.vortex2021.databinding.FragmentLinkedBinding
import edu.nitt.vortex2021.helpers.initGradientBackgroundAnimation
import edu.nitt.vortex2021.helpers.viewLifecycle
import edu.nitt.vortex2021.model.Question

class LinkedFragment: Fragment() {

    var binding by viewLifecycle<FragmentLinkedBinding>()
    private lateinit var questionAdapter: QuestionAdapter
    private lateinit var questions: MutableList<Question>

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentLinkedBinding.inflate(inflater, container, false)
        initGradientBackgroundAnimation(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        questionAdapter = QuestionAdapter(requireContext(),
                object : QuestionAdapter.OnButtonPressListener {
                    override fun onCorrectAnswer(questionNo: Int) {
                        Toast.makeText(requireContext(), "Correct answer!", Toast.LENGTH_SHORT).show()
                        //send info to the server about correct answer
                        if (questionNo == 5) {
                            gotoNextRound()
                        } else {
                            addQuestion()
                        }
                    }

                    override fun onWrongAnswer(questionNo: Int, givenAnswer: String) {
                        Toast.makeText(requireContext(), "$givenAnswer is not the answer", Toast.LENGTH_SHORT).show()
                    }

                    override fun onHintRequest(questionNumber: Int) {
                        MaterialAlertDialogBuilder(requireContext())
                                .setTitle(getString(R.string.confirm_hint_title))
                                .setMessage(getString(R.string.confirm_hint_body))
                                .setPositiveButton("Yes") { dialog, _ ->
                                    //send info to backend
                                    questionAdapter.addExtraHint(questionNumber)
                                    addLockedTabs()
                                    dialog.cancel()
                                }
                                .setNegativeButton("No") { dialog, _ ->
                                    dialog.cancel()
                                }.create().show()
                    }

                })
        binding.questionsViewPager.adapter = questionAdapter

        TabLayoutMediator(binding.questionsTabLayout, binding.questionsViewPager) { tab, position ->
            tab.text = "${position+1}"
        }.attach()

        getInitialQuestions()
    }

    private fun getInitialQuestions() {
        questionAdapter.clearQuestions()
        val userProgress = getUserProgress()
        binding.roundTextView.text = getString(R.string.round_number, userProgress.roundNumber)
        questions = getQuestions(userProgress.roundNumber) as MutableList<Question>
        addLockedTabs()
        repeat(userProgress.questionNumber) {
            addQuestion()
        }
    }

    private fun addQuestion() {
        val newQuestion = questions[questionAdapter.itemCount]
        questionAdapter.addQuestion(newQuestion)
        binding.questionsTabLayout.
            selectTab(binding.questionsTabLayout.getTabAt(questionAdapter.itemCount - 1))
        addLockedTabs()
    }

    private fun gotoNextRound() {
        questionAdapter.clearQuestions()
        addLockedTabs()
        getInitialQuestions()
    }

    private fun addLockedTabs() {
        val count = binding.questionsTabLayout.tabCount
        val tabStrip = binding.questionsTabLayout.getChildAt(0) as LinearLayout
        if (count != 0) tabStrip.getChildAt(count-1).isClickable = true
        for (i in (count+1)..5) {
            val newTab = binding.questionsTabLayout.newTab()
            newTab.text = "$i \uD83D\uDD12"
            binding.questionsTabLayout.addTab(newTab)
            tabStrip.getChildAt(i-1).isClickable = false
        }
    }

}