package com.kayan.github.module.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kayan.github.model.ui.EmptyUIModel
import com.kayan.github.ui.holder.EmptyHolder
import com.kayan.github.ui.holder.base.BindCustomLoadMoreFooter
import com.kayan.github.ui.holder.base.BindCustomRefreshHeader
import com.kayan.github.ui.holder.base.BindingDataRecyclerManager
import com.shuyu.commonrecycler.BindSuperAdapter
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.shuyu.commonrecycler.listener.OnItemClickListener
import com.shuyu.commonrecycler.listener.OnLoadingListener
import javax.inject.Inject

/**
 * 基础列表
 * Created by kayan
 * Date: 2018-10-19
 */
abstract class BaseListFragment<T : ViewDataBinding, R : BaseViewModel> : BaseFragment<T>(), OnItemClickListener,
    OnLoadingListener {

    protected var normalAdapterManager by autoCleared<BindingDataRecyclerManager>()

    private lateinit var baseViewModel: R

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var adapter by autoCleared<BindSuperAdapter>()

    override fun onCreateView(mainView: View?) {
        normalAdapterManager = BindingDataRecyclerManager()
        baseViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(getViewModelClass())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()

        //数据层加载数据的状态，通知UI进行相应，比如数据加载完成，就通知列表刷新完成
        getViewModel().loading.observe(this, Observer {
            when (it) {
                LoadState.RefreshDone -> {
                    refreshComplete()
                }
                LoadState.LoadMoreDone -> {
                    loadMoreComplete()
                }
                LoadState.Refresh -> {
                    ///刷新时清空旧数据
                }
            }
        })

        //进行数据刷新
        getViewModel().dataList.observe(this, Observer { items ->
            items?.apply {
                if (items.size > 0) {
                    if (getViewModel().isFirstData()) {
                        adapter?.dataList?.clear()
                    }
                    val currentSize: Int = adapter?.dataList?.size ?: 0
                    adapter?.dataList?.addAll(items)
                    if (currentSize == 0) {
                        notifyChanged()
                    } else {
                        notifyInsert(currentSize, items.size)
                    }
                } else {
                    if (getViewModel().isFirstData()) {
                        adapter?.dataList?.clear()
                        notifyChanged()
                    }
                }
            }
        })

        getViewModel().needMore.observe(this, Observer { it ->
            it?.apply {
                normalAdapterManager?.setNoMore(!it)
            }
        })

        //触发组件下拉刷新，开始请求数据
        showRefresh()
    }

    /**
     * item点击
     */
    override fun onItemClick(context: Context, position: Int) {

    }

    /**
     * UI组件回调： 下拉刷新
     */
    override fun onRefresh() {
        getViewModel().refresh()
    }

    /**
     * 加载更多
     */
    override fun onLoadMore() {
        getViewModel().loadMore()
    }

    /**
     * 当前 recyclerView，为空即不走 @link[initList] 的初始化
     */
    abstract fun getRecyclerView(): RecyclerView?

    /**
     * 绑定Item
     */
    abstract fun bindHolder(manager: BindSuperAdapterManager)

    /**
     * ViewModel Class
     */
    abstract fun getViewModelClass(): Class<R>

    /**
     * ViewModel
     */
    open fun getViewModel(): R = baseViewModel

    /**
     * 是否需要下拉刷新
     */
    open fun enableRefresh(): Boolean = false

    /**
     * 是否需要下拉刷新
     */
    open fun enableLoadMore(): Boolean = false


    open fun refreshComplete() {
        normalAdapterManager?.refreshComplete()
    }

    open fun loadMoreComplete() {
        normalAdapterManager?.loadMoreComplete()
    }

    open fun showRefresh() {
        normalAdapterManager?.setRefreshing(true)
    }

    open fun isLoading(): Boolean = getViewModel().isLoading()

    open fun notifyInsert(position: Int, count: Int) {
        adapter?.notifyItemRangeInserted(position + adapter!!.absFirstPosition(), count)
    }

    open fun notifyDelete(position: Int, count: Int) {
        adapter?.dataList?.removeAt(position)
        adapter?.notifyItemRangeRemoved(position + adapter!!.absFirstPosition(), count)
    }

    open fun notifyChanged() {
        adapter?.notifyDataSetChanged()
    }

    fun initList() {
        if (activity != null && getRecyclerView() != null) {
            normalAdapterManager?.setPullRefreshEnabled(enableRefresh())
                    ?.setLoadingMoreEnabled(enableLoadMore())
                    ?.setOnItemClickListener(this)
                    ?.setLoadingListener(this)
                    ?.setRefreshHeader(BindCustomRefreshHeader(activity!!))
                    ?.setFootView(BindCustomLoadMoreFooter(activity!!))
                    ?.setLoadingMoreEmptyEnabled(false)
                    ?.bindEmpty(EmptyUIModel(), EmptyHolder.ID, EmptyHolder::class.java)
            normalAdapterManager?.apply {
                bindHolder(this)
                adapter = BindSuperAdapter(activity as Context, this, arrayListOf())
                getRecyclerView()?.layoutManager = LinearLayoutManager(activity!!)
                getRecyclerView()?.adapter = adapter
            }
        }
    }
}