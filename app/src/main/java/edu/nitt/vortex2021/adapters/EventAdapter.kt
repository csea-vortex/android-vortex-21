package edu.nitt.vortex2021.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import edu.nitt.vortex2021.databinding.EventItemBinding
import edu.nitt.vortex2021.fragments.EventsFragmentDirections
import edu.nitt.vortex2021.helpers.Constants
import edu.nitt.vortex2021.model.EventList

class EventAdapter(val eventList:List<EventList>) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {
    inner class EventViewHolder(val binding:EventItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(EventItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
       return eventList.size
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val title = eventList[position].eventData.title
        holder.binding.playButton.isEnabled = false
        holder.binding.eventNameText.text = title
        if(title.equals("Linked")){
            holder.binding.ratingBar.visibility = View.VISIBLE
        }
        Picasso.get().load(Constants.BACKEND_BASE_URL + eventList[position].eventData.smallImage).into(holder.binding.eventLogo)
        if(eventList[position].isRegistered){
            holder.binding.playButton.isEnabled = true
        }
        holder.binding.playButton.setOnClickListener {
            it.findNavController().navigate(EventsFragmentDirections.actionFragmentEventsToInstructionFragment(position))
        }
        holder.binding.registerButton.setOnClickListener {
            //TODO(): call register route
            holder.binding.playButton.isEnabled = true
            holder.binding.statusTextView.text = "You are ready to go"
                holder.binding.registerButton.isEnabled = false
        }

    }

}