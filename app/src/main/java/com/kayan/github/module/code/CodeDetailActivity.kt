package com.kayan.github.module.code

import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.kayan.github.di.ARouterInjectable
import com.kayan.github.module.ARouterAddress
import com.kayan.github.module.base.BaseFragmentActivity

/**
 * Created by kayan
 * Date: 2022-06-18
 */

@Route(path = ARouterAddress.CodeDetailActivity)
class CodeDetailActivity : BaseFragmentActivity(), ARouterInjectable {

    companion object {

        fun gotoCodeDetail(userName: String, reposName: String, path: String, url: String) {
            getRouterNavigation(ARouterAddress.CodeDetailActivity, userName, reposName, path, url, null).navigation()
        }

        fun gotoCodeDetailLocal(path: String, localCode: String) {
            getRouterNavigation(ARouterAddress.CodeDetailActivity, "", "", path, "", localCode).navigation()
        }

        fun getRouterNavigation(uri: String, userName: String, reposName: String, path: String, url: String, localCode: String?): Postcard {
            return ARouter.getInstance()
                    .build(uri)
                    .withString("path", path)
                    .withString("url", url)
                    .withString("userName", userName)
                    .withString("reposName", reposName)
                    .withString("localCode", localCode)
        }
    }

    @Autowired
    @JvmField
    var path = ""


    @Autowired
    @JvmField
    var url = ""

    @Autowired
    @JvmField
    var localCode: String? = null

    @Autowired
    @JvmField
    var reposName = ""

    @Autowired
    @JvmField
    var userName = ""


    override fun getToolBarTitle(): String = path

    override fun getInitFragment(): CodeDetailFragment = getRouterNavigation(ARouterAddress.CodeDetailFragment, userName, reposName, path, url, localCode).navigation() as CodeDetailFragment


}