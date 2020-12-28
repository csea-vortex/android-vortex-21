package edu.nitt.vortex2021.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import edu.nitt.vortex2021.databinding.StoryItemBinding
import edu.nitt.vortex2021.helpers.Constants
import edu.nitt.vortex2021.model.Story

class StoryTrayAdapter(
    private var stories: List<Story>,
    private val clickListener: (Int) -> Unit
) : RecyclerView.Adapter<StoryTrayAdapter.StoryViewHolder>() {

    inner class StoryViewHolder(val binding: StoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(index: Int, story: Story, clickListener: (Int) -> Unit) {
            binding.storyName.text = story.title
            try {
                Picasso.get()
                    .load(Constants.BACKEND_BASE_URL + story.slides[0].imageUrl)
                    .into(binding.storyImage, object : Callback {
                        override fun onSuccess() = Unit
                        override fun onError(e: Exception?) = Unit
                    })
            } catch (e: Exception) {
            }

            binding.root.setOnClickListener {
                clickListener(index)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = StoryItemBinding.inflate(
            LayoutInflater
                .from(parent.context), parent, false
        )
        return StoryViewHolder(binding)
    }

    override fun getItemCount() = stories.size

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(position, stories[position], clickListener)
    }
}
