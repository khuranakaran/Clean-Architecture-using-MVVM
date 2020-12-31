package com.zoetis.digitalaristotle.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.zoetis.digitalaristotle.R
import com.zoetis.digitalaristotle.databinding.ItemAssessmentBinding
import com.zoetis.digitalaristotle.model.Assessment
import kotlinx.android.extensions.LayoutContainer

class AssessmentAdapter : RecyclerView.Adapter<AssessmentAdapter.ViewHolder>() {

    private lateinit var mList: Assessment

    fun setData(list: Assessment) {
        mList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: ItemAssessmentBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_assessment,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mList.questions.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.itemBinding.country = mList!![position]
        holder.itemBinding.tvQuestion.text = mList.questions[position].text
    }

    class ViewHolder(var itemBinding: ItemAssessmentBinding) :
        RecyclerView.ViewHolder(itemBinding.root), LayoutContainer {
        override val containerView: View
            get() = itemBinding.root
    }
}