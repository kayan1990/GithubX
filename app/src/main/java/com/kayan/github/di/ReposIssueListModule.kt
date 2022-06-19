package com.kayan.github.di

import android.app.Application
import androidx.core.content.ContextCompat
import com.mikepenz.iconics.IconicsColor
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.utils.sizeDp
import com.kayan.github.kotlin.R
import com.kayan.github.common.style.Iconfont
import com.kayan.github.di.annotation.FragmentQualifier
import dagger.Module
import dagger.Provides
import devlight.io.library.ntb.NavigationTabBar

/**
 * 仓库Issue列表TabBar数据
 * Created by kayan
 * Date: 2022-06-18
 */

@Module
class ReposIssueListModule {


    @FragmentQualifier("IssueList")
    @Provides
    fun providerReposIssueListTabModel(application: Application): List<NavigationTabBar.Model> {
        return listOf(
                NavigationTabBar.Model.Builder(
                        IconicsDrawable(application)
                                .icon(Iconfont.Icon.GSY_REPOS_ITEM_ALL)
                                .color(IconicsColor.colorInt(R.color.subTextColor))
                                .sizeDp(14),
                        ContextCompat.getColor(application, R.color.colorPrimaryLight))
                        .title(application.getString(R.string.issueAllText))
                        .build(),
                NavigationTabBar.Model.Builder(
                        IconicsDrawable(application)
                                .icon(Iconfont.Icon.GSY_REPOS_ITEM_OPEN)
                                .color(IconicsColor.colorInt(R.color.subTextColor))
                                .sizeDp(14),
                        ContextCompat.getColor(application, R.color.colorPrimaryLight))
                        .title(application.getString(R.string.issueOpenText))
                        .build(),
                NavigationTabBar.Model.Builder(
                        IconicsDrawable(application)
                                .icon(Iconfont.Icon.GSY_REPOS_ITEM_CLOSE)
                                .color(IconicsColor.colorInt(R.color.subTextColor))
                                .sizeDp(14),
                        ContextCompat.getColor(application, R.color.colorPrimaryLight))
                        .title(application.getString(R.string.issueCloseText))
                        .build()
        )

    }


    @FragmentQualifier("IssueList")
    @Provides
    fun providerStatusList(): ArrayList<String> = arrayListOf("all", "open", "closed")

}