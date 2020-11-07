package edu.nitt.vortex21.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import edu.nitt.vortex21.R
import edu.nitt.vortex21.model.Story
import kotlinx.android.synthetic.main.story_item.view.*

class StoryAdapter(
    private val mContext: Context,
    private val stories: List<Story>,
    private val clickListener:(Story)->Unit
    ) : RecyclerView.Adapter<StoryAdapter.StoryViewHolder>()
{

    class StoryViewHolder(val view: View): RecyclerView.ViewHolder(view){
        var storyImageSeen = view.findViewById(R.id.story_image_seen) as CircleImageView
        var storyImage = view.findViewById(R.id.story_image) as CircleImageView
        var storyName = view.findViewById(R.id.story_name) as TextView
        fun bind(story: Story, clickListener:(Story)->Unit){
            view.story_name.text = story.storyName
            view.setOnClickListener{
                clickListener(story)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.story_item, parent, false)

        return StoryViewHolder(view)
    }

    override fun getItemCount() = stories.size

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
       holder.bind(stories[position],clickListener)
    }
}