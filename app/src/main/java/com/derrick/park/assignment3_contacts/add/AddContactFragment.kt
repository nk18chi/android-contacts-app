package com.derrick.park.assignment3_contacts.add

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.derrick.park.assignment3_contacts.R
import com.derrick.park.assignment3_contacts.databinding.FragmentAddContactBinding
import com.derrick.park.assignment3_contacts.network.ContactsProperty
import java.util.*

class AddContactFragment : Fragment() {

    private val viewModel: AddContactViewModel by lazy {
        ViewModelProviders.of(this).get(AddContactViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentAddContactBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.navigateToContactList.observe(this, Observer {
            it?.let {
                this.findNavController().navigate(
                        AddContactFragmentDirections.actionAddContactFragmentToContactsFragment(it)
                )
                viewModel.onSubmitNavigated()
            }
        })

        binding.buttonSubmitAddContact.setOnClickListener {
            val tokens = StringTokenizer(binding.editTextName.text.toString(), " ")
            val cellNum = binding.editTextPhoneNumber.text.toString()
            viewModel.onSubmitClicked(
                    ContactsProperty.generateContact(
                            tokens.nextToken().capitalize(),
                            tokens.nextToken().capitalize(),
                            cellNum.substring(0, 3) + "-"
                                    + cellNum.substring(3, 6) + "-"
                                    + cellNum.substring(6)
                    )
            )
            val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        }

        binding.editTextName.doAfterTextChanged {
            checkAndSetSubmitEnabled(binding)
        }

        binding.editTextPhoneNumber.doAfterTextChanged {
            checkAndSetSubmitEnabled(binding)
        }

        return binding.root
    }

    private fun checkAndSetSubmitEnabled(binding: FragmentAddContactBinding) {
        binding.buttonSubmitAddContact.isEnabled =
                ContactsProperty.isNameAvailable(binding.editTextName.text.toString()) &&
                        ContactsProperty.isCellAvailable(
                                binding.editTextPhoneNumber.text.toString()
                        )
    }

}
