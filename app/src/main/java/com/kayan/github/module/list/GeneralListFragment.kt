package com.kayan.github.module.list

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.kayan.github.kotlin.R
import com.kayan.github.kotlin.databinding.FragmentListBinding
import com.kayan.github.di.ARouterInjectable
import com.kayan.github.model.ui.EventUIModel
import com.kayan.github.model.ui.ReposUIModel
import com.kayan.github.model.ui.UserUIModel
import com.kayan.github.module.ARouterAddress
import com.kayan.github.module.base.BaseListFragment
import com.kayan.github.module.person.PersonActivity
import com.kayan.github.module.repos.ReposDetailActivity
import com.kayan.github.ui.holder.EventHolder
import com.kayan.github.ui.holder.ReposHolder
import com.kayan.github.ui.holder.UserHolder
import com.shuyu.commonrecycler.BindSuperAdapterManager
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * 通用列表
 * Created by kayan
 * Date: 2018-11-08
 */

@Route(path = ARouterAddress.GeneralListFragment)
class GeneralListFragment : BaseListFragment<FragmentListBinding, GeneralListViewModel>(), ARouterInjectable {

    @Autowired
    @JvmField
    var reposName = ""

    @Autowired
    @JvmField
    var userName = ""

    @Autowired
    @JvmField
    var requestType: GeneralEnum? = null


    var filterController: GeneralFilterController? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_list
    }

    override fun onItemClick(context: Context, position: Int) {
        super.onItemClick(context, position)
        val item = adapter?.dataList?.get(position)
        when (item) {
            is EventUIModel -> {

            }
            is UserUIModel -> {
                PersonActivity.gotoPersonInfo(item.login!!)
            }
            is ReposUIModel -> {
                ReposDetailActivity.gotoReposDetail(item.ownerName, item.repositoryName)
            }
        }
    }

    override fun onCreateView(mainView: View?) {
        super.onCreateView(mainView)
        getViewModel().requestType = requestType
        getViewModel().reposName = reposName
        getViewModel().userName = userName
        filterController = GeneralFilterController(this, getViewModel())
    }

    override fun getViewModelClass(): Class<GeneralListViewModel> = GeneralListViewModel::class.java

    override fun enableRefresh(): Boolean = true

    override fun enableLoadMore(): Boolean = true

    override fun getRecyclerView(): RecyclerView? = baseRecycler

    override fun bindHolder(manager: BindSuperAdapterManager) {
        manager.bind(EventUIModel::class.java, EventHolder.ID, EventHolder::class.java)
        manager.bind(UserUIModel::class.java, UserHolder.ID, UserHolder::class.java)
        manager.bind(ReposUIModel::class.java, ReposHolder.ID, ReposHolder::class.java)
    }
}