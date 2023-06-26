package com.muratozturk.mova.ui.mylist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.muratozturk.mova.databinding.ItemFilterCategoryBinding
import com.muratozturk.mova.domain.model.MyListCategoryUI

class MyListCategoryAdapter(
    private val categories: List<MyListCategoryUI>
) : RecyclerView.Adapter<MyListCategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyListCategoryAdapter.CategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemFilterCategoryBinding.inflate(layoutInflater, parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyListCategoryAdapter.CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    inner class CategoryViewHolder(val binding: ItemFilterCategoryBinding) :
        ViewHolder(binding.root) {
        fun bind(category: MyListCategoryUI) {
            with(binding) {
                categoryCheckBox.text = category.text

                categoryCheckBox.setOnClickListener {
                    val checkBox = it as CheckBox
                    checkBox.isChecked = true
                    categories.forEachIndexed { i, myListCategoryUI ->
                        if (myListCategoryUI.text != checkBox.text.toString()) {
                            myListCategoryUI.isChecked = false
                            notifyItemChanged(i)
                        }
                    }
                }

                categoryCheckBox.isChecked = category.isChecked
            }
        }
    }
}