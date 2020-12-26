package edu.nitt.vortex2021.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import edu.nitt.vortex2021.BaseApplication
import edu.nitt.vortex2021.R
import edu.nitt.vortex2021.adapters.QuestionAdapter
import edu.nitt.vortex2021.api.getDummyQuestion
import edu.nitt.vortex2021.databinding.FragmentLinkedBinding
import edu.nitt.vortex2021.helpers.Resource
import edu.nitt.vortex2021.helpers.initGradientBackgroundAnimation
import edu.nitt.vortex2021.helpers.viewLifecycle
import edu.nitt.vortex2021.model.CheckLinkedAnswerRequest
import edu.nitt.vortex2021.viewmodel.LinkedViewModel

class LinkedFragment: Fragment() {

    var binding by viewLifecycle<FragmentLinkedBinding>()
    private lateinit var questionAdapter: QuestionAdapter
    private lateinit var linkedViewModel: LinkedViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentLinkedBinding.inflate(inflater, container, false)
        initViewModel()
        initGradientBackgroundAnimation(binding.root)
        return binding.root
    }

    private fun initViewModel() {
        val factory = (requireActivity().application as BaseApplication)
                .applicationComponent
                .getViewModelProviderFactory()
        linkedViewModel = ViewModelProvider(this, factory).get(LinkedViewModel::class.java)
        observeLiveData()
        linkedViewModel.sendLatestLinkedQuestionRequest()
        linkedViewModel.getCurrentScoreRank()
    }

    private fun observeLiveData() {
        linkedViewModel.latestLinkedQuestionResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    Log.i("LinkedFragment", response.data.toString())
                    hideProgressBar()
                    val latestLinkedQuestion = response.data!!
                    if (latestLinkedQuestion.answeredAll) {
                        showCompletedMessage()
                    } else {
                        binding.roundTextView.text = getString(R.string.round_number, latestLinkedQuestion.level)
                        questionAdapter.setLatestQuestion(latestLinkedQuestion)
                        addLockedTabs()
                        binding.questionsViewPager.post {
                            binding.questionsViewPager.currentItem = questionAdapter.itemCount-1
                        }
                    }
                }

                is Resource.Error -> {
                    Log.i("LinkedFragment", response.data.toString())
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                    hideProgressBar()
                }
            }
        }

        linkedViewModel.checkedLinkedAnswerResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    Log.i("LinkedFragment", response.data.toString())
                    val checkedLinkedAnswer = response.data!!
                    if (checkedLinkedAnswer.isCorrectSolution) {
                        Toast.makeText(requireContext()
                                , getString(R.string.correct_answer, checkedLinkedAnswer.marksAwarded), Toast.LENGTH_SHORT).show()
                        showProgressBar()
                        linkedViewModel.sendLatestLinkedQuestionRequest()
                        linkedViewModel.getCurrentScoreRank()
                    } else {
                        Toast.makeText(requireContext(), getString(R.string.wrong_answer), Toast.LENGTH_SHORT).show()
                    }
                }

                is Resource.Error -> {
                    Log.i("LinkedFragment", response.data.toString())
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                    hideProgressBar()
                }
            }
        }

        linkedViewModel.additionalHintResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    Log.i("LinkedFragment", response.data.toString())
                    val additionalHint = response.data!!
                    questionAdapter.addAdditionalHint(additionalHint)
                    hideProgressBar()
                }

                is Resource.Error -> {
                    Log.i("LinkedFragment", response.data.toString())
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                    hideProgressBar()
                }
            }
        }

        linkedViewModel.currentScoreRankResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    Log.i("LinkedFragment", response.data.toString())
                    val currentScoreRank = response.data!!
                    binding.positionTextView.text = currentScoreRank.rank.toString()
                    binding.scoreTextView.text = currentScoreRank.totalScore.toString()
                    hideProgressBar()
                }

                is Resource.Error -> {
                    Log.i("LinkedFragment", response.data.toString())
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                    hideProgressBar()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //binding.questionsTabLayout.setSelectedTabIndicatorColor(Color.GREEN)
        questionAdapter = QuestionAdapter(requireContext(),
                object : QuestionAdapter.OnButtonPressListener {
                    override fun onAnswer(givenAnswer: String) {
                        showProgressBar()
                        linkedViewModel.checkLatestLinkedAnswer(CheckLinkedAnswerRequest(givenAnswer))
                    }

                    override fun onHintRequest() {
                        MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogCustom)
                                .setTitle(getString(R.string.confirm_hint_title))
                                .setMessage(getString(R.string.confirm_hint_body))
                                .setPositiveButton("Yes") { dialog, _ ->
                                    showProgressBar()
                                    linkedViewModel.getLatestLinkedQuestionAdditionalHint()
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

        binding.questionsTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val customView = tab?.customView
                customView?.findViewById<TextView>(R.id.question_number_text_view)?.text = (tab?.position?.plus(1)).toString()
                customView?.findViewById<View>(R.id.selected_circle_view)?.visibility = View.VISIBLE
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val customView = tab?.customView
                customView?.findViewById<TextView>(R.id.question_number_text_view)?.text = (tab?.position?.plus(1)).toString()
                customView?.findViewById<View>(R.id.selected_circle_view)?.visibility = View.INVISIBLE
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        val dummyQuestion = getDummyQuestion()
        questionAdapter.setLatestQuestion(dummyQuestion)
        addLockedTabs()
        binding.roundTextView.text = getString(R.string.round_number, dummyQuestion.level)
        binding.questionsViewPager.post {
            binding.questionsViewPager.currentItem = questionAdapter.itemCount-1
        }
    }

    private fun addLockedTabs() {
        for (i in 0 until binding.questionsTabLayout.tabCount) {
            val tab = binding.questionsTabLayout.getTabAt(i)!!
            tab.customView = null
            tab.setCustomView(R.layout.custom_tab)
            tab.customView?.findViewById<TextView>(R.id.question_number_text_view)?.text = (i+1).toString()
        }
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

    private fun showCompletedMessage() {
        binding.questionsViewPager.visibility = View.GONE
        binding.questionsTabLayout.visibility = View.GONE
        binding.roundTextView.visibility = View.INVISIBLE
        binding.partyPopperImage.visibility = View.VISIBLE
        binding.congratulationsTextView.visibility = View.VISIBLE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

}