package com.kayan.github.module.info

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import com.orhanobut.dialogplus.DialogPlus
import com.kayan.github.kotlin.R
import com.kayan.github.common.net.ResultCallBack
import com.kayan.github.common.utils.IssueDialogClickListener
import com.kayan.github.common.utils.showIssueEditDialog
import com.kayan.github.model.AppGlobalModel
import com.kayan.github.model.bean.UserInfoRequestModel
import com.kayan.github.model.ui.UserUIModel
import com.kayan.github.repository.UserRepository
import javax.inject.Inject

/**
 * Created by kayan
 * Date: 2018-11-19
 */
class UserInfoViewModel @Inject constructor(private val userRepository: UserRepository, private val globalAppModel: AppGlobalModel) : ViewModel() {


    fun onInfoClick(v: View?) {
        val context = v?.context ?: return
        when (v.id) {
            R.id.user_info_name -> {
                context.showIssueEditDialog(context.getString(R.string.edit), false,
                        "", globalAppModel.userObservable.name, object : IssueDialogClickListener {
                    override fun onConfirm(dialog: DialogPlus, title: String, editTitle: String?, editContent: String?) {
                        val userInfoRequestModel = UserInfoRequestModel()
                        userInfoRequestModel.name = editContent
                        changeUserInfo(context, userInfoRequestModel, dialog)
                    }
                })
            }
            R.id.user_info_email -> {
                context.showIssueEditDialog(context.getString(R.string.edit), false,
                        "", globalAppModel.userObservable.email, object : IssueDialogClickListener {
                    override fun onConfirm(dialog: DialogPlus, title: String, editTitle: String?, editContent: String?) {
                        val userInfoRequestModel = UserInfoRequestModel()
                        userInfoRequestModel.email = editContent
                        changeUserInfo(context, userInfoRequestModel, dialog)
                    }
                })
            }
            R.id.user_info_location -> {
                context.showIssueEditDialog(context.getString(R.string.edit), false,
                        "", globalAppModel.userObservable.location, object : IssueDialogClickListener {
                    override fun onConfirm(dialog: DialogPlus, title: String, editTitle: String?, editContent: String?) {
                        val userInfoRequestModel = UserInfoRequestModel()
                        userInfoRequestModel.location = editContent
                        changeUserInfo(context, userInfoRequestModel, dialog)
                    }
                })
            }
            R.id.user_info_bio -> {
                context.showIssueEditDialog(context.getString(R.string.edit), false,
                        "", globalAppModel.userObservable.bio, object : IssueDialogClickListener {
                    override fun onConfirm(dialog: DialogPlus, title: String, editTitle: String?, editContent: String?) {
                        val userInfoRequestModel = UserInfoRequestModel()
                        userInfoRequestModel.bio = editContent
                        changeUserInfo(context, userInfoRequestModel, dialog)
                    }
                })
            }
            R.id.user_info_blog -> {
                context.showIssueEditDialog(context.getString(R.string.edit), false,
                        "", globalAppModel.userObservable.blog, object : IssueDialogClickListener {
                    override fun onConfirm(dialog: DialogPlus, title: String, editTitle: String?, editContent: String?) {
                        val userInfoRequestModel = UserInfoRequestModel()
                        userInfoRequestModel.blog = editContent
                        changeUserInfo(context, userInfoRequestModel, dialog)
                    }
                })
            }
            R.id.user_info_company -> {
                context.showIssueEditDialog(context.getString(R.string.edit), false,
                        "", globalAppModel.userObservable.company, object : IssueDialogClickListener {
                    override fun onConfirm(dialog: DialogPlus, title: String, editTitle: String?, editContent: String?) {
                        val userInfoRequestModel = UserInfoRequestModel()
                        userInfoRequestModel.company = editContent
                        changeUserInfo(context, userInfoRequestModel, dialog)
                    }
                })
            }
        }

    }

    fun changeUserInfo(context: Context, userInfoRequestModel: UserInfoRequestModel, dialog: DialogPlus) {
        userRepository.changeUserInfo(context, userInfoRequestModel, object : ResultCallBack<UserUIModel> {
            override fun onSuccess(result: UserUIModel?) {
                dialog.dismiss()
            }

            override fun onFailure() {
            }
        })
    }
}