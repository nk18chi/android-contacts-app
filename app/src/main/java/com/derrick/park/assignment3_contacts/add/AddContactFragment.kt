package com.derrick.park.assignment3_contacts.add

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.derrick.park.assignment3_contacts.R

class AddContactFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        println("Run! add fragment")
        return inflater.inflate(R.layout.fragment_add_contact, container, false)
    }
}
