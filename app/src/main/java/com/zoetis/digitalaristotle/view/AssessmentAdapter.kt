package com.zoetis.digitalaristotle.view

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.zoetis.digitalaristotle.R
import com.zoetis.digitalaristotle.database.AssessmentDB
import com.zoetis.digitalaristotle.databinding.ItemAssessmentBinding
import com.zoetis.digitalaristotle.interfaces.ImagePickerCallback
import com.zoetis.digitalaristotle.model.Assessment
import com.zoetis.digitalaristotle.model.Question
import com.zoetis.digitalaristotle.utils.Constants
import com.zoetis.digitalaristotle.viewmodel.AssessmentViewModel
import kotlinx.android.extensions.LayoutContainer
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter
import java.util.*


class AssessmentAdapter(
    val context: AppCompatActivity,
    private val mViewModel: AssessmentViewModel,
    private val imagePickerCallback: ImagePickerCallback
) :
    RecyclerView.Adapter<AssessmentAdapter.ViewHolder>() {

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
        val binding: ItemAssessmentBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_assessment,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (mList.questions != null) {
            mList.questions.size
        } else {
            0
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question = mList.questions[position]
        /*holder.itemBinding.tvQuestion.setHtml(
            question.text,
            HtmlHttpImageGetter(holder.itemBinding.tvQuestion)
        )*/

        holder.itemBinding.tvQuestion.setDisplayText(question.text)
        assessmentDB = AssessmentDB()
        assessmentDB.saAnswer = ""

        when {
            question.type.toUpperCase(Locale.ROOT) == Constants.MULTI_CHOICE -> {
                holder.itemBinding.llMultiChoice.visibility = View.VISIBLE
                holder.itemBinding.llSubjective.visibility = View.GONE

                setChoices(question, holder)
            }
            question.type.toUpperCase(Locale.ROOT) == Constants.SUBJECTIVE -> {
                holder.itemBinding.llMultiChoice.visibility = View.GONE
                holder.itemBinding.llSubjective.visibility = View.VISIBLE
            }
            else -> {
                holder.itemBinding.llMultiChoice.visibility = View.GONE
                holder.itemBinding.llSubjective.visibility = View.GONE
            }
        }

        holder.itemBinding.etSaAnswer.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                assessmentDB.type= Constants.SUBJECTIVE
                assessmentDB.saAnswer = s.toString()
                assessmentDB.qno = question.qno.toString()
                assessmentDB.mcAnswer = -1

                mViewModel.insertAnswer(assessmentDB)
            }
        })

        holder.itemBinding.ivAttach.setOnClickListener {
            assessmentDB.qno = question.qno.toString()
            assessmentDB.type = question.type

            imagePickerCallback.pickImage(assessmentDB)
        }

        updateAnswerMC(holder, position)
    }

    private fun setAnswerAs(answerPosition: Int, question: Question) {
        assessmentDB.type = question.type
        assessmentDB.qno = question.qno.toString()
        assessmentDB.mcAnswer = answerPosition
        mViewModel.insertAnswer(assessmentDB)
    }



    private fun setChoices(question: Question, holder: ViewHolder) {
        holder.itemBinding.tvChoice1.setDisplayText( question.mcOptions[0])
        holder.itemBinding.tvChoice2.setDisplayText(question.mcOptions[1])
        holder.itemBinding.tvChoice3.setDisplayText(question.mcOptions[2])
        holder.itemBinding.tvChoice4.setDisplayText(question.mcOptions[3])
    }

    class ViewHolder(var itemBinding: ItemAssessmentBinding) :
        RecyclerView.ViewHolder(itemBinding.root), LayoutContainer {
        override val containerView: View
            get() = itemBinding.root
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    private fun updateAnswerMC(holder: ViewHolder, position: Int) {
        holder.itemBinding.tvChoice1.setOnClickListener {
            holder.itemBinding.llChoice1.background = ContextCompat.getDrawable(
                context,
                R.drawable.bg_selected
            )

           /* holder.itemBinding.tvChoice1.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.white
                )
            )
            holder.itemBinding.llChoice2.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.black
                )
            )
            holder.itemBinding.llChoice3.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.black
                )
            )
            holder.itemBinding.llChoice4.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.black
                )
            )*/

            holder.itemBinding.llChoice2.background = ContextCompat.getDrawable(
                context,
                R.drawable.bg_unselected
            )
            holder.itemBinding.llChoice3.background = ContextCompat.getDrawable(
                context,
                R.drawable.bg_unselected
            )
            holder.itemBinding.llChoice4.background = ContextCompat.getDrawable(
                context,
                R.drawable.bg_unselected
            )

            question = mList.questions[position]
            setAnswerAs(0, question)
        }

        holder.itemBinding.llChoice2.setOnClickListener {
            holder.itemBinding.llChoice1.background = ContextCompat.getDrawable(
                context,
                R.drawable.bg_unselected
            )

            /*holder.itemBinding.llChoice1.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.black
                )
            )
            holder.itemBinding.llChoice2.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.white
                )
            )
            holder.itemBinding.llChoice3.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.black
                )
            )
            holder.itemBinding.llChoice4.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.black
                )
            )*/
            holder.itemBinding.llChoice2.background = ContextCompat.getDrawable(
                context,
                R.drawable.bg_selected
            )
            holder.itemBinding.llChoice3.background = ContextCompat.getDrawable(
                context,
                R.drawable.bg_unselected
            )
            holder.itemBinding.llChoice4.background = ContextCompat.getDrawable(
                context,
                R.drawable.bg_unselected
            )

            question = mList.questions[position]
            setAnswerAs(1, question)
        }

        holder.itemBinding.llChoice3.setOnClickListener {
            /*holder.itemBinding.llChoice1.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.black
                )
            )
            holder.itemBinding.llChoice2.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.black
                )
            )
            holder.itemBinding.llChoice3.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.white
                )
            )
            holder.itemBinding.llChoice4.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.black
                )
            )*/

            holder.itemBinding.llChoice1.background = ContextCompat.getDrawable(
                context,
                R.drawable.bg_unselected
            )
            holder.itemBinding.llChoice2.background = ContextCompat.getDrawable(
                context,
                R.drawable.bg_unselected
            )
            holder.itemBinding.llChoice3.background = ContextCompat.getDrawable(
                context,
                R.drawable.bg_selected
            )
            holder.itemBinding.llChoice4.background = ContextCompat.getDrawable(
                context,
                R.drawable.bg_unselected
            )

            question = mList.questions[position]
            setAnswerAs(2, question)
        }

        holder.itemBinding.llChoice4.setOnClickListener {

           /* holder.itemBinding.llChoice1.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.black
                )
            )
            holder.itemBinding.llChoice2.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.black
                )
            )
            holder.itemBinding.llChoice3.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.black
                )
            )
            holder.itemBinding.llChoice4.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.white
                )
            )
*/
            holder.itemBinding.llChoice1.background = ContextCompat.getDrawable(
                context,
                R.drawable.bg_unselected
            )
            holder.itemBinding.llChoice2.background = ContextCompat.getDrawable(
                context,
                R.drawable.bg_unselected
            )
            holder.itemBinding.llChoice3.background = ContextCompat.getDrawable(
                context,
                R.drawable.bg_unselected
            )
            holder.itemBinding.llChoice4.background = ContextCompat.getDrawable(
                context,
                R.drawable.bg_selected
            )

            question = mList.questions[position]
            setAnswerAs(3, question)
        }
    }
}