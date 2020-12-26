package edu.nitt.vortex2021.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.nitt.vortex2021.databinding.ListItemLeaderboardBinding
import edu.nitt.vortex2021.model.LeaderboardRow


class LeaderboardAdapter(
    private val rows: List<LeaderboardRow>,
    private val clickListener: (row: LeaderboardRow) -> Unit
) : RecyclerView.Adapter<LeaderboardAdapter.LeaderboardRowViewHolder>() {

    private var selectedRowIndex: Int = -1

    inner class LeaderboardRowViewHolder(val binding: ListItemLeaderboardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(index: Int, row: LeaderboardRow, clickListener: (row: LeaderboardRow) -> Unit) {
            binding.rank.text = row.rank.toString()
            binding.username.text = row.username
            binding.score.text = row.score.toString()
            binding.collegeName.text = row.college

            binding.root.setOnClickListener {
                clickListener(row)
                selectedRowIndex = index
                notifyDataSetChanged()
            }

            if (selectedRowIndex == index) {
                binding.collegeName.visibility = View.VISIBLE
            } else {
                binding.collegeName.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardRowViewHolder {
        val binding = ListItemLeaderboardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LeaderboardRowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LeaderboardRowViewHolder, position: Int) {
        holder.bind(position, rows[position], clickListener)
    }

    override fun getItemCount() = rows.size
}
