package com.derrick.park.assignment3_contacts.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.derrick.park.assignment3_contacts.network.ContactsProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ContactsViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the most recent response
    private val _contactsList = MutableLiveData<List<ContactsProperty>>()

    // The external immutable LiveData for the response String
    val contactsList: LiveData<List<ContactsProperty>>
        get() = _contactsList

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getContactProperties()
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     */
    private fun getContactProperties() {
        coroutineScope.launch {
            val getContactsDeferred = ContactsApi.retrofitService.getProperties(20)
            try {
                val listResult = getContactsDeferred.await().contactList
                _contactsList.value = listResult
            } catch (e: Exception) {
                _contactsList.value = ArrayList()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}