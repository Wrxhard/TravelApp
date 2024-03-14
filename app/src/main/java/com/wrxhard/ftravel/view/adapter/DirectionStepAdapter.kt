package com.wrxhard.ftravel.view.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wrxhard.ftravel.databinding.StepItemLayoutBinding
import com.wrxhard.ftravel.model.google.DirectionStepModel

class DirectionStepAdapter :
    RecyclerView.Adapter<DirectionStepAdapter.ViewHolder>() {

    private var directionStepModels: List<DirectionStepModel>? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = StepItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        directionStepModels?.let {
            val stepModel = it[position]
            val htmlInstructions = stepModel.getHtmlInstructions()
            val formattedHtmlInstructions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(htmlInstructions, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(htmlInstructions)
            }

            with(holder.binding) {
                txtStepHtml.text = formattedHtmlInstructions
                txtStepTime.text = stepModel.getDuration().getText()
                txtStepDistance.text = stepModel.getDistance().getText()
            }
        }
    }

    override fun getItemCount(): Int {
        return directionStepModels?.size ?: 0
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDirectionStepModels(directionStepModels: List<DirectionStepModel>?) {
        this.directionStepModels = directionStepModels
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: StepItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}
