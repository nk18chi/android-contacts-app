package com.derrick.park.assignment3_contacts.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.derrick.park.assignment3_contacts.databinding.ListViewContactItemBinding
import com.derrick.park.assignment3_contacts.network.ContactsProperty

class ContactsAdapter : ListAdapter<ContactsProperty, ContactsAdapter.ContactsPropertyViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsAdapter.ContactsPropertyViewHolder {
        println("Run adapter onCreateViewHolder")
        return ContactsPropertyViewHolder(ListViewContactItemBinding.inflate(
                LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ContactsPropertyViewHolder, position: Int) {
        val contactsProperty = getItem(position)
        holder.bind(contactsProperty)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ContactsProperty>() {
        override fun areItemsTheSame(oldItem: ContactsProperty, newItem: ContactsProperty): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ContactsProperty, newItem: ContactsProperty): Boolean {
            return oldItem == newItem
        }
    }

    class ContactsPropertyViewHolder(private var binding: ListViewContactItemBinding):
            RecyclerView.ViewHolder(binding.root) {
        fun bind(contactsProperty: ContactsProperty) {
            println("Run adapter bind")
            binding.contact = contactsProperty
            binding.executePendingBindings()
        }

    }
}