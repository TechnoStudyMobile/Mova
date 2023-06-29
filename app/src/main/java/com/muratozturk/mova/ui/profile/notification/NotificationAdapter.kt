package com.muratozturk.mova.ui.profile.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.muratozturk.mova.databinding.ItemNotificationsBinding
import com.muratozturk.mova.domain.model.NotificationUI


class NotificationAdapter(private val notifList: List<NotificationUI>) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationAdapter.NotificationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNotificationsBinding.inflate(layoutInflater,parent,false)
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationAdapter.NotificationViewHolder, position: Int) {
        holder.bind(notifList[position])
    }

    override fun getItemCount(): Int {
        return notifList.size
    }

    inner class NotificationViewHolder(val binding: ItemNotificationsBinding) : ViewHolder(binding.root) {

        fun bind(notif: NotificationUI) {
            binding.itemText.text = notif.text
        }
    }
}

