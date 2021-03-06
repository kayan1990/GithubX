package com.kayan.github.module.trend

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.recyclerview.widget.RecyclerView
import com.kayan.github.kotlin.R
import com.kayan.github.kotlin.databinding.FragmentTrendBinding
import com.kayan.github.model.ui.ReposUIModel
import com.kayan.github.module.base.BaseListFragment
import com.kayan.github.module.repos.ReposDetailActivity
import com.kayan.github.ui.adapter.ListDropDownAdapter
import com.kayan.github.ui.holder.ReposHolder
import com.shuyu.commonrecycler.BindSuperAdapterManager
import kotlinx.android.synthetic.main.fragment_trend.*


/**
 * 趋势
 * Created by kayan
 * Date: 2022-06-18
 */

class TrendFragment : BaseListFragment<FragmentTrendBinding, TrendViewModel>() {

    private lateinit var baseRecycler: RecyclerView

    override fun getLayoutId(): Int {
        return R.layout.fragment_trend
    }

    override fun onCreateView(mainView: View?) {
        super.onCreateView(mainView)

        baseRecycler = RecyclerView(activity!!)
        baseRecycler.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDropLists(context)
    }

    override fun onItemClick(context: Context, position: Int) {
        super.onItemClick(context, position)
        adapter?.dataList?.get(position).apply {
            val data = this as ReposUIModel
            ReposDetailActivity.gotoReposDetail(data.ownerName, data.repositoryName)
        }
    }

    override fun getViewModelClass(): Class<TrendViewModel> = TrendViewModel::class.java

    override fun enableRefresh(): Boolean = true

    override fun enableLoadMore(): Boolean = false

    override fun getRecyclerView(): RecyclerView? = baseRecycler

    override fun bindHolder(manager: BindSuperAdapterManager) {
        manager.bind(ReposUIModel::class.java, ReposHolder.ID, ReposHolder::class.java)
    }

    /**
     * 初始化弹出过滤列表
     */
    private fun initDropLists(context: Context?) {

        val sortData = getViewModel().sortData
        val sortValue = getViewModel().sortValue

        val dropMap = HashMap<String, View>()

        for (i in 0 until sortData.size) {
            val dropList = ListView(context)
            dropList.dividerHeight = 0
            val sinceListAdapter = ListDropDownAdapter(context!!, sortData[i])
            dropList.adapter = sinceListAdapter
            dropMap[sortData[i][0]] = dropList
            dropList.setOnItemClickListener { view, _, p, _ ->
                (view.adapter as ListDropDownAdapter).setCheckItem(p)
                trend_drop_menu.setTabText(sortData[i][p])
                trend_drop_menu.closeMenu()
                getViewModel().sortType[i] = sortValue[i][p]
                showRefresh()
            }
        }


        trend_drop_menu.setDropDownMenu(dropMap.keys.toList(), dropMap.values.toList(), baseRecycler)
    }
}