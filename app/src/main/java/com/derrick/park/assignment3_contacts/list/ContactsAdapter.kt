package com.derrick.park.assignment3_contacts.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.derrick.park.assignment3_contacts.R
import com.derrick.park.assignment3_contacts.databinding.ListViewContactItemBinding
import com.derrick.park.assignment3_contacts.network.ContactsProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactsAdapter : ListAdapter<ContactDataItem, RecyclerView.ViewHolder>(DiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return ContactsPropertyViewHolder(ListViewContactItemBinding.inflate(
//                LayoutInflater.from(parent.context)))

        return when (viewType) {
            0 -> IndexViewHolder.from(parent)
            1 -> ContactsPropertyViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ContactsPropertyViewHolder -> {
                val contact = getItem(position) as ContactDataItem.ContactItem
                holder.bind(contact.contact)
            }
            is IndexViewHolder -> {
                holder.bind(getItem(position).id)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ContactDataItem.IndexItem -> 0
            is ContactDataItem.ContactItem -> 1
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ContactDataItem>() {
        override fun areItemsTheSame(oldItem: ContactDataItem, newItem: ContactDataItem): Boolean {
            return oldItem.id === newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: ContactDataItem, newItem: ContactDataItem): Boolean {
            return oldItem == newItem
        }
    }

    class ContactsPropertyViewHolder(private var binding: ListViewContactItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(contactsProperty: ContactsProperty) {
            binding.contact = contactsProperty
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ContactsPropertyViewHolder {
                return ContactsPropertyViewHolder(ListViewContactItemBinding.inflate(LayoutInflater.from(parent.context)))
            }
        }
    }

    class IndexViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        fun bind(index: String) {
            view.findViewById<TextView>(R.id.textView_index).text = index
        }

        companion object {
            fun from(parent: ViewGroup): IndexViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.list_view_index_item, parent, false)
                return IndexViewHolder(view)
            }
        }
    }

    fun sortByNameAndAddIndex(list: List<ContactsProperty>) {
        adapterScope.launch {
            val contactList = list.sortedWith(compareBy({it.name.first}))

            val items = ArrayList<ContactDataItem>()
            var char = contactList[0].name.first[0].toUpperCase()
            items.add(ContactDataItem.IndexItem(char))
            for (l in contactList) {
                var curChar = l.name.first[0].toUpperCase()
                if (char != curChar) {
                    char = curChar
                    items.add(ContactDataItem.IndexItem(char))
                }
                items.add(ContactDataItem.ContactItem(l))
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }
}

sealed class ContactDataItem {
    abstract val id: String

    data class ContactItem(val contact: ContactsProperty) : ContactDataItem() {
        override val id = contact.login.uuid
    }

    data class IndexItem(val index: Char) : ContactDataItem() {
        override val id = index.toString()
    }
}