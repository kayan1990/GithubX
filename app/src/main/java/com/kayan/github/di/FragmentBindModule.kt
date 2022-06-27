package com.kayan.github.di

import com.kayan.github.module.code.CodeDetailFragment
import com.kayan.github.module.dynamic.DynamicFragment
import com.kayan.github.module.issue.IssueDetailFragment
import com.kayan.github.module.list.GeneralListFragment
import com.kayan.github.module.login.LoginOAuthFragment
import com.kayan.github.module.my.MyFragment
import com.kayan.github.module.notify.NotifyFragment
import com.kayan.github.module.person.PersonFragment
import com.kayan.github.module.repos.action.ReposActionListFragment
import com.kayan.github.module.repos.file.ReposFileListFragment
import com.kayan.github.module.repos.issue.ReposIssueListFragment
import com.kayan.github.module.repos.readme.ReposReadmeFragment
import com.kayan.github.module.search.SearchFragment
import com.kayan.github.module.trend.TrendFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Fragment注入
 * Created by kayan
 * Date: 2022-06-18
 */


@Module
abstract class MainFragmentBindModule {

    //主要作用就是通过 @ContributesAndroidInjector  标记哪个类需要使用依赖注入功能
    //节省代码
    @ContributesAndroidInjector
    abstract fun contributeDynamicFragment(): DynamicFragment

    @ContributesAndroidInjector
    abstract fun contributeTrendFragment(): TrendFragment

    @ContributesAndroidInjector
    abstract fun contributeMyFragment(): MyFragment

    @ContributesAndroidInjector
    abstract fun contributeLoginOAuthFragment(): LoginOAuthFragment

    @ContributesAndroidInjector(modules = [SearchModule::class])
    abstract fun contributeSearchFragment(): SearchFragment
}

@Module
abstract class StartFragmentBindModule {

    @ContributesAndroidInjector
    abstract fun contributeLoginOAuthFragment(): LoginOAuthFragment

}


@Module
abstract class PersonFragmentBindModule {

    @ContributesAndroidInjector
    abstract fun contributeWelcomeFragment(): PersonFragment

}

@Module
abstract class CodeDetailFragmentBindModule {

    @ContributesAndroidInjector
    abstract fun contributeCodeDetailFragment(): CodeDetailFragment
}


@Module
abstract class IssueDetailFragmentBindModule {

    @ContributesAndroidInjector
    abstract fun contributeIssueDetailFragment(): IssueDetailFragment
}


@Module
abstract class ReposDetailFragmentBindModule {

    @ContributesAndroidInjector
    abstract fun contributeReposReadmeFragment(): ReposReadmeFragment


    @ContributesAndroidInjector(modules = [ReposIssueListModule::class])
    abstract fun contributeReposIssueListFragment(): ReposIssueListFragment


    @ContributesAndroidInjector
    abstract fun contributeReposActionListFragment(): ReposActionListFragment


    @ContributesAndroidInjector
    abstract fun contributeReposFileListFragment(): ReposFileListFragment
}

@Module
abstract class SearchFragmentBindModule {

    @ContributesAndroidInjector(modules = [SearchModule::class])
    abstract fun contributeSearchFragment(): SearchFragment
}

@Module
abstract class GeneralListFragmentBindModule {

    @ContributesAndroidInjector
    abstract fun contributeGeneralListFragment(): GeneralListFragment
}






