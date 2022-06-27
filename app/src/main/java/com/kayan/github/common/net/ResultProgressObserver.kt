package com.kayan.github.common.net

import android.content.Context
import com.kayan.github.kotlin.R
import com.kayan.github.ui.view.LoadingDialog


/**
 * 带loading显示的结果回调
 * 在网络请求的开始和结束，自动处理loading展示和消失逻辑
 * Created by kayan
 * Date: 2018-10-08
 */
abstract class ResultProgressObserver<T>(private val context: Context, private val needLoading: Boolean = true) : ResultTipObserver<T>(context) {

    private var loadingDialog: LoadingDialog? = null

    private var loadingText: String? = null

    constructor(context: Context, loadingText: String?) : this(context) {
        this.loadingText = loadingText
    }

    override fun onRequestStart() {
        super.onRequestStart()
        if (needLoading) {
            showLoading()
        }
    }

    override fun onRequestEnd() {
        super.onRequestEnd()
        dismissLoading()
    }

    private fun getLoadingText(): String {
        return if (loadingText.isNullOrBlank()) context.getString(R.string.loading) else loadingText!!
    }

    private fun showLoading() {
        dismissLoading()
        loadingDialog = LoadingDialog.showDialog(context, getLoadingText(), false, null)
    }

    private fun dismissLoading() {
        loadingDialog?.apply {
            if (this.isShowing) {
                this.dismiss()
            }
        }
    }
}