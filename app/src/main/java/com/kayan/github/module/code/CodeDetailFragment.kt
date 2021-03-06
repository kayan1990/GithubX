package com.kayan.github.module.code

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.kayan.github.common.utils.CommonUtils
import com.kayan.github.common.utils.copy
import com.kayan.github.di.ARouterInjectable
import com.kayan.github.kotlin.R
import com.kayan.github.kotlin.databinding.FragmentCodeDetailBinding
import com.kayan.github.module.ARouterAddress
import com.kayan.github.module.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_code_detail.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.share
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * Created by kayan
 * Date: 2022-06-18
 */

@Route(path = ARouterAddress.CodeDetailFragment)
class CodeDetailFragment : BaseFragment<FragmentCodeDetailBinding>(), ARouterInjectable {

    @Autowired
    @JvmField
    var path = ""


    @Autowired
    @JvmField
    var url = ""

    @Autowired
    @JvmField
    var reposName = ""

    @Autowired
    @JvmField
    var userName = ""

    @Autowired
    @JvmField
    var localCode: String? = null


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: CodeDetailViewModel

    override fun onCreateView(mainView: View?) {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(CodeDetailViewModel::class.java)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        code_detail_web.spinKit.visibility = View.VISIBLE
        viewModel.htmlData.observe(this, Observer {
            if (it.isNullOrBlank()) {
                return@Observer
            }
            code_detail_web.spinKit.visibility = View.GONE
            code_detail_web.webView.requestIntercept = false
            code_detail_web.webView.settings.defaultTextEncodingName = "UTF-8"//???????????????utf-8
            code_detail_web.webView.loadData(it, "text/html; charset=UTF-8", null);

        })
        if (localCode == null) {
            viewModel.getCodeHtml(userName, reposName, url)
        } else {
            viewModel.htmlData.value = localCode
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_code_detail


    override fun actionOpenByBrowser() {
        if (url.isBlank()) {
            return
        }
        context?.browse(CommonUtils.getFileHtmlUrl(userName, reposName, url))
    }

    override fun actionCopy() {
        if (url.isBlank()) {
            return
        }
        context?.copy(CommonUtils.getFileHtmlUrl(userName, reposName, url))
        context?.toast(R.string.hadCopy)
    }

    override fun actionShare() {
        if (url.isBlank()) {
            return
        }
        context?.share(CommonUtils.getFileHtmlUrl(userName, reposName, url))
    }
}