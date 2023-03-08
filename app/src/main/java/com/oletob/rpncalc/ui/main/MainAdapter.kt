package com.oletob.rpncalc.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.oletob.rpncalc.databinding.NumberItemBinding

class MainAdapter(private val context: Context) : BaseAdapter() {

    private lateinit var binding: NumberItemBinding

    var numbers: MutableList<String> = mutableListOf("0")

    override fun getCount() = numbers.size

    override fun getItem(position: Int) = numbers[position]

    override fun getItemId(position: Int) = position.toLong()

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        binding = NumberItemBinding.inflate(LayoutInflater.from(context), parent, false)

        binding.textViewItem.text = numbers[position]

        return binding.root
    }
}