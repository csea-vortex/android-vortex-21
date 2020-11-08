package edu.nitt.vortex21.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import edu.nitt.vortex21.R
import edu.nitt.vortex21.databinding.StoryItemBinding
import edu.nitt.vortex21.model.Story
import kotlinx.android.synthetic.main.story_item.view.*

class StoryAdapter(
    private var stories: List<Story>,
    private val clickListener: (Int) -> Unit
) : RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    class StoryViewHolder(val binding: StoryItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(index: Int, story: Story, clickListener: (Int)->Unit){
            binding.storyName.text = story.storyName
            binding.root.setOnClickListener{
                clickListener(index)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = StoryItemBinding.inflate(LayoutInflater
            .from(parent.context),parent,false)
        return StoryViewHolder(binding)
    }

    override fun getItemCount() = stories.size

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
       holder.bind(position, stories[position], clickListener)
    }
}
