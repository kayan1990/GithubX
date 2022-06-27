package com.kayan.github.module.issue

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.kayan.github.kotlin.R
import com.kayan.github.di.ARouterInjectable
import com.kayan.github.ARouterAddress
import com.kayan.github.module.base.BaseFragmentActivity
import com.kayan.github.module.repos.ReposDetailActivity


/**
 * Created by kayan
 * Date: 2018-10-24
 */
@Route(path = ARouterAddress.IssueDetailActivity)
class IssueDetailActivity : BaseFragmentActivity(), ARouterInjectable {

    @Autowired
    @JvmField
    var userName = ""

    @Autowired
    @JvmField
    var reposName = ""


    @Autowired
    @JvmField
    var issueNumber = 0

    companion object {
        fun gotoIssueDetail(userName: String, reposName: String, issueNumber: Int) {
            getRouterNavigation(ARouterAddress.IssueDetailActivity, userName, reposName, issueNumber).navigation()
        }

        fun getRouterNavigation(uri: String, userName: String, reposName: String, issueNumber: Int): Postcard {
            return ARouter.getInstance()
                    .build(uri)
                    .withString("userName", userName)
                    .withString("reposName", reposName)
                    .withInt("issueNumber", issueNumber)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_home -> {
                ReposDetailActivity.gotoReposDetail(userName, reposName)
            }
        }
        return super.onMenuItemClick(item)
    }

    override fun getInitFragment(): IssueDetailFragment {
        return getRouterNavigation(ARouterAddress.IssueDetailFragment, userName, reposName, issueNumber).navigation() as IssueDetailFragment
    }

    override fun getToolBarTitle(): String = "$userName/$reposName"
}