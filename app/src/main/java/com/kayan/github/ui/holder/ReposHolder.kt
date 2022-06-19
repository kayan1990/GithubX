package com.kayan.github.ui.holder

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import com.kayan.github.kotlin.R
import com.kayan.github.kotlin.databinding.LayoutReposItemBinding
import com.kayan.github.model.ui.ReposUIModel
import com.kayan.github.module.person.PersonActivity
import com.kayan.github.ui.holder.base.DataBindingHolder
import kotlinx.android.synthetic.main.layout_repos_item.view.*


/**
 * 仓库item
 */
class ReposHolder(context: Context, private val v: View, dataBing: ViewDataBinding) : DataBindingHolder<ReposUIModel, LayoutReposItemBinding>(context, v, dataBing) {


    override fun onBind(model: ReposUIModel, position: Int, dataBing: LayoutReposItemBinding) {
        dataBing.reposUIModel = model
        v.repos_user_img.setOnClickListener {
            PersonActivity.gotoPersonInfo(model.ownerName)
        }
    }

    companion object {
        const val ID = R.layout.layout_repos_item
    }
}