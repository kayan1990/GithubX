package com.kayan.github.module.dynamic

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.kayan.github.kotlin.R
import com.kayan.github.common.utils.EventUtils
import com.kayan.github.kotlin.databinding.FragmentListBinding
import com.kayan.github.model.ui.EventUIModel
import com.kayan.github.module.base.BaseListFragment
import com.kayan.github.ui.holder.EventHolder
import com.shuyu.commonrecycler.BindSuperAdapterManager
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * 动态
 * Created by kayan
 * Date: 2022-06-18
 */

class DynamicFragment : BaseListFragment<FragmentListBinding, DynamicViewModel>() {


    override fun getLayoutId(): Int {
        return R.layout.fragment_list
    }

    override fun onItemClick(context: Context, position: Int) {
        super.onItemClick(context, position)
        EventUtils.evenAction(activity, adapter?.dataList?.get(position) as EventUIModel)
    }

    override fun getViewModelClass(): Class<DynamicViewModel> = DynamicViewModel::class.java

    override fun enableRefresh(): Boolean = true

    override fun enableLoadMore(): Boolean = true

    override fun getRecyclerView(): RecyclerView? = baseRecycler

    override fun bindHolder(manager: BindSuperAdapterManager) {
        manager.bind(EventUIModel::class.java, EventHolder.ID, EventHolder::class.java)
    }
}