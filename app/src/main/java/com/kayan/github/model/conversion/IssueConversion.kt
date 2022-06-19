package com.kayan.github.model.conversion

import com.kayan.github.common.utils.CommonUtils
import com.kayan.github.model.bean.Issue
import com.kayan.github.model.bean.IssueEvent
import com.kayan.github.model.ui.IssueUIModel

/**
 * Issue相关实体转换
 * Created by kayan
 * Date: 2022-06-18
 */
object IssueConversion {

    fun issueToIssueUIModel(issue: Issue): IssueUIModel {
        val issueUIModel = IssueUIModel()
        issueUIModel.username = issue.user?.login ?: ""
        issueUIModel.image = issue.user?.avatarUrl ?: ""
        issueUIModel.action = issue.title ?: ""
        issueUIModel.time = CommonUtils.getDateStr(issue.createdAt)
        issueUIModel.comment = issue.commentNum.toString()
        issueUIModel.issueNum = issue.number
        issueUIModel.status = issue.state ?: ""
        issueUIModel.content = issue.body ?: ""
        issueUIModel.locked = issue.locked
        return issueUIModel
    }


    fun issueEventToIssueUIModel(issue: IssueEvent): IssueUIModel {
        val issueUIModel = IssueUIModel()
        issueUIModel.username = issue.user?.login ?: ""
        issueUIModel.image = issue.user?.avatarUrl ?: ""
        issueUIModel.action = issue.body ?: ""
        issueUIModel.time = CommonUtils.getDateStr(issue.createdAt)
        issueUIModel.status = issue.id ?: ""
        return issueUIModel
    }

}