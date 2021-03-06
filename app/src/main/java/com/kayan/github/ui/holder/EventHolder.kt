package com.kayan.github.ui.holder

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import com.kayan.github.kotlin.R
import com.kayan.github.kotlin.databinding.LayoutEventItemBinding
import com.kayan.github.model.ui.EventUIModel
import com.kayan.github.module.person.PersonActivity
import com.kayan.github.ui.holder.base.DataBindingHolder
import kotlinx.android.synthetic.main.layout_event_item.view.*

/**
 * 事件item
 */
class EventHolder(context: Context,private val v: View, dataBing: ViewDataBinding) : DataBindingHolder<EventUIModel, LayoutEventItemBinding>(context, v, dataBing) {


    override fun createView(v: View) {
        super.createView(v)
    }

    override fun onBind(model: EventUIModel, position: Int, dataBing: LayoutEventItemBinding) {
        dataBing.eventUIModel = model
        v.event_user_img.setOnClickListener {
            PersonActivity.gotoPersonInfo(model.username)
        }
    }

    companion object {
        const val ID = R.layout.layout_event_item
    }
}