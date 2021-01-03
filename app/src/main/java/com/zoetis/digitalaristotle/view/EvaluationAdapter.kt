package com.zoetis.digitalaristotle.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zoetis.digitalaristotle.R
import com.zoetis.digitalaristotle.database.AssessmentDB
import com.zoetis.digitalaristotle.databinding.ItemEvaluationBinding
import com.zoetis.digitalaristotle.model.Assessment
import com.zoetis.digitalaristotle.model.Question
import com.zoetis.digitalaristotle.utils.Constants
import com.zoetis.digitalaristotle.utils.Utility
import com.zoetis.digitalaristotle.viewmodel.AssessmentViewModel
import kotlinx.android.extensions.LayoutContainer
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter
import java.util.*

class EvaluationAdapter(val context: AppCompatActivity, private val mViewModel: AssessmentViewModel) :
    RecyclerView.Adapter<EvaluationAdapter.ViewHolder>() {

    private var question: Question = Question()
    private var mList: Assessment = Assessment()
    private var assessmentDB: AssessmentDB = AssessmentDB()

    fun setData(list: Assessment) {
        mList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: ItemEvaluationBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_evaluation,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return if (mList.questions != null) {
            mList.questions.size
        } else {
            0
        }
    }

    private fun setChoices(question: Question, holder: ViewHolder, answer: List<AssessmentDB>) {
        if (question.type == Constants.MULTI_CHOICE) {
            holder.itemBinding.tvChoice1.setHtml("(A) " + question.mcOptions[0])
            holder.itemBinding.tvChoice2.setHtml("(B) " + question.mcOptions[1])
            holder.itemBinding.tvChoice3.setHtml("(C) " + question.mcOptions[2])
            holder.itemBinding.tvChoice4.setHtml("(D) " + question.mcOptions[3])
        }

        if (answer.isNotEmpty()) {
            when (answer[0].mcAnswer) {
                0 -> {
                    holder.itemBinding.tvUnanswered.visibility = View.GONE
                    holder.itemBinding.tvChoice1.background =
                        ContextCompat.getDrawable(context, R.drawable.bg_selected)
                    holder.itemBinding.tvChoice1.setTextColor(ContextCompat.getColor(context, R.color.white))

                }
                1 -> {
                    holder.itemBinding.tvChoice2.setTextColor(ContextCompat.getColor(context, R.color.white))
                    holder.itemBinding.tvUnanswered.visibility = View.GONE
                    holder.itemBinding.tvChoice2.background =
                        ContextCompat.getDrawable(context, R.drawable.bg_selected)
                }
                2 -> {
                    holder.itemBinding.tvChoice3.setTextColor(ContextCompat.getColor(context, R.color.white))
                    holder.itemBinding.tvUnanswered.visibility = View.GONE
                    holder.itemBinding.tvChoice3.background =
                        ContextCompat.getDrawable(context, R.drawable.bg_selected)
                }
                3 -> {
                    holder.itemBinding.tvChoice4.setTextColor(ContextCompat.getColor(context, R.color.white))
                    holder.itemBinding.tvUnanswered.visibility = View.GONE
                    holder.itemBinding.tvChoice4.background =
                        ContextCompat.getDrawable(context, R.drawable.bg_selected)
                }
                else -> {
                    holder.itemBinding.tvUnanswered.visibility = View.VISIBLE
                }
            }

            if (answer[0].type == Constants.SUBJECTIVE) {
                if (answer[0].saImage != "") {
                    holder.itemBinding.ivSaImage.visibility = View.VISIBLE
                    Glide.with(context)
                        .load(Utility.getInstance(context)?.getImageFromBase64(answer[0].saImage)).into(holder.itemBinding.ivSaImage)
                } else {
                    holder.itemBinding.ivSaImage.visibility = View.GONE
                }
            }
        } else {
            holder.itemBinding.tvUnanswered.visibility = View.VISIBLE
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question = mList.questions[position]
        holder.itemBinding.tvQuestion.setHtml(
            question.text,
            HtmlHttpImageGetter(holder.itemBinding.tvQuestion)
        )

        assessmentDB = AssessmentDB()
        assessmentDB.saAnswer = ""

        when {
            question.type.toUpperCase(Locale.ROOT) == Constants.MULTI_CHOICE -> {
                holder.itemBinding.llMultiChoice.visibility = View.VISIBLE
                holder.itemBinding.llSubjective.visibility = View.GONE
                mViewModel.getAnswerByQNo(question.qno).observe(context, { answer ->
                    setChoices(question, holder, answer)
                })
            }
            question.type.toUpperCase(Locale.ROOT) == Constants.SUBJECTIVE -> {
                holder.itemBinding.llMultiChoice.visibility = View.GONE
                holder.itemBinding.llSubjective.visibility = View.VISIBLE

                mViewModel.getAnswerByQNo(question.qno).observe(context, { answer ->
                    setChoices(question, holder, answer)
                })
            }
            else -> {
                holder.itemBinding.llMultiChoice.visibility = View.GONE
                holder.itemBinding.llSubjective.visibility = View.GONE
            }
        }
    }

    class ViewHolder(var itemBinding: ItemEvaluationBinding) :
        RecyclerView.ViewHolder(itemBinding.root), LayoutContainer {
        override val containerView: View
            get() = itemBinding.root
    }

}