package com.kayan.github.di

import com.kayan.github.di.annotation.ActivityScope
import com.kayan.github.module.StartNavigationActivity
import com.kayan.github.module.code.CodeDetailActivity
import com.kayan.github.module.info.UserInfoActivity
import com.kayan.github.module.issue.IssueDetailActivity
import com.kayan.github.module.list.GeneralListActivity
import com.kayan.github.module.main.MainActivity
import com.kayan.github.module.notify.NotifyActivity
import com.kayan.github.module.person.PersonActivity
import com.kayan.github.module.repos.ReposDetailActivity
import com.kayan.github.module.search.SearchActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Activity 注入，并且提供Fragment注入绑定
 * Created by kayan
 * Date: 2022-06-18
 */
@Module
abstract class ActivityBindModule {

    @ActivityScope
    //主要作用就是通过 @ContributesAndroidInjector  标记哪个类需要使用依赖注入功能
    //节省代码
    @ContributesAndroidInjector(modules = [MainFragmentBindModule::class])
    abstract fun StartNavigationActivityInjector(): StartNavigationActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class, MainFragmentBindModule::class])
    abstract fun mainActivityInjector(): MainActivity


    @ActivityScope
    @ContributesAndroidInjector(modules = [PersonFragmentBindModule::class])
    abstract fun personActivityInjector(): PersonActivity


    @ActivityScope
    @ContributesAndroidInjector(modules = [ReposDetailFragmentBindModule::class, ReposDetailModule::class])
    abstract fun reposDetailActivityInjector(): ReposDetailActivity


    @ActivityScope
    @ContributesAndroidInjector(modules = [CodeDetailFragmentBindModule::class])
    abstract fun codeDetailActivityInjector(): CodeDetailActivity


    @ActivityScope
    @ContributesAndroidInjector(modules = [IssueDetailFragmentBindModule::class])
    abstract fun issueDetailActivityInjector(): IssueDetailActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [SearchFragmentBindModule::class])
    abstract fun searchActivityInjector(): SearchActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [GeneralListFragmentBindModule::class])
    abstract fun generalListActivityInjector(): GeneralListActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [NotifyFragmentBindModule::class])
    abstract fun notifyActivityInjector(): NotifyActivity


    @ActivityScope
    @ContributesAndroidInjector
    abstract fun userInfoActivityInjector(): UserInfoActivity


}