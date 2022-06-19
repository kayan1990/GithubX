package com.kayan.github.di

import android.app.Application
import android.graphics.Color
import androidx.fragment.app.Fragment
import com.mikepenz.iconics.IconicsColor
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.utils.sizeDp
import com.kayan.github.kotlin.R
import com.kayan.github.common.style.Iconfont
import com.kayan.github.module.my.MyFragment
import com.kayan.github.module.trend.TrendFragment
import dagger.Module
import dagger.Provides
import devlight.io.library.ntb.NavigationTabBar

/**
 * MainActivity注入需要的Module
 * Created by kayan
 * Date: 2022-06-18
 */


@Module
class MainActivityModule {

    @Provides
    fun providerMainFragmentList(): List<Fragment> {
        return listOf(TrendFragment(), MyFragment())
    }

    @Provides
    fun providerMainTabModel(application: Application): List<NavigationTabBar.Model> {
        return listOf(
//                NavigationTabBar.Model.Builder(
//                        IconicsDrawable(application)
//                                .icon(GSYIconfont.Icon.GSY_MAIN_DT)
//                                .color(IconicsColor.colorInt(R.color.subTextColor))
//                                .sizeDp(20),
//                        Color.parseColor("#00000000"))
//                        .title(application.getString(R.string.tabDynamic))
//                        .build(),
                NavigationTabBar.Model.Builder(
                        IconicsDrawable(application)
                                .icon(Iconfont.Icon.GSY_MAIN_QS)
                                .color(IconicsColor.colorInt(R.color.subTextColor))
                                .sizeDp(20),
                        Color.parseColor("#00000000"))
                        .title(application.getString(R.string.tabRecommended))
                        .build(),
                NavigationTabBar.Model.Builder(
                        IconicsDrawable(application)
                                .icon(Iconfont.Icon.GSY_MAIN_MY)
                                .color(IconicsColor.colorInt(R.color.subTextColor))
                                .sizeDp(20),
                        Color.parseColor("#00000000"))
                        .title(application.getString(R.string.tabMy))
                        .build()
        )

    }
}
