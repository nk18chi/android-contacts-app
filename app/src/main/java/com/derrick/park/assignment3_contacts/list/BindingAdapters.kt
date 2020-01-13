package com.derrick.park.assignment3_contacts.list

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("fullNameString")
fun setFullNameString(textView: TextView, fullName: String?) {
    fullName?.let { textView.text = it }
}

@BindingAdapter("cellString")
fun setCellString(textView: TextView, cell: String?) {
    cell?.let { textView.text = it }
}