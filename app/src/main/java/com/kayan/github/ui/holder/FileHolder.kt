package com.kayan.github.ui.holder

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import com.kayan.github.kotlin.R
import com.kayan.github.kotlin.databinding.LayoutFileItemBinding
import com.kayan.github.model.ui.FileUIModel
import com.kayan.github.ui.holder.base.DataBindingHolder


/**
 * 文件显示item
 */
class FileHolder(context: Context, private val v: View, dataBing: ViewDataBinding) : DataBindingHolder<FileUIModel, LayoutFileItemBinding>(context, v, dataBing) {


    override fun onBind(model: FileUIModel, position: Int, dataBing: LayoutFileItemBinding) {
        dataBing.fileUIModel = model
    }

    companion object {
        const val ID = R.layout.layout_file_item
    }
}