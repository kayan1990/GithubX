package com.kayan.github.module.issue

import android.content.Context
import android.os.Handler
import android.view.View
import androidx.core.os.postDelayed
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.OnItemClickListener
import com.kayan.github.kotlin.R
import com.kayan.github.common.net.ResultCallBack
import com.kayan.github.common.utils.IssueDialogClickListener
import com.kayan.github.common.utils.copy
import com.kayan.github.common.utils.showIssueEditDialog
import com.kayan.github.common.utils.showOptionSelectDialog
import com.kayan.github.model.ui.IssueUIModel
import com.shuyu.commonrecycler.BindSuperAdapter
import org.jetbrains.anko.toast

class IssueOptionCommentController(private val context: Context, private val adapter: BindSuperAdapter?, private val issueDetailFragment: IssueDetailFragment) : OnItemClickListener {

    private var selectPosition = -1
    private var selectItem: IssueUIModel? = null

    val list = arrayListOf(context.getString(R.string.copyComment), context.getString(R.string.issueCommentEdit), context.getString(R.string.issueCommentDelete))

    fun onItemClick(position: Int) {
        selectItem = adapter?.dataList?.get(position) as IssueUIModel
        selectPosition = position
        context.showOptionSelectDialog(list, this)
    }

    override fun onItemClick(dialog: DialogPlus?, item: Any?, view: View?, p: Int) {
        if (selectItem == null || selectPosition == -1) {
            return
        }
        when {
            list[p].contains(context.getString(R.string.copyComment)) -> {
                context.copy(selectItem!!.action)
                dialog?.dismiss()
                context.toast(R.string.hadCopy)

            }
            list[p].contains(context.getString(R.string.issueCommentEdit)) -> {
                dialog?.dismiss()
                Handler().postDelayed(1000) {
                    context.showIssueEditDialog(context.getString(R.string.issueCommentEdit), false, selectPosition.toString(), selectItem!!.action, object : IssueDialogClickListener {
                        override fun onConfirm(dialog: DialogPlus, title: String, editTitle: String?, editContent: String?) {
                            val currentPosition = editTitle!!.toInt()
                            val currentItem = adapter?.dataList!![currentPosition] as IssueUIModel
                            currentItem.action = editContent ?: ""
                            issueDetailFragment.getViewModel().editComment(context, currentItem.status, currentItem)
                            dialog.dismiss()
                        }
                    })
                }
            }
            list[p].contains(context.getString(R.string.issueCommentDelete)) -> {
                dialog?.dismiss()
                issueDetailFragment.getViewModel().deleteComment(context, selectItem!!.status, object : ResultCallBack<String> {
                    override fun onSuccess(result: String?) {
                        issueDetailFragment.notifyDelete(selectPosition, 1)
                    }
                })
            }
        }
    }
}

