package com.kayan.github.module.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.kayan.github.kotlin.R
import com.kayan.github.di.ARouterInjectable
import com.kayan.github.module.ARouterAddress
import com.kayan.github.module.base.BaseFragmentActivity

/**
 * ้็จๅ่กจ
 * Created by kayan
 * Date: 2018-11-08
 */
@Route(path = ARouterAddress.GeneralListActivity)
class GeneralListActivity : BaseFragmentActivity(), ARouterInjectable {

    @Autowired
    @JvmField
    var userName = ""


    @Autowired
    @JvmField
    var reposName = ""


    @Autowired
    @JvmField
    var title = ""


    @Autowired
    @JvmField
    var requestType: GeneralEnum? = null

    @Autowired
    @JvmField
    var needFilter: Boolean = false


    private var fragment: GeneralListFragment? = null

    companion object {
        fun gotoGeneralList(userName: String, reposName: String, title: String, requestType: GeneralEnum?, needFilter: Boolean = false) {
            getRouterNavigation(ARouterAddress.GeneralListActivity, userName, reposName, title, requestType, needFilter).navigation()
        }

        fun getRouterNavigation(uri: String, userName: String, reposName: String, title: String, requestType: GeneralEnum?, needFilter: Boolean): Postcard {
            return ARouter.getInstance()
                    .build(uri)
                    .withString("userName", userName)
                    .withString("reposName", reposName)
                    .withString("title", title)
                    .withSerializable("requestType", requestType)
                    .withSerializable("needFilter", needFilter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getInitFragment(): GeneralListFragment {
        fragment = getRouterNavigation(ARouterAddress.GeneralListFragment, userName, reposName, title, requestType, needFilter).navigation() as GeneralListFragment
        return fragment!!
    }

    override fun getToolBarTitle(): String = title


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (needFilter) {
            menuInflater.inflate(R.menu.toolbar_menu_filter, menu)
        }
        return true
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_filter -> {
                fragment?.filterController?.drawer?.openDrawer()
            }
        }
        return true
    }
}