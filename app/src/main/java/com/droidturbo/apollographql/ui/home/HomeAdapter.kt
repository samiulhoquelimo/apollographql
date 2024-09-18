package com.droidturbo.apollographql.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.droidturbo.apollographql.R
import com.droidturbo.apollographql.data.model.SimpleCountry
import com.droidturbo.apollographql.databinding.ListItemHomeBinding
import com.droidturbo.apollographql.ui.base.BaseViewHolder

class HomeAdapter : ListAdapter<SimpleCountry, BaseViewHolder>(DiffCallback) {

    private var clicked: ((code: String) -> Unit)? = null

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) = holder.onBind(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_home, parent, false
        )
    )

    inner class ViewHolder(view: View) : BaseViewHolder(view) {
        private val binding = ListItemHomeBinding.bind(view)

        override fun clear() {
            binding.tvCountry.text = null
        }

        override fun onBind(position: Int) {
            val (code, name, emoji, capital) = getItem(position)
            val message = "$emoji $name, $capital, $code"
            binding.tvCountry.text = message
            itemView.setOnClickListener{ clicked?.invoke(code)}
        }
    }

    fun clicked(clicked: (code: String) -> Unit) {
        this.clicked = clicked
    }

    private object DiffCallback : DiffUtil.ItemCallback<SimpleCountry>() {
        override fun areItemsTheSame(oldItem: SimpleCountry, newItem: SimpleCountry): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: SimpleCountry, newItem: SimpleCountry): Boolean =
            oldItem == newItem
    }
}

