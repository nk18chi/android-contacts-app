package com.derrick.park.assignment3_contacts.list


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.derrick.park.assignment3_contacts.R
import com.derrick.park.assignment3_contacts.databinding.FragmentContactsBinding


class ContactsFragment : Fragment() {

    /**
     * Lazily initialize our [OverviewViewModel].
     */
    private val viewModel: ContactsViewModel by lazy {
        ViewModelProviders.of(this).get(ContactsViewModel::class.java)
    }

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentContactsBinding.inflate(inflater)
        val adapter = ContactsAdapter()

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.setLifecycleOwner(this)

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel
        binding.contactList.adapter = adapter

        viewModel.contactsList.observe(this, Observer {
            it?.let {
                adapter.sortByNameAndAddIndex(it)
            }
        })

        viewModel.navigateToAddContact.observe(this, Observer {
            it?.let {
                this.findNavController().navigate(
                        ContactsFragmentDirections.actionContactsFragmentToAddContactFragment())
                viewModel.onAddContactNavigated()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_contact, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_contact -> {
                viewModel.onAddContactClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
