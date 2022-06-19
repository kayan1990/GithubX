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
 * 搜索页面数据TabBar
 * Created by kayan
 * Date: 2022-06-18
 */

@Module
class SearchModule {


    @FragmentQualifier("Search")
    @Provides
    fun providerReposIssueListTabModel(application: Application): List<NavigationTabBar.Model> {
        return listOf(
                NavigationTabBar.Model.Builder(
                        IconicsDrawable(application)
                                .icon(Iconfont.Icon.GSY_HOME)
                                .color(IconicsColor.colorInt(R.color.subTextColor))
                                .sizeDp(20),
                        ContextCompat.getColor(application, R.color.colorPrimaryLight))
                        .title(application.getString(R.string.searchRepos))
                        .build(),
                NavigationTabBar.Model.Builder(
                        IconicsDrawable(application)
                                .icon(Iconfont.Icon.GSY_REPOS_ITEM_USER)
                                .color(IconicsColor.colorInt(R.color.subTextColor))
                                .sizeDp(20),
                        ContextCompat.getColor(application, R.color.colorPrimaryLight))
                        .title(application.getString(R.string.searchUser))
                        .build()
        )

    }

}