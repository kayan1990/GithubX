package com.kayan.github.common.utils

import android.content.Context
import com.kayan.github.model.ui.EventUIAction
import com.kayan.github.model.ui.EventUIModel
import com.kayan.github.module.issue.IssueDetailActivity
import com.kayan.github.module.person.PersonActivity
import com.kayan.github.module.repos.ReposDetailActivity

/**
 * 事件相关跳转
 * Created by kayan
 * Date: 2022-06-18
 */

object EventUtils {

    fun evenAction(context: Context?, eventUIModel: EventUIModel?) {
        when (eventUIModel?.actionType) {
            EventUIAction.Person -> {
                PersonActivity.gotoPersonInfo(eventUIModel.owner)
            }
            EventUIAction.Repos -> {
                ReposDetailActivity.gotoReposDetail(eventUIModel.owner, eventUIModel.repositoryName)
            }
            EventUIAction.Issue -> {
                IssueDetailActivity.gotoIssueDetail(eventUIModel.owner, eventUIModel.repositoryName, eventUIModel.IssueNum)
            }

            EventUIAction.Release -> {
            }
        }
    }
}