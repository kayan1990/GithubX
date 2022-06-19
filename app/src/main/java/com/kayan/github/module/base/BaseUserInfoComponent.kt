package com.kayan.github.module.base

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import com.kayan.github.AppConfig
import com.kayan.github.common.utils.EventUtils
import com.kayan.github.common.utils.GSYPreference
import com.kayan.github.kotlin.R
import com.kayan.github.kotlin.databinding.FragmentUserInfoBinding
import com.kayan.github.kotlin.databinding.LayoutUserHeaderBinding
import com.kayan.github.model.ui.EventUIModel
import com.kayan.github.model.ui.UserUIModel
import com.kayan.github.module.list.GeneralEnum
import com.kayan.github.module.list.GeneralListActivity
import com.kayan.github.module.person.PersonActivity
import com.kayan.github.repository.LoginRepository
import com.kayan.github.repository.UserRepository
import com.kayan.github.ui.holder.EventHolder
import com.kayan.github.ui.holder.UserHolder
import com.kayan.github.ui.holder.base.DataBindingComponent
import com.shuyu.commonrecycler.BindSuperAdapterManager
import kotlinx.android.synthetic.main.fragment_user_info.*
import org.jetbrains.anko.toast

/**
 * 基础用户显示
 * Created by kayan
 * Date: 2018-10-24
 */

abstract class BaseUserInfoFragment<T : BaseUserInfoViewModel> : BaseListFragment<FragmentUserInfoBinding, T>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_user_info
    }

    override fun onCreateView(mainView: View?) {
        super.onCreateView(mainView)
        getViewModel().login = getLoginName()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        login_submit_btn.setOnClickListener {
//            navigationPopUpTo(view, null, R.id.action_myFragment_to_loginOAuthFragment2, false, false)
//        }
    }


    override fun onItemClick(context: Context, position: Int) {
        super.onItemClick(context, position)
        val item = adapter?.dataList?.get(position)
        when (item) {
            is EventUIModel -> {
                EventUtils.evenAction(activity, adapter?.dataList?.get(position) as EventUIModel)
            }
            is UserUIModel -> {
                PersonActivity.gotoPersonInfo(item.login!!)
            }
        }
    }

    override fun enableRefresh(): Boolean = true

    override fun enableLoadMore(): Boolean = true

    override fun getRecyclerView(): RecyclerView? = fragment_my_recycler

    override fun bindHolder(manager: BindSuperAdapterManager) {
        val binding: LayoutUserHeaderBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_user_header,
                null, false, DataBindingComponent())
        binding.userUIModel = getViewModel().getUserModel()
        binding.baseUserViewModel = getViewModel()
        binding.userHeaderNotify.visibility = View.GONE
        manager.addHeaderView(binding.root)
        bindHeader(binding)

        manager.bind(EventUIModel::class.java, EventHolder.ID, EventHolder::class.java)
        manager.bind(UserUIModel::class.java, UserHolder.ID, UserHolder::class.java)
    }

    open fun bindHeader(binding: LayoutUserHeaderBinding) {
    }

    abstract fun getLoginName(): String?
}


abstract class BaseUserInfoViewModel constructor(private val loginRepository: LoginRepository, private val userRepository: UserRepository, private val application: Application) : BaseViewModel(application) {

    val foucsIcon = ObservableField<String>()

    var login: String? = null

    var userInfoStorage: String by GSYPreference(AppConfig.USER_INFO, "")

    override fun loadDataByRefresh() {
        userRepository.getPersonInfo(null, this, login)
    }

    override fun loadDataByLoadMore() {
        if (getUserModel().type == "Organization") {
            userRepository.getOrgMembers(getUserModel().login, this, page)
        } else {
            userRepository.getUserEvent(getUserModel().login, this, page)
        }
    }


    fun onTabIconClick(v: View?) {
        getUserModel().login?.apply {
            when (v?.id) {
                R.id.user_header_repos -> {
                    GeneralListActivity.gotoGeneralList(this, "", login ?: ""+" "
                    +application.getString(R.string.repositoryText), GeneralEnum.UserRepository, true)
                }
                R.id.user_header_fan -> {
                    GeneralListActivity.gotoGeneralList(this, "", login ?: ""+" "
                    +application.getString(R.string.FollowersText), GeneralEnum.UserFollower)
                }
                R.id.user_header_focus -> {
                    GeneralListActivity.gotoGeneralList(this, "", login ?: ""+" "
                    +application.getString(R.string.FollowedText), GeneralEnum.UserFollowed)
                }
                R.id.user_header_star -> {
                    GeneralListActivity.gotoGeneralList(this, "", login ?: ""+" "
                    +application.getString(R.string.staredText), GeneralEnum.UserStar)
                }
                R.id.user_header_honor -> {
                    v.context.toast(R.string.user100Honor)
                }
            }
        }
    }

    fun loginOut(v: View?) {
        v?.run {
            loginRepository.logout(this.context)
            getUserModel().login = null;
        }

    }

    abstract fun getUserModel(): UserUIModel


    open fun onFocusClick(v: View?) {

    }
}