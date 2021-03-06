package com.kayan.github.ui.holder

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import com.kayan.github.kotlin.R
import com.kayan.github.kotlin.databinding.LayoutPushItemBinding
import com.kayan.github.model.ui.FileUIModel
import com.kayan.github.ui.holder.base.DataBindingHolder


/**
 * 提交文件显示item
 */
class PushHolder(context: Context, private val v: View, dataBing: ViewDataBinding) : DataBindingHolder<FileUIModel, LayoutPushItemBinding>(context, v, dataBing) {


    override fun onBind(model: FileUIModel, position: Int, dataBing: LayoutPushItemBinding) {
        dataBing.fileUIModel = model
    }

    companion object {
        const val ID = R.layout.layout_push_item
    }
}