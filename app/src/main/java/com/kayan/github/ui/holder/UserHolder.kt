package com.kayan.github.ui.holder

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import com.kayan.github.kotlin.R
import com.kayan.github.kotlin.databinding.LayoutUserItemBinding
import com.kayan.github.model.ui.UserUIModel
import com.kayan.github.ui.holder.base.DataBindingHolder


/**
 * 用户item
 */
class UserHolder(context: Context, private val v: View, dataBing: ViewDataBinding) : DataBindingHolder<UserUIModel, LayoutUserItemBinding>(context, v, dataBing) {

    override fun onBind(model: UserUIModel, position: Int, dataBing: LayoutUserItemBinding) {
        dataBing.userUIModel = model
    }

    companion object {
        const val ID = R.layout.layout_user_item
    }
}