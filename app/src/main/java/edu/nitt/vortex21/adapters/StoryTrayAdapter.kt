package edu.nitt.vortex21.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import edu.nitt.vortex21.databinding.StoryItemBinding
import edu.nitt.vortex21.helpers.Constants
import edu.nitt.vortex21.model.Story
import java.lang.Exception

class StoryTrayAdapter(
    private var stories: List<Story>,
    private val clickListener: (Int) -> Unit
) : RecyclerView.Adapter<StoryTrayAdapter.StoryViewHolder>() {

    class StoryViewHolder(val binding: StoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(index: Int, story: Story, clickListener: (Int) -> Unit) {
           binding.storyName.text = story.title

            try {
                Picasso.get()
                    .load(Constants.BACKEND_BASE_URL + story.slides[0].imageUrl)
                    .into(binding.storyImage, object : Callback {
                        override fun onSuccess() {

                        }

                        override fun onError(e: Exception?) {

                            e?.stackTrace
                        }

                    })
            }catch (e:Exception){
                e.stackTrace
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
