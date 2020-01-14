package com.derrick.park.assignment3_contacts.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.derrick.park.assignment3_contacts.network.ContactsProperty

class AddContactViewModel : ViewModel() {
    private val _navigateToContactList = MutableLiveData<ContactsProperty>()
    val navigateToContactList: LiveData<ContactsProperty>
        get() = _navigateToContactList

    fun onSubmitClicked(contact: ContactsProperty) {
        _navigateToContactList.value = contact
    }

    fun onSubmitNavigated() {
        _navigateToContactList.value = null
    }
}