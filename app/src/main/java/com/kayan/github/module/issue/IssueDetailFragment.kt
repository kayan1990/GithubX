package com.kayan.github.module.issue

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.kayan.github.kotlin.R
import com.kayan.github.common.utils.CommonUtils
import com.kayan.github.common.utils.copy
import com.kayan.github.kotlin.databinding.FragmentIssueDetailBinding
import com.kayan.github.kotlin.databinding.LayoutIssueHeaderBinding
import com.kayan.github.di.ARouterInjectable
import com.kayan.github.model.ui.IssueUIModel
import com.kayan.github.module.ARouterAddress
import com.kayan.github.module.base.BaseListFragment
import com.kayan.github.module.person.PersonActivity
import com.kayan.github.ui.holder.IssueCommentHolder
import com.kayan.github.ui.holder.base.DataBindingComponent
import com.shuyu.commonrecycler.BindSuperAdapterManager
import kotlinx.android.synthetic.main.fragment_issue_detail.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.share
import org.jetbrains.anko.toast

/**
 * Created by kayan
 * Date: 2018-10-24
 */

@Route(path = ARouterAddress.IssueDetailFragment)
class IssueDetailFragment : BaseListFragment<FragmentIssueDetailBinding, IssueDetailViewModel>(), ARouterInjectable {

    @Autowired
    @JvmField
    var userName = ""

    @Autowired
    @JvmField
    var reposName = ""


    @Autowired
    @JvmField
    var issueNumber = 0

    private lateinit var issueCommentViewModel: IssueOptionCommentController

    private lateinit var issueControlViewModel: IssueStatusController

    override fun getLayoutId(): Int {
        return R.layout.fragment_issue_detail
    }

    override fun onCreateView(mainView: View?) {
        super.onCreateView(mainView)
        getViewModel().userName = userName
        getViewModel().reposName = reposName
        getViewModel().issueNumber = issueNumber
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        issueCommentViewModel = IssueOptionCommentController(context!!, adapter, this)
        issueControlViewModel = IssueStatusController(context!!, adapter, this, issue_detail_control_bar)

        getViewModel().liveIssueModel.observe(this, Observer { result ->
            issueControlViewModel.initControlBar(result)
        })

    }

    override fun onItemClick(context: Context, position: Int) {
        super.onItemClick(context, position)
        issueCommentViewModel.onItemClick(position)
    }

    override fun enableRefresh(): Boolean = true

    override fun enableLoadMore(): Boolean = true

    override fun getRecyclerView(): RecyclerView? = baseRecycler

    override fun getViewModelClass(): Class<IssueDetailViewModel> = IssueDetailViewModel::class.java

    override fun bindHolder(manager: BindSuperAdapterManager) {
        val binding: LayoutIssueHeaderBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_issue_header,
                null, false, DataBindingComponent())
        binding.issueUIModel = getViewModel().issueUIModel

        binding.issueHeaderImage.setOnClickListener {
            PersonActivity.gotoPersonInfo(getViewModel().issueUIModel.username)
        }

        manager.addHeaderView(binding.root)

        manager.bind(IssueUIModel::class.java, IssueCommentHolder.ID, IssueCommentHolder::class.java)
    }


    override fun actionOpenByBrowser() {
        context?.browse(CommonUtils.getIssueHtmlUrl(userName, reposName, issueNumber.toString()))
    }

    override fun actionCopy() {
        context?.copy(CommonUtils.getIssueHtmlUrl(userName, reposName, issueNumber.toString()))
        context?.toast(R.string.hadCopy)
    }

    override fun actionShare() {
        context?.share(CommonUtils.getIssueHtmlUrl(userName, reposName, issueNumber.toString()))
    }
}