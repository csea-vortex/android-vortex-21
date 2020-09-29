package edu.nitt.vortex.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import edu.nitt.vortex.R
import edu.nitt.vortex.model.SocialMediaLink

class SocialMediaLinksAdapter(
    private val context: Context,
    private val data: Array<SocialMediaLink>
) : RecyclerView.Adapter<SocialMediaLinksAdapter.SocialMediaLinkViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SocialMediaLinkViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_item_social_media, parent, false)
                as ConstraintLayout
        return SocialMediaLinkViewHolder(view)
    }

    override fun onBindViewHolder(holder: SocialMediaLinkViewHolder, position: Int) {
        holder.icon.setImageResource(data[position].iconId)
        holder.name.text = data[position].name
        holder.container.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(data[position].url))
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = data.size

    class SocialMediaLinkViewHolder(val view: ConstraintLayout) : RecyclerView.ViewHolder(view) {
        val container = view.findViewById(R.id.container) as ConstraintLayout
        val icon = view.findViewById(R.id.icon) as CircleImageView
        val name = view.findViewById(R.id.name) as TextView
    }

}
