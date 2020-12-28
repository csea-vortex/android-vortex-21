package edu.nitt.vortex2021.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import edu.nitt.vortex2021.databinding.ListItemEventBinding
import edu.nitt.vortex2021.helpers.AppSupportedEvents
import edu.nitt.vortex2021.helpers.Constants
import edu.nitt.vortex2021.helpers.getEventFromTitle
import edu.nitt.vortex2021.model.Event
import java.util.*

class EventAdapter(
    private val eventList: List<Event>,
    private val onPlayButtonClickListener: (event: Event) -> Unit,
    private val onRegisterEventButtonClickListener: (event: Event) -> Unit,
    private val onLeaderboardButtonClickListener: (event: Event) -> Unit
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    inner class EventViewHolder(val binding: ListItemEventBinding) : RecyclerView.ViewHolder(binding.root) {

        private fun bindPlayButton(event: Event) {
            // ToDo: Get user's progress
            // ToDo: Show when the event starts if not yet started
            val data = event.eventData
            val isEventStarted = System.currentTimeMillis() >= data.eventFrom.time
            val isEventEnded = System.currentTimeMillis() >= data.eventTo.time
            val isEventOngoing = isEventStarted && !isEventEnded

            binding.playButton.apply {
                isEnabled = event.isRegistered && isEventOngoing
                visibility = if (event.isRegistered) View.VISIBLE else View.GONE
                setOnClickListener { onPlayButtonClickListener(event) }
            }
        }

        private fun bindRegisterButton(event: Event) {
            // Todo: Show Registered / Event Over Status similar to website
            val isEventFinished = System.currentTimeMillis() >= event.eventData.eventTo.time
            val isButtonVisible = !(isEventFinished || event.isRegistered)

            binding.registerButton.apply {
                isEnabled = isButtonVisible
                visibility = if (isButtonVisible) View.VISIBLE else View.GONE
                setOnClickListener { onRegisterEventButtonClickListener(event) }
            }
        }

        private fun bindLeaderboardButton(event: Event) {
            val leaderboardSupported = AppSupportedEvents.LINKED == getEventFromTitle(event.eventData.title)
            binding.leaderBoardButton.apply {
                isEnabled = leaderboardSupported
                visibility = if (leaderboardSupported) View.VISIBLE else View.GONE
                setOnClickListener { onLeaderboardButtonClickListener(event) }
            }
        }

        fun bind(event: Event) {
            binding.eventNameText.text = event.eventData.title
            bindPlayButton(event)
            bindRegisterButton(event)
            bindLeaderboardButton(event)

            try {
                Picasso.get().load(Constants.BACKEND_BASE_URL + event.eventData.smallImage)
                    .into(binding.eventLogo, object : Callback {
                        override fun onSuccess() = Unit
                        override fun onError(e: Exception?) {
                            e?.stackTrace
                        }
                    })
            } catch (e: Exception) {
                e.stackTrace
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ListItemEventBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EventViewHolder(binding)
    }

    override fun getItemCount() = eventList.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(eventList[position])
    }

}